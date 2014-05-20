/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zi sheng Wu
 */

package api.others;

import support.Constants;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import model.Challenge;


public class ChallengeSupporter {
    
    public static int isLegal(Date request_time, int user_id, Challenge psChallenge) {
        if (psChallenge.getOffense().getId() == user_id) {
            if (psChallenge.getOffense_begin_time() != null) {
                if (psChallenge.isOffense_closed()) return 1;
                if (psChallenge.getOffense_end_time() != null) return 2;
                if (psChallenge.getOffense_attempt() >= Constants.CHALLENGE_MAX_ATTEMPT) return 3;
                if (TimeUnit.MILLISECONDS.toSeconds(request_time.getTime() - psChallenge.getOffense_begin_time().getTime()) > Constants.CHALLENGE_MAX_DURATION) return 4;
            }
        } else if (psChallenge.getDefense().getId() == user_id) {
            if (psChallenge.getDefense_begin_time() != null) {
                if (psChallenge.isDefense_closed()) return 1;
                if (psChallenge.getDefense_end_time() != null) return 2;
                if (psChallenge.getDefense_attempt() >= Constants.CHALLENGE_MAX_ATTEMPT) return 3;
                if (TimeUnit.MILLISECONDS.toSeconds(request_time.getTime() - psChallenge.getDefense_begin_time().getTime()) > Constants.CHALLENGE_MAX_DURATION) return 4;
            }
        } else {
            return -1;
        }
        return 0;
    }
    
    public static void close (int user_id, Challenge psChallenge) {
        if (psChallenge.getOffense().getId() == user_id) {
            psChallenge.setOffense_closed(true);
        } else if (psChallenge.getDefense().getId() == user_id) {
            psChallenge.setDefense_closed(true);
        }
        determineWinner(psChallenge);
    }
    
    public static void determineWinner(Challenge psChallenge) {
        if (psChallenge.isOffense_closed() && psChallenge.isDefense_closed()) {
            if (psChallenge.getOffense_end_time() != null) {
                if (psChallenge.getDefense_end_time() != null) {
                    if (TimeUnit.MILLISECONDS.toSeconds(psChallenge.getOffense_end_time().getTime()-psChallenge.getOffense_begin_time().getTime()) 
                            < TimeUnit.MILLISECONDS.toSeconds(psChallenge.getDefense_end_time().getTime()-psChallenge.getDefense_begin_time().getTime())) {
                        psChallenge.setWinner(psChallenge.getOffense());
                    } else {
                        psChallenge.setWinner(psChallenge.getDefense());
                    }
                } else {
                    psChallenge.setWinner(psChallenge.getOffense());
                }
            } else {
                if (psChallenge.getDefense_end_time() != null) {
                    psChallenge.setWinner(psChallenge.getDefense());
                }
            }
            if (psChallenge.getWinner() != null) {
                psChallenge.getWinner().setScore(psChallenge.getWinner().getScore()+psChallenge.getCode().getCredit());
            }
        }
    }
    
    public static void increaseAttempt(int user_id, Challenge psChallenge) {
        if (psChallenge.getOffense().getId() == user_id) {
            psChallenge.setOffense_attempt(psChallenge.getOffense_attempt()+1);
        } else if (psChallenge.getDefense().getId() == user_id) {
            psChallenge.setDefense_attempt(psChallenge.getDefense_attempt()+1);
        }
    }

    public static void finish(int user_id, Date request_time, Challenge psChallenge) {
        if (psChallenge.getOffense().getId() == user_id) {
            psChallenge.setOffense_end_time(request_time);
            psChallenge.setOffense_closed(true);
        } else if (psChallenge.getDefense().getId() == user_id) {
            psChallenge.setDefense_end_time(request_time);
            psChallenge.setDefense_closed(true);
        }
        determineWinner(psChallenge);
    }
    
}
