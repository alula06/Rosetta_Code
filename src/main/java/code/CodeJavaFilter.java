/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zi sheng Wu
 */

package code;


public class CodeJavaFilter {
    
    public static String getClassname(String sourcecode) {
        int i=-1, j=-1, k=-1;
        i = sourcecode.indexOf("public");
        j = sourcecode.indexOf("class");
        k = sourcecode.indexOf("{");
        if (i<0 || j<0 || k<0) return null;
        if (i>j || j>k) return null;
        for (int x=i+6; x<j; x++) {
            if (sourcecode.charAt(x) != ' ') return null;
        }
        if (sourcecode.charAt(j+5) != ' ') return null;
        String classname = sourcecode.substring(j+6,k).trim();
        int len = classname.length();
        if (len==0) return null;
        if (!Character.isLetter(classname.charAt(0))) return null;
        return classname;
    }
    
    public static boolean filter(String sourcecode) {

        return true;
    }
    
}
