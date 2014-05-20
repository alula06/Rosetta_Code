/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package code.interfaces;

/**
 *
 * @author Zi sheng Wu
 */
public interface iCodeExecutor {
    public boolean execute();
    public void setPath(String path);
    public void setFilenameWithoutExt(String filenameWithoutExt);
    public void setOptions(String[] options);
    public int getStatus();
    public String getCommand();
    public String getMessage();
    public String getErrorMessage();
    public String getOutputMessage();
}
