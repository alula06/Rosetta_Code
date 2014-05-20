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
public interface iCodeCompiler {
    public boolean compile();
    public void setFileLocation(String fileLocationWithoutExt);
    public void setOptions(String[] options);
    public int getStatus();
    public String getCommand();
    public String getMessage();
    public String getErrorMessage();
    public String getOutputMessage();
}
