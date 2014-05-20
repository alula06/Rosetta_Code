/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.SessionChallengeBean;
import api.bean.SessionUserBean;
import api.others.ChallengeSupporter;
import code.CodeJavaFilter;
import code.CodeProcessor;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.Map;
import model.Challenge;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Zi sheng Wu
 */
public class ChallengeCodeAction extends ActionSupport {
    // inputs
    private String sourcecode;
    // outputs
    private boolean status = false;
    private String message;
    private int errorcode;
    private int statuscode;
    private String compiler_command;
    private String compiler_error;
    private String execution_command;
    private String execution_error;
    private String execution_output;
    private int attemp;
    private boolean correct;
    // others
    private Date request_time;
    private String classname="";
    private SessionUserBean user;
    private SessionChallengeBean duel;
    private CodeProcessor cpr;
    private int user_id;
    private String correct_output;
    
    @Override
    public String execute() throws Exception {
        request_time = new Date();
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user == null) {
            errorcode = 1;
            message = "You must login to complete this action.";
            return SUCCESS;
        }
        duel = (SessionChallengeBean)session.get("duel");
        if (duel == null) {
            errorcode = -1;
            message = "No challenge in your session.";
            return SUCCESS;
        }
        Session psSession = HibernateSessionFactory.getSession();
        Transaction transaction = null;
        try {
            transaction = psSession.beginTransaction();
            Challenge psDuel = (Challenge)psSession.get(Challenge.class, duel.getId());
            if (psDuel == null) {
                message = "challenge not exists.";
                errorcode = -1;
                return SUCCESS;
            }
            user_id = user.getUser_id();
            attemp = duel.getAttempt()+1;
            duel.setAttempt(attemp);
            ChallengeSupporter.increaseAttempt(user_id, psDuel);
            int legalcode = ChallengeSupporter.isLegal(request_time, user_id, psDuel);
            if (legalcode == 0) {
                if (CodeJavaFilter.filter(sourcecode)) {
                    classname = CodeJavaFilter.getClassname(sourcecode);
                }
                if (classname.isEmpty()) {
                    errorcode = -1;
                    message = "Not correct a class name or containing illegal statements.";
                    return SUCCESS;
                }
                cpr = new CodeProcessor(user.getUser_id(), sourcecode, classname, duel.getType());
                if(cpr.createSourceFile()){
                    if(cpr.compile()){
                        compiler_command = "javac " + classname + ".java";
                        compiler_error = cpr.getCompileErrorMessage();
                        if(cpr.execute()) {
                            execution_command = "java " + classname;
                            execution_error = cpr.getExecuteErrorMessage();
                            execution_output = cpr.getExecuteOutputMessage().trim();
                            correct_output = psDuel.getCode().getOutput();
                            if (execution_output != null) {
                                correct = execution_output.equals(correct_output);
                            }
                            if (correct) {
                                ChallengeSupporter.finish(user_id, request_time, psDuel);
                                session.remove("duel");
                                message = "success";
                                status = true;
                            } else {
                                message = "not correct";
                            }
                        }
                    }
                }
                cpr.clear();
                statuscode = cpr.getStatus();
            } else {
                ChallengeSupporter.close(user_id, psDuel);
                session.remove("duel");
                errorcode = 2;
                message = "The challenge expired or closed.";
            }
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

    public int getAttemp() {
        return attemp;
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
    
}
