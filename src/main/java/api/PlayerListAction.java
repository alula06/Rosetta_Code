/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.PlayerBean;
import api.bean.SessionUserBean;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
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
public class PlayerListAction extends ActionSupport {
    // outputs
    private boolean status;
    private String message;
    private int errorcode;
    private PlayerBean[] players;
    // others
    private SessionUserBean user;
    
    @Override
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            Session psSession = HibernateSessionFactory.getSession();
            try {
                List<User> psUsers = psSession.createCriteria(User.class)
                        .add(Restrictions.and(Restrictions.eq("type",user.getType()),Restrictions.ge("level",user.getLevel())))
                        .list();
                int size = psUsers.size();
                if (size > 0) {
                    players = new PlayerBean[size];
                    for (int i=0; i<size; i++) {
                        players[i] = new PlayerBean();
                        players[i].setId(psUsers.get(i).getId());
                        players[i].setName(psUsers.get(i).getUsername());
                        players[i].setScore(psUsers.get(i).getScore());
                        players[i].setLevel(psUsers.get(i).getLevel());
                        players[i].setType(psUsers.get(i).getType());
                        players[i].setRegister_date(psUsers.get(i).getRegister_date());
                    }
                    message = "success";
                    status = true;
                } else {
                    message = "no player found.";
                    status = false;
                }
            } catch (Exception ex) {
                throw ex;
            } finally {
                psSession.close();
            }
        } else {
            message = "You must login to complete this action.";
            status = false;
        }
        return SUCCESS;
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

    public PlayerBean[] getPlayers() {
        return players;
    }
    
}
