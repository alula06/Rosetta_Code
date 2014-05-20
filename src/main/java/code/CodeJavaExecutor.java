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

import code.interfaces.iCodeExecutor;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import support.Constants;


public class CodeJavaExecutor implements iCodeExecutor {
    
    private int status = -1;
    private String path = null;
    private String filenameWithoutExt = null;
    private String[] options;
    private String errorMessage = "";
    private String outputMessage = "";
    private String message;
    private String command = "";
    private List<String> params;
    
    public CodeJavaExecutor(String path, String filenameWithoutExt) {
        this(path, filenameWithoutExt, null);
    }
    
    public CodeJavaExecutor(String path, String filenameWithoutExt, String[] options) {
        this.options = options;
        this.path = path;
        this.filenameWithoutExt = filenameWithoutExt;
        
    }
            
    @Override
    public boolean execute() {
        /* // add more options
        params = new ArrayList<String>();
        params.addAll(Arrays.asList("java", "-cp", this.path, "-Xmx128M", "-Xms16M"));
        command = "java -cp . -Xmx128M -Xms16M ";
        for (String s : this.options) {
            command += s + " ";
            params.add(s);
        }
        command += this.filenameWithoutExt;
        params.add(this.filenameWithoutExt);
        */
        command = "java -Xmx128M -Xms16M " + this.filenameWithoutExt;
        if(FileUtils.waitFor(new File(this.path+this.filenameWithoutExt+".class"), Constants.FILE_EXECUTION_WAIT_TIME)) {
            Runtime runtime = Runtime.getRuntime();
            Process process = null;        
            try {
                params = Arrays.asList("java", "-cp", this.path, "-Xmx128M", "-Xms16M", "-Djava.security.manager", this.filenameWithoutExt);
                process = new ProcessBuilder(params).start();

                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                waitForOrKill(process, TimeUnit.SECONDS.toMillis(Constants.EXECUTION_TIMEOUT));
                // Read and print the output
                String line = null;
                this.outputMessage = "";
                this.errorMessage = "";
                while ((line = in.readLine()) != null) {
                  this.outputMessage += line + "\n";
                }
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
            } catch (Exception e) {
                this.status = 3;
                this.errorMessage = e.toString();
            }
        }
        return false;
    }
    
    
    @Override
    public void setPath(String path) {
        this.path = path;
    }
    
    @Override
    public void setFilenameWithoutExt(String filenameWithoutExt) {
        this.filenameWithoutExt = filenameWithoutExt;
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

/* -------------------------------------- */  
    public static void waitForOrKill(Process self, long numberOfMillis) {
        ProcessRunner runnable = new ProcessRunner(self);
        Thread thread = new Thread(runnable);
        thread.start();
        runnable.waitForOrKill(numberOfMillis);
    }
    
    protected static class ProcessRunner implements Runnable {
        
        Process process;
        private boolean done;

        public ProcessRunner(Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            try {
                process.waitFor();
            } catch (InterruptedException ex) {
                // Ignore
            }
            synchronized (this) {
                notifyAll();
                done = true;
            }
        }

        public synchronized void waitForOrKill(long timeoutInMillis) {
            if (!done) {
                try {
                    wait(timeoutInMillis);
                } catch (InterruptedException e) {
                    // Ignore
                }
                if (!done) {
                    process.destroy();
                }
            }
        }
    }
/* -------------------------------------- */
    
}
