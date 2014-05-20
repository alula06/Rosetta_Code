/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api;

import api.bean.ChallengeBean;
import api.bean.ChallengeInfoBean;
import api.bean.SessionChallengeBean;
import api.bean.SessionUserBean;
import api.others.ChallengeSupporter;
import support.Constants;
import api.others.CustomValidator;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import model.Code;
import model.Challenge;
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
public class ChallengeAction extends ActionSupport {
    // inouts
    private int level;
    private int type;
    private int defense_id;
    private int challenge_id;
    // outputs
    private boolean status;
    private String message;
    private int errorcode;
    private ChallengeBean challenge;
    private ChallengeInfoBean challengeinfo;
    //private ChallengeBean[] challenges;
    List<ChallengeBean> challenges;
    
    // others
    private SessionUserBean user;
    private SessionChallengeBean duel;
    private int user_id;
    private Date added_date;
    private Date request_time;
    
    public String create() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            if (user_id != defense_id) {
                Session psSession = HibernateSessionFactory.getSession();
                Transaction transaction = null;
                try {
                    User psUser = (User)psSession.get(User.class, user_id);
                    int limit = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.and(Restrictions.eq("offense_closed", false),Restrictions.eq("offense", psUser)))
                            .list().size();
                    if (limit >= Constants.CHALLENGE_OFFENSE_LIMIT) {
                        message = "You cannot challenge more than " + Constants.CHALLENGE_OFFENSE_LIMIT + " players at the same time.";
                        status = false;
                        return SUCCESS;
                    }
                    User psDefenseUser = null;
                    if (defense_id < 0) {
                        List<User> psUsers = psSession.createCriteria(User.class)
                            .add(Restrictions.eq("type",user.getType()))
                            .add(Restrictions.ge("level",user.getLevel()))
                            .add(Restrictions.ne("id", user_id))
                            .list();
                        int numOfUsers = psUsers.size();
                        if (numOfUsers > 0) {
                            psDefenseUser = psUsers.get((int)(numOfUsers * Math.random()));
                        } else {
                            errorcode = -1;
                            message = "Currently, no avialable palyer.";
                            return SUCCESS;
                        }
                    } else {
                        psDefenseUser = (User)psSession.get(User.class, defense_id);
                        if (psDefenseUser == null) {
                            errorcode = -1;
                            message = "No such palyer.";
                            return SUCCESS;
                        }
                    }
                    List<Code> psCodes = psSession.createCriteria(Code.class)
                                .add(Restrictions.and(Restrictions.eq("level", user.getLevel()), Restrictions.eq("type", user.getType())))
                                .list();
                    int size = psCodes.size();
                    if (psUser != null && psDefenseUser != null && size > 0) {
                        transaction = psSession.beginTransaction();
                        Challenge newDuel = new Challenge();
                        newDuel.setOffense(psUser);
                        newDuel.setOffense_attempt(0);
                        newDuel.setOffense_closed(false);
                        newDuel.setDefense(psDefenseUser);
                        newDuel.setDefense_attempt(0);
                        newDuel.setDefense_closed(false);
                        Code code = psCodes.get((int)(size * Math.random()));
                        newDuel.setCode(code);
                        added_date = new Date();
                        newDuel.setAdded_date(added_date);
                        code.getChallenges().add(newDuel);
                        psUser.getChallenge_offense().add(newDuel);
                        psDefenseUser.getChallenge_defense().add(newDuel);
                        psSession.save(newDuel);
                        transaction.commit();
                        challenge = new ChallengeBean();
                        storeParameterIntoChallengeBean(challenge, newDuel);
                        message = "success";
                        status = true;
                    } else {
                        message = "System Error.";
                        errorcode = -1;
                        status = false;
                    }
                } catch (Exception ex) {
                    if (transaction != null) transaction.rollback();
                    throw ex;
                } finally {
                    psSession.close();
                }
            } else {
                message = "illegal parameters.";
                errorcode = 3;
                status = false;
            }
        } else {
            message = "You must login to complete this action.";
            errorcode = 1;
            status = false;
        }
        return SUCCESS;
    }
    
    private void storeParameterIntoChallengeBean(ChallengeBean challenge, Challenge duel) {
        challenge.setId(duel.getId());
        challenge.setOffense_id(duel.getOffense().getId());
        challenge.setOffense_name(duel.getOffense().getUsername());
        challenge.setOffense_level(duel.getOffense().getLevel());
        challenge.setOffense_register_date(duel.getOffense().getRegister_date());
        challenge.setOffense_begin_time(duel.getOffense_begin_time());
        challenge.setOffense_end_time(duel.getOffense_end_time());
        challenge.setOffense_attempt(duel.getOffense_attempt());
        challenge.setOffense_closed(duel.isOffense_closed());
        challenge.setDefense_id(duel.getDefense().getId());
        challenge.setDefense_name(duel.getDefense().getUsername());
        challenge.setDefense_level(duel.getDefense().getLevel());
        challenge.setDefense_register_date(duel.getDefense().getRegister_date());
        challenge.setDefense_begin_time(duel.getDefense_begin_time());
        challenge.setDefense_end_time(duel.getDefense_end_time());
        challenge.setDefense_attempt(duel.getDefense_attempt());
        challenge.setDefense_closed(duel.isDefense_closed());
        if (duel.getWinner() != null) {
            challenge.setWinner_id(duel.getWinner().getId());
            challenge.setWinner_name(duel.getWinner().getUsername());
        }
        challenge.setCredit(duel.getCode().getCredit());
        challenge.setLevel(duel.getCode().getLevel());
    }
    
    public String start() throws Exception {
        request_time = new Date();
        Map session = ActionContext.getContext().getSession();
        duel = (SessionChallengeBean)session.get("duel");
        if (duel != null) {
            session.remove(duel);
            duel = null;
        }
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            Session psSession = HibernateSessionFactory.getSession();
            Transaction transaction = null;
            try {
                Challenge psDuel = (Challenge)psSession.get(Challenge.class, challenge_id);
                if (psDuel != null) {
                    int legalcode = ChallengeSupporter.isLegal(request_time, user_id, psDuel);
                    if (legalcode == 0) {
                        transaction = psSession.beginTransaction();
                        challengeinfo = new ChallengeInfoBean();
                        challengeinfo.setId(challenge_id);
                        challengeinfo.setLevel(psDuel.getCode().getLevel());
                        challengeinfo.setType(psDuel.getCode().getType());
                        challengeinfo.setCredit(psDuel.getCode().getCredit());
                        challengeinfo.setDescription(psDuel.getCode().getDescription());
                        if (psDuel.getOffense().getId() == user_id) {
                            if(psDuel.getOffense_begin_time() == null) {
                                psDuel.setOffense_begin_time(request_time);
                            }
                            challengeinfo.setBegin_time(psDuel.getOffense_begin_time());
                            challengeinfo.setSeconds((long)(Constants.CHALLENGE_MAX_DURATION - TimeUnit.MILLISECONDS.toSeconds(request_time.getTime() - psDuel.getOffense_begin_time().getTime()) - 1));
                            challengeinfo.setAttempt(psDuel.getOffense_attempt());
                        } else if (psDuel.getDefense().getId() == user_id) {
                            if(psDuel.getDefense_begin_time() == null) {
                                psDuel.setDefense_begin_time(request_time);
                            }
                            challengeinfo.setBegin_time(psDuel.getDefense_begin_time());
                            challengeinfo.setSeconds((long)(Constants.CHALLENGE_MAX_DURATION - TimeUnit.MILLISECONDS.toSeconds(request_time.getTime() - psDuel.getDefense_begin_time().getTime()) - 1));
                            challengeinfo.setAttempt(psDuel.getDefense_attempt());
                        } else {
                            message = "illegal parameter.";
                            errorcode = 3;
                            status = false;
                            return SUCCESS;
                        }
                        challenge = new ChallengeBean();
                        storeParameterIntoChallengeBean(challenge, psDuel);
                        transaction.commit();
                        duel = new SessionChallengeBean();
                        duel.setId(challenge_id);
                        duel.setCode_id(psDuel.getCode().getId());
                        duel.setType(challengeinfo.getType());
                        duel.setAttempt(challengeinfo.getAttempt());
                        duel.setBegin_time(request_time);
                        session.put("duel", duel);
                        message = "challenge started.";
                        status = true;
                    } else if (legalcode > 0) {
                        transaction = psSession.beginTransaction();
                        ChallengeSupporter.close(user_id, psDuel);
                        transaction.commit();
                        errorcode = 2;
                        message = "The challenge expired or closed.";
                        status = false;
                    } else {
                        errorcode = 3;
                        message = "illegal parameter.";
                        status = false;
                    }
                } else {
                    message = "challenge not exists.";
                    errorcode = -1;
                    status = false;
                }
            } catch (Exception ex) {
                if (transaction != null) transaction.rollback();
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

    public String list() throws Exception {
        Map session = ActionContext.getContext().getSession();
        user = (SessionUserBean)session.get("user");
        if (user != null) {
            user_id = user.getUser_id();
            Session psSession = HibernateSessionFactory.getSession();
            Transaction transaction = null;
            try {
                User psUser = (User)psSession.get(User.class, user_id);
                if (psUser != null) {
                    List<Challenge> psChallenges = psSession.createCriteria(Challenge.class)
                            .add(Restrictions.or(
                                            Restrictions.and(Restrictions.eq("offense", psUser),
                                                    Restrictions.eq("offense_closed", false)),
                                            Restrictions.and(Restrictions.eq("defense", psUser),
                                                    Restrictions.eq("defense_closed", false))
                            ))
                            .addOrder(Order.asc("id"))
                            .list();
                    int size = psChallenges.size();
                    if (size == 0) {
                        message = "No challenge found.";
                        status = false;
                        return SUCCESS;
                    }
                    challenges = new ArrayList<ChallengeBean>();
                    transaction = psSession.beginTransaction();
                    for (int i=0; i<size; i++) {
                        int legalcode = ChallengeSupporter.isLegal(new Date(), user_id, psChallenges.get(i));
                        if (legalcode > 0) {
                            ChallengeSupporter.close(user_id, psChallenges.get(i));
                        } else if (legalcode == 0) {
                            ChallengeBean newChallengeBean = new ChallengeBean();
                            storeParameterIntoChallengeBean(newChallengeBean,psChallenges.get(i));
                            challenges.add(newChallengeBean);
                        } else {
                            message = "System Error.";
                            errorcode = -1;
                            status = false;
                            return SUCCESS;
                        }
                    }
                    transaction.commit();
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
    
    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public ChallengeBean getChallenge() {
        return challenge;
    }
    
    public ChallengeInfoBean getChallengeinfo() {
        return challengeinfo;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setDefense_id(int defense_id) {
        this.defense_id = defense_id;
    }

    public void setChallenge_id(int challenge_id) {
        this.challenge_id = challenge_id;
    }

    public List<ChallengeBean> getChallenges() {
        return challenges;
    }
    
}
