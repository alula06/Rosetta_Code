/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.SessionUserBean;
import api.others.CustomValidator;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;
import java.util.List;

import java.util.Map;
import model.Activity;
import model.Lesson;
import model.User;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zi sheng Wu
 */
public class RegisterAction extends ActionSupport {
    
    // inputs
    private String email; // email
    private String password; // password
    private String username; // user's name
    // outputs
    private boolean status;
    private String message;
    // others
    private SessionUserBean user;
    
    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user == null) {
            if (isLegalInfo()) {
                Session psSession = HibernateSessionFactory.getSession();
                Transaction transaction = null;
                try {
                    List<User> psUserList = psSession.createCriteria(User.class)
                            .add(Restrictions.or(Restrictions.eq("email", email),Restrictions.eq("username", username)))
                            .list();
                    if (psUserList.isEmpty()) {
                        transaction = psSession.beginTransaction();
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setUsername(username);
                        newUser.setPassword(password);
                        newUser.setLevel(1);
                        newUser.setScore(0);
                        newUser.setType(1);
                        newUser.setRegister_date(new Date());
                        psSession.save(newUser);
                        /**************************/
                        List<Lesson> psLessons = psSession.createCriteria(Lesson.class).list();
                        int size = psLessons.size();
                        for (int i=0; i<size; i++) {
                            Activity newActivity = new Activity();
                            newActivity.setProgress(0);
                            newActivity.setScore(0);
                            newActivity.setTotal(psLessons.get(i).getLessoncodes().size());
                            newActivity.setBegin_date(new Date());
                            newActivity.setLesson(psLessons.get(i));
                            newActivity.setUser(newUser);
                            psSession.save(newActivity);
                        }
                        /**************************/
                        user = new SessionUserBean();
                        user.setEmail(email);
                        user.setUser_id(newUser.getId());
                        user.setLevel(newUser.getLevel());
                        user.setType(newUser.getType());
                        user.setUsername(username);
                        user.setLogin_date(new Date());
                        user.getLogin_date().toString();
                        session.put("user", user);
                        /**************************/
                        transaction.commit();
                        message = "Registered successfully.";
                        status = true;
                    } else {
                        message = "Email or username has existed.";
                        status = false;
                    }
                } catch (Exception ex) {
                    if (transaction != null) transaction.rollback();
                    throw ex;
                } finally {
                    psSession.close();
                }
            } else {
                status = false;
            }
        } else {
            message = "You already logged in.";
            status = false;
        }
        return SUCCESS;
    }
    
    public String update() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            if (isLegalInfo()) {
                Session psSession = HibernateSessionFactory.getSession();
                Transaction transaction = null;
                try {
                    User psUser = (User)psSession.get(User.class, user.getUser_id());
                    if (psUser != null) {
                        transaction = psSession.beginTransaction();
                        psUser.setEmail(email);
                        psUser.setPassword(password);
                        user.setEmail(email);
                        transaction.commit();
                        message = "Update successfully.";
                        status = true;
                    } else {
                        message = "System Error.";
                        status = false;
                    }
                } catch (Exception ex) {
                    if (transaction != null) transaction.rollback();
                    throw ex;
                } finally {
                    psSession.close();
                }
            } else {
                status = false;
            }
        } else {
            message = "You must log in to continue the action.";
            status = false;
        }
        return SUCCESS;
    }
    
    private boolean isLegalInfo() {
        if (email != null && password != null && username != null) {
            if(!CustomValidator.isEmail(email)) {
                message = "Illegal email.";
                return false;
            }
            if(!CustomValidator.isPassword(password)) {
                message = "Illegal password";
                return false;
            }
            if(!CustomValidator.isUsername(username)) {
                message = "Illegal username";
                return false;
            }
        } else {
            message = "username, password or email cannot be blank.";
            return false;
        }
        return true;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
