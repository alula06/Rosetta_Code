/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package api.bean;

import java.util.Date;

/**
 *
 * @author Zi sheng Wu
 */
public class ChallengeBean {
    private int id;
    private int offense_id;
    private String offense_name;
    private int offense_level;
    private Date offense_register_date;
    private Date offense_begin_time;
    private Date offense_end_time;
    private int offense_attempt;
    private boolean offense_closed;
    private int defense_id;
    private String defense_name;
    private int defense_level;
    private Date defense_register_date;
    private Date defense_begin_time;
    private Date defense_end_time;
    private int defense_attempt;
    private boolean defense_closed;
    private int winner_id;
    private String winner_name;
    private int credit;
    private int level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOffense_id() {
        return offense_id;
    }

    public void setOffense_id(int offense_id) {
        this.offense_id = offense_id;
    }

    public String getOffense_name() {
        return offense_name;
    }

    public void setOffense_name(String offense_name) {
        this.offense_name = offense_name;
    }

    public Date getOffense_begin_time() {
        return offense_begin_time;
    }

    public void setOffense_begin_time(Date offense_begin_time) {
        this.offense_begin_time = offense_begin_time;
    }

    public Date getOffense_end_time() {
        return offense_end_time;
    }

    public void setOffense_end_time(Date offense_end_time) {
        this.offense_end_time = offense_end_time;
    }

    public int getOffense_attempt() {
        return offense_attempt;
    }

    public void setOffense_attempt(int offense_attempt) {
        this.offense_attempt = offense_attempt;
    }

    public boolean isOffense_closed() {
        return offense_closed;
    }

    public void setOffense_closed(boolean offense_closed) {
        this.offense_closed = offense_closed;
    }

    public int getDefense_id() {
        return defense_id;
    }

    public void setDefense_id(int defense_id) {
        this.defense_id = defense_id;
    }

    public String getDefense_name() {
        return defense_name;
    }

    public void setDefense_name(String defense_name) {
        this.defense_name = defense_name;
    }

    public Date getDefense_begin_time() {
        return defense_begin_time;
    }

    public void setDefense_begin_time(Date defense_begin_time) {
        this.defense_begin_time = defense_begin_time;
    }

    public Date getDefense_end_time() {
        return defense_end_time;
    }

    public void setDefense_end_time(Date defense_end_time) {
        this.defense_end_time = defense_end_time;
    }

    public int getDefense_attempt() {
        return defense_attempt;
    }

    public void setDefense_attempt(int defense_attempt) {
        this.defense_attempt = defense_attempt;
    }

    public boolean isDefense_closed() {
        return defense_closed;
    }

    public void setDefense_closed(boolean defense_closed) {
        this.defense_closed = defense_closed;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public String getWinner_name() {
        return winner_name;
    }

    public void setWinner_name(String winner_name) {
        this.winner_name = winner_name;
    }

    public int getOffense_level() {
        return offense_level;
    }

    public void setOffense_level(int offense_level) {
        this.offense_level = offense_level;
    }

    public int getDefense_level() {
        return defense_level;
    }

    public void setDefense_level(int defense_level) {
        this.defense_level = defense_level;
    }

    public Date getOffense_register_date() {
        return offense_register_date;
    }

    public void setOffense_register_date(Date offense_register_date) {
        this.offense_register_date = offense_register_date;
    }

    public Date getDefense_register_date() {
        return defense_register_date;
    }

    public void setDefense_register_date(Date defense_register_date) {
        this.defense_register_date = defense_register_date;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
