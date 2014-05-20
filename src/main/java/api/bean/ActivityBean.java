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
public class ActivityBean {
    
    private int id;
    private int progress;
    private int total;
    private int score;
    private java.util.Date begin_date;
    private java.util.Date end_date;
    private LessonBean lesson;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public java.util.Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(java.util.Date begin_date) {
        this.begin_date = begin_date;
    }

    public java.util.Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(java.util.Date end_date) {
        this.end_date = end_date;
    }

    public LessonBean getLesson() {
        return lesson;
    }

    public void setLesson(LessonBean lesson) {
        this.lesson = lesson;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}
