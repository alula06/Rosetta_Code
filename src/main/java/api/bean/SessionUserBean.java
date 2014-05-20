/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api.bean;

/**
 *
 * @author Zi sheng Wu
 */
public class SessionUserBean {
    private int user_id;
    private String email;
    private String username;
    private int level;
    private int type;
    private java.util.Date login_date;
    
    public SessionUserBean(){}
    
    public SessionUserBean(int user_id, int level, int type, String email, String username, java.util.Date login_date) {
        this.user_id = user_id;
        this.level = level;
        this.type = type;
        this.email = email;
        this.username = username;
        this.login_date = login_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public java.util.Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(java.util.Date login_date) {
        this.login_date = login_date;
    }   

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
