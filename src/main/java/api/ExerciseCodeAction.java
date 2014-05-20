/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.SessionUserBean;
import code.CodeJavaFilter;
import code.CodeProcessor;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import model.Activity;
import model.LessonCode;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Zi sheng Wu
 */
public class ExerciseCodeAction extends ActionSupport {
    // inputs
    private String sourcecode;
    private int activity_id;
    private int exercise_id;
    // outputs
    private boolean status = false;
    private String message;
    private int errorcode;
    private int statuscode;
    private String compiler_message;
    private String compiler_command;
    private String compiler_error;
    private String execution_message;
    private String execution_command;
    private String execution_error;
    private String execution_output;
    private String correct_output;
    private boolean correct;
    private int credit;
    // others
    private String classname="";
    private SessionUserBean user;
    private CodeProcessor cpr;
    private int user_id;
    
    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user == null) {
            errorcode = 1;
            message = "You must login to complete this action.";
            status = false;
            return SUCCESS;
        }

        Session psSession = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        try {
            user_id = user.getUser_id();
            transaction = psSession.beginTransaction();
            Activity psActivity = (Activity) psSession.get(Activity.class, activity_id);
            if (psActivity == null) {
                errorcode = -1;
                message = "Activity not exists.";
                return SUCCESS;
            }
            if (psActivity.getUser().getId() != user_id) {
                errorcode = -1;
                message = "illegal request. Not your own activity";
                return SUCCESS;
            }
            LessonCode psExercise = (LessonCode) psSession.get(LessonCode.class, exercise_id);
            if (psExercise == null) {
                errorcode = -1;
                message = "Exercise not exists.";
                return SUCCESS;
            }
            if (psExercise.getLesson().getId() != psActivity.getLesson().getId()) {
                errorcode = -1;
                message = "The lesson does have such exercise.";
                return SUCCESS;
            
            }
            if (psExercise.getOrder() != psActivity.getProgress()+1) {
                errorcode = -1;
                message = "illegal request. the exercise not match your lesson progress.";
                return SUCCESS;
            
            }
            if (CodeJavaFilter.filter(sourcecode)) {
                classname = CodeJavaFilter.getClassname(sourcecode);
            }
            if (classname.isEmpty()) {
                errorcode = -1;
                message = "Not correct a class name or containing illegal statements.";
                return SUCCESS;
            }
            cpr = new CodeProcessor(user.getUser_id(), sourcecode, classname, psExercise.getCode().getType());
            if(cpr.createSourceFile()){
                if(cpr.compile()){
                    compiler_command = "javac " + classname + ".java";
                    compiler_error = cpr.getCompileErrorMessage();
                    if(cpr.execute()) {
                        execution_command = "java " + classname;
                        execution_error = cpr.getExecuteErrorMessage();
                        execution_output = cpr.getExecuteOutputMessage().trim();
                        correct_output = psExercise.getCode().getOutput();
                        if (execution_output != null) {
                            correct = execution_output.equals(correct_output);
                        }
                        if (correct) {
                            message = "success";
                            status = true;
                            psActivity.setProgress(psActivity.getProgress()+1);
                            credit = psExercise.getCode().getCredit();
                            psActivity.setScore(psActivity.getScore()+credit);
                            psActivity.getUser().setScore(psActivity.getUser().getScore()+credit);
                        } else {
                            message = "not correct";
                        }
                    }
                }
            }
            cpr.clear();
            statuscode = cpr.getStatus();
            //compiler_command = cpr.getCompilerCommand();
            compiler_message = cpr.getCompilerMessage();
            //execution_command = cpr.getExecutionCommand();
            execution_message = cpr.getExecutionMessage();
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            throw ex;
        } finally {
            psSession.close();
        }
        return SUCCESS;
    }

    public void setSourcecode(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public String getCompiler_error() {
        return compiler_error;
    }

    public String getExecution_error() {
        return execution_error;
    }

    public String getExecution_output() {
        return execution_output;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getCompiler_command() {
        return compiler_command;
    }

    public String getExecution_command() {
        return execution_command;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public void setExercise_id(int exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getCorrect_output() {
        return correct_output;
    }

    public int getCredit() {
        return credit;
    }

    public String getCompiler_message() {
        return compiler_message;
    }

    public String getExecution_message() {
        return execution_message;
    }
    
}
