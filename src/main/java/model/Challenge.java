/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Date;

/**
 *
 * @author Zi sheng Wu
 */
public class Challenge {
    private Integer id;
    private Code code;
    private Date added_date;
    private User winner;
    private User offense;
    private Date offense_begin_time;
    private Date offense_end_time;
    private Integer offense_attempt;
    private boolean offense_closed;
    private User defense;
    private Date defense_begin_time;
    private Date defense_end_time;
    private Integer defense_attempt;
    private boolean defense_closed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public Date getAdded_date() {
        return added_date;
    }

    public void setAdded_date(Date added_date) {
        this.added_date = added_date;
    }

    public User getWinner() {
        return winner;
    }

    public void setWinner(User winner) {
        this.winner = winner;
    }

    public User getOffense() {
        return offense;
    }

    public void setOffense(User offense) {
        this.offense = offense;
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

    public Integer getOffense_attempt() {
        return offense_attempt;
    }

    public void setOffense_attempt(Integer offense_attempt) {
        this.offense_attempt = offense_attempt;
    }

    public User getDefense() {
        return defense;
    }

    public void setDefense(User defense) {
        this.defense = defense;
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

    public Integer getDefense_attempt() {
        return defense_attempt;
    }

    public void setDefense_attempt(Integer defense_attempt) {
        this.defense_attempt = defense_attempt;
    }

    public boolean isOffense_closed() {
        return offense_closed;
    }

    public void setOffense_closed(boolean offense_closed) {
        this.offense_closed = offense_closed;
    }

    public boolean isDefense_closed() {
        return defense_closed;
    }

    public void setDefense_closed(boolean defense_closed) {
        this.defense_closed = defense_closed;
    }
}
