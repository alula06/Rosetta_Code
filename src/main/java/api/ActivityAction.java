/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.ActivityBean;
import api.bean.ExerciseBean;
import api.bean.LessonBean;
import api.bean.SessionUserBean;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.Activity;
import model.Lesson;
import model.LessonCode;
import model.User;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zi sheng Wu
 */
public class ActivityAction extends ActionSupport {
    // inputs
    private Integer requestcode = 0;
    private Integer activity_id;
    // outputs
    private boolean status;
    private String message;
    private int errorcode;
    private ActivityBean[] activities;
    private ExerciseBean[] exercises;
    // others
    private SessionUserBean user;
    private int user_id;
    
    public String listActivity() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            Session psSession = HibernateSessionFactory.getSession();
            try {
                User psUser = (User)psSession.get(User.class, user_id);
                List<Activity> psActivities = null;
                int size = 0;
                psActivities = psSession.createCriteria(Activity.class)
                            .add(Restrictions.eq("user",psUser))
                            .list();
                size = psActivities.size();
                if (size > 0) {
                    activities = new ActivityBean[size];
                    for (int i=0; i<size; i++) {
                        activities[i] = new ActivityBean();
                        storeParameterIntoActivityBean(activities[i], psActivities.get(i));
                    }
                    message = "success";
                    status = true;
                } else {
                    message = "No activity found.";
                    status = false;
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                psSession.close();
            }
        } else {
            errorcode = 1;
            message = "You must login to complete this action.";
            status = false;
        }
        return SUCCESS;
    }
    
    private void storeParameterIntoActivityBean(ActivityBean activity, Activity psActivity) {
        activity.setId(psActivity.getId());
        activity.setProgress(psActivity.getProgress());
        activity.setTotal(psActivity.getTotal());
        activity.setBegin_date(psActivity.getBegin_date());
        activity.setEnd_date(psActivity.getEnd_date());
        activity.setScore(psActivity.getScore());
        LessonBean lesson = new LessonBean();
        Lesson psLesson = psActivity.getLesson();
        lesson.setId(psLesson.getId());
        lesson.setName(psLesson.getName());
        lesson.setLevel(psLesson.getLevel());
        lesson.setType(psLesson.getType());
        lesson.setDescription(psLesson.getDescription());
        activity.setLesson(lesson);
    }
       
    public String retrieveActivity() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            Session psSession = HibernateSessionFactory.getSession();
            try {
                Activity psActivity = (Activity)psSession.get(Activity.class, activity_id);
                if (psActivity != null) {
                    if (psActivity.getUser().getId() == user.getUser_id()) {
                        activities = new ActivityBean[1];
                        activities[0] = new ActivityBean();
                        storeParameterIntoActivityBean(activities[0], psActivity);
                        Lesson psLesson = psActivity.getLesson();
                        List<LessonCode> psLessonCodes = psSession.createCriteria(LessonCode.class)
                                .add(Restrictions.eq("lesson", psLesson))
                                .addOrder(Order.asc("order"))
                                .list();
                        int size = psLessonCodes.size();
                        exercises = new ExerciseBean[size];
                        for (int i=0; i<size; i++) {
                            exercises[i] = new ExerciseBean();
                            getExercises()[i].setId(psLessonCodes.get(i).getId());
                            getExercises()[i].setHint(psLessonCodes.get(i).getHint());
                            getExercises()[i].setCode_id(psLessonCodes.get(i).getCode().getId());
                            getExercises()[i].setLevel(psLessonCodes.get(i).getCode().getLevel());
                            getExercises()[i].setType(psLessonCodes.get(i).getCode().getType());
                            getExercises()[i].setCredit(psLessonCodes.get(i).getCode().getCredit());
                            getExercises()[i].setDescription(psLessonCodes.get(i).getCode().getDescription());
                        }
                        message = "success";
                        status = true;
                    } else {
                        errorcode = 3;
                        message = "illegal parameter.";
                        status = false;
                    }
                } else {
                    errorcode = -1;
                    message = "Activity not exists.";
                    status = false;
                }   
            } catch (Exception ex) {
                throw ex;
            } finally {
                psSession.close();
            }
        } else {
            errorcode = 1;
            message = "You must login to complete this action.";
            status = false;
        }
        return SUCCESS;
    }
    
    public void setRequestcode(Integer requestcode) {
        this.requestcode = requestcode;
    }
    
    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ActivityBean[] getActivities() {
        return activities;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public Integer getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    public ExerciseBean[] getExercises() {
        return exercises;
    }

    
}
