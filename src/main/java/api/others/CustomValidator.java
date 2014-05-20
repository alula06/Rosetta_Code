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

import java.util.Date;
import java.util.concurrent.TimeUnit;
import model.Challenge;
import model.User;


public class CustomValidator {
    
    public static boolean isInFilter(char ch, String filter) {
        for (int i=0; i<filter.length(); i++) {
            if (ch == filter.charAt(i))
                return true;
        }
        return false;
    }
    
    public static boolean isEmail(String email) {
        int indexOfAt, indexOfDot, length;
        length = email.length();
        if (length < 5 || length > 40) return false;
        indexOfAt = email.indexOf('@');
        indexOfDot = email.lastIndexOf('.');
        if (indexOfAt <= 0 || indexOfAt >= indexOfDot-1 || indexOfDot == length-1) return false;
        for (int i=indexOfDot+1; i<length; i++) {
            if (!Character.isLetter(email.charAt(i))) return false;
        }
        if(!Character.isLetterOrDigit(email.charAt(0)) || !Character.isLetterOrDigit(email.charAt(indexOfAt+1))) return false;
        for (int i=0; i<indexOfAt; i++) {
            if (!Character.isLetterOrDigit(email.charAt(i)) && !isInFilter(email.charAt(i), "-_."))
                return false;
        }
        for (int i=indexOfAt+1; i<indexOfDot; i++) {
            if (!Character.isLetterOrDigit(email.charAt(i)) && !isInFilter(email.charAt(i), "-_."))
                return false;
        }
        return true;
    }
    
    public static boolean isPassword(String password) {
        int length = password.length();
        String filter = ".<>[]{}-_=+()*&^%$#@!~,'";
        if (length == 0) return false;
        for (int i=0; i<length; i++) {
            if (!Character.isLetterOrDigit(password.charAt(i)) && !isInFilter(password.charAt(i), filter))
                return false;
        }
        return true;
    }
    
    public static boolean isUsername(String username) {
        int length = username.length();
        String filter = " -'";
        if (length == 0 || length > 20) return false;
        if (username.charAt(0) == ' ') return false;
        for (int i=0; i<length; i++) {
            if (!Character.isLetter(username.charAt(i)) && !isInFilter(username.charAt(i), filter))
                return false;
        }
        return true;
    }
    
    
}
