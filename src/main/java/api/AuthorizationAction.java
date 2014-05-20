/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.SessionChallengeBean;
import api.bean.SessionUserBean;
import static com.opensymphony.xwork2.Action.SUCCESS;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;

import java.util.Date;
import java.util.List;
import java.util.Map;
import model.User;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Zi sheng Wu
 */
public class AuthorizationAction extends ActionSupport {
    
    // inputs
    private String email; // email
    private String password; // password
    // outputs
    private boolean status;
    private String username;
    private int user_id;
    private int level;
    private int type;
    private int score;
    private String message; // message
    private String date; // the date
    // others
    private SessionUserBean user;
    
    public String login() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user == null) {
            if (password != null && email != null) {
                Session psSession = HibernateSessionFactory.getSession();
                try {
                    List<User> psUserList = psSession.createCriteria(User.class)
                            .add(Restrictions.eq("email", email))
                            .list();
                    if (psUserList.size() == 1) {
                        User psUser = psUserList.get(0);
                        if (password.equals(psUser.getPassword())) {
                            user_id = psUser.getId();
                            username = psUser.getUsername();
                            level = psUser.getLevel();
                            type = psUser.getType();
                            score = psUser.getScore();
                            user = new SessionUserBean();
                            user.setEmail(email);
                            user.setUser_id(user_id);
                            user.setLevel(level);
                            user.setType(type);
                            user.setUsername(username);
                            user.setLogin_date(new Date());
                            date = user.getLogin_date().toString();
                            session.put("user", user);
                            message = username + " Logged in successfully!";
                            status = true;
                        } else {
                            message = "Password doesn't match!";
                            status = false;
                        }
                    } else {
                        message = "User not exists.";
                        status = false;
                    }
                } catch (Exception ex) {
                    throw ex;
                } finally {
                    psSession.close();
                }
            }
        } else {
            username = user.getUsername();
            date = user.getLogin_date().toString();
            message = "You already logged in.";
            status = true;
        }
        return SUCCESS;
    }
    
    public String logout() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        SessionChallengeBean duel = (SessionChallengeBean)session.get("duel");
        if (user != null) {
            message = user.getUsername() + " logged out successfully!";
            session.remove("user");
            user = null;
        } else {
            message = "No user logged in!";
        }
        if (duel != null) {
            session.remove("duel");
        }
        date = new Date().toString();
        status = true;
        return SUCCESS;
    }
    
    public boolean getStatus() {
        return status;
    }
    
    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
    
    public String getDate() {
        return date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getLevel() {
        return level;
    }

    public int getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    
}
