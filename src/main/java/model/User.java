/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Zi sheng Wu
 */
public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private Integer level;
    private Integer type;
    private Integer score;
    private Date register_date;
    private Set<Challenge> challenge_defense;
    private Set<Challenge> challenge_offense;
    //private Set<Challenge> challenge_winner;
    private Set<Activity> lesson_activities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Set<Challenge> getChallenge_defense() {
        return challenge_defense;
    }

    public void setChallenge_defense(Set<Challenge> challenge_defense) {
        this.challenge_defense = challenge_defense;
    }

    public Set<Challenge> getChallenge_offense() {
        return challenge_offense;
    }

    public void setChallenge_offense(Set<Challenge> challenge_offense) {
        this.challenge_offense = challenge_offense;
    }

    public Set<Activity> getLesson_activities() {
        return lesson_activities;
    }

    public void setLesson_activities(Set<Activity> lesson_activities) {
        this.lesson_activities = lesson_activities;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }
/*
    public Set<Challenge> getChallenge_winner() {
        return challenge_winner;
    }

    public void setChallenge_winner(Set<Challenge> challenge_winner) {
        this.challenge_winner = challenge_winner;
    }
*/
}
