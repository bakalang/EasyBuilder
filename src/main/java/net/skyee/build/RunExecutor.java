package net.skyee.build;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class RunExecutor {
    Logger log = LoggerFactory.getLogger(RunExecutor.class);
    int iExitValue;

    public String runScript(String command) {
        if(SystemUtils.IS_OS_WINDOWS)
            command = "cmd.exe /C "+command;

        CollectingLogOutputStream clos = new CollectingLogOutputStream();
        CommandLine cl = CommandLine.parse(command);
        DefaultExecutor de = new DefaultExecutor();
        PumpStreamHandler psh = new PumpStreamHandler(clos);
        de.setExitValue(0);
        de.setStreamHandler(psh);

        try {
            iExitValue = de.execute(cl);
        } catch (ExecuteException e) {
            log.info("Execution failed.");
            e.printStackTrace();
        } catch (IOException e) {
            log.info("permission denied.");
            e.printStackTrace();
        }
        return String.valueOf(iExitValue);
    }

    public static void main(String args[])
    {
        RunExecutor re = new RunExecutor();
        re.runScript("ant -version");
    }
}
