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

import code.interfaces.iCodeCompiler;
import code.interfaces.iCodeExecutor;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import support.Constants;


public class CodeProcessor {
    
    //private int user_id;
    private String sourcecode;
    private String filename;
    private int type;
    private String path;
    private boolean isCreated;
    private boolean isCompiled;
    private boolean isExecuted;
    private iCodeCompiler ccr;
    private iCodeExecutor cer;
    
    public CodeProcessor() {}
    
    public CodeProcessor(int user_id, String sourcecode, String filename, int type) {
        this.sourcecode = sourcecode;
        this.filename = filename;
        this.type = type;
        path = System.getenv("OPENSHIFT_DATA_DIR");
        if (path == null) {
            path = System.getProperty("catalina.base") + "/data/" + user_id + "/";
        } else {
            path = path + "data/" + user_id + "/";
        }
    }
    
    public int getStatus() {
        if (isExecuted) {
            return 3;
        } else if (isCompiled) {
            return 2;
        } else if (isCreated) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public String getCompileErrorMessage() {
        return ccr.getErrorMessage();
    }
    
    public String getExecuteErrorMessage() {
        return cer.getErrorMessage();
    }
    
    public String getExecuteOutputMessage() {
        return cer.getOutputMessage();
    }
    
    public String getCompilerCommand() {
        return ccr.getCommand();
    }
    
    public String getExecutionCommand() {
        return cer.getCommand();
    }
    
    public String getCompilerMessage() {
        return ccr.getMessage();
    }
    
    public String getExecutionMessage() {
        return cer.getMessage();
    }
    
    public boolean createSourceFile() throws IOException {
        try {
            if (type == 1) {
                File srcfile = new File(path+filename+".java");
                FileUtils.writeStringToFile(srcfile, sourcecode, "UTF-8");
                if (FileUtils.waitFor(srcfile, Constants.FILE_CREATION_WAIT_TIME)) isCreated = true;
            }
        } catch (IOException ex) {
            throw ex;
            //Logger.getLogger(CodeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isCreated;
    }
    
    public boolean compile() {
        if (type == 1) {
            ccr = new CodeJavaCompiler(path+filename);
            isCompiled = ccr.compile();
        }
        return isCompiled;
    }
    
    public boolean execute() {
        if(type == 1) {
            cer = new CodeJavaExecutor(path,filename);
            isExecuted = cer.execute();
        }
        return isExecuted;
    }
    
    public void clear() throws IOException {
        try {
            FileUtils.deleteDirectory(new File(path));
        } catch (IOException ex) {
            throw ex;
            //Logger.getLogger(CodeProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
