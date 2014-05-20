/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package code;

import code.interfaces.iCodeCompiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.FileUtils;
import support.Constants;

/**
 *
 * @author Zi sheng Wu
 */
public class CodeJavaCompiler implements iCodeCompiler {

    private int status = -1;
    private String fileLocation = null;
    private String[] options = null;
    private String errorMessage = "";
    private String outputMessage = "";
    private String message;
    private String command;
    
    public CodeJavaCompiler(String fileLocationWithoutExt) {
        this(fileLocationWithoutExt, null);
    }
    
    public CodeJavaCompiler(String fileLocationWithoutExt, String[] options) {
        this.options = options;
        this.fileLocation = fileLocationWithoutExt + ".java";
    }
            
    @Override
    public boolean compile() {
        command = "javac "+ fileLocation;
        if(FileUtils.waitFor(new File(fileLocation), Constants.FILE_COMPILER_WAIT_TIME)) {
            Runtime runtime = Runtime.getRuntime();
            Process process = null;        
            try {
                process = runtime.exec(command);
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                // Read and print the output
                String line = null;
                this.errorMessage = "";
                while ((line = err.readLine()) != null) {
                  this.errorMessage += line + "\n";
                }
                if ("".equals(this.errorMessage)) {
                    this.status = 1;
                    return true;
                } else {
                    this.status = 2;
                    return false;
                }
            } catch (IOException e) {
                this.status = 3;
                this.errorMessage = e.toString();
            }
        } else {
            message = "File not exists.";
        }
        return false;
    }

    @Override
    public void setFileLocation(String fileLocationWithoutExt) {
        this.fileLocation = fileLocationWithoutExt + ".java";
    }
    
    @Override
    public void setOptions(String[] options) {
        this.options = options;
    }
    
    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getOutputMessage() {
        return this.outputMessage;
    }

    @Override
    public String getCommand() {
        return this.command;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
    
}
