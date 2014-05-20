/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.SessionUserBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Date;

import java.util.Map;
import model.Activity;
import model.Challenge;
import model.User;
import model.support.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author Zi sheng Wu
 */
public class ProfileAction extends ActionSupport {
    // outputs
    private boolean status;
    private int errorcode;
    private String message;
    private int user_id;
    private String email;
    private String username;
    private int score;
    private int level;
    private int type;
    private Date register_date;
    private int offense_win;
    private int offense_lose;
    private int offense_total;
    private int defense_win;
    private int defense_lose;
    private int defense_total;
    private int lessoncompleted;
    // others
    private SessionUserBean user;
    
    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            Session psSession = HibernateSessionFactory.getSession();
            try {
                User psUser = (User)psSession.get(User.class, user_id);
                if (psUser != null) {
                    username = psUser.getUsername();
                    email = psUser.getEmail();
                    score = psUser.getScore();
                    level = psUser.getLevel();
                    type = psUser.getType();
                    register_date = psUser.getRegister_date();
                    offense_total = psUser.getChallenge_offense().size();
                    offense_win = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.and(Restrictions.eq("offense", psUser), Restrictions.eq("winner", psUser)))
                            .list().size();
                    offense_lose = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.eq("offense", psUser))
                            .add(Restrictions.ne("winner", psUser))
                            //.add(Restrictions.ne("winner", null))
                            .list().size();
                    defense_total = psUser.getChallenge_defense().size();
                    defense_win = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.and(Restrictions.eq("defense", psUser), Restrictions.eq("winner", psUser)))
                            .list().size();
                    defense_lose = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.eq("defense", psUser))
                            .add(Restrictions.ne("winner", psUser))
                            //.add(Restrictions.ne("winner", null))
                            .list().size();
                    lessoncompleted = psSession.createCriteria(Activity.class)
                            .add(Restrictions.and(Restrictions.eq("user",psUser),Restrictions.eqProperty("progress", "total")))
                            .list().size();
                    message = "success";
                    status = true;
                } else {
                    message = "System Error.";
                    errorcode = -1;
                    status = false;
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                psSession.close();
            }
        } else {
            message = "You must login to complete this action.";
            errorcode = 1;
            status = false;
        }
        return SUCCESS;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getOffense_win() {
        return offense_win;
    }

    public int getOffense_total() {
        return offense_total;
    }

    public int getDefense_win() {
        return defense_win;
    }

    public int getDefense_total() {
        return defense_total;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public int getType() {
        return type;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public int getLessoncompleted() {
        return lessoncompleted;
    }

    public int getOffense_lose() {
        return offense_lose;
    }

    public int getDefense_lose() {
        return defense_lose;
    }
    
    
    
}
