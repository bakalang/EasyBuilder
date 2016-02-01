package net.skyee.jsch;


import com.jcraft.jsch.*;
import net.skyee.Context;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class JschAction extends JschBase{
    private Logger log = LoggerFactory.getLogger(JschAction.class);
    private Context context;
    private JschAction() {
        this.context = Context.getInstance();
    }

    public JschAction(String userid, String password, String host) throws JSchException {
        this.setup(userid, password, host);
    }


    public void sudo(String command) throws IOException {
        Channel channel = null;
        try {
            channel = session.openChannel("exec");


            if(SystemUtils.IS_OS_WINDOWS)
                command = "cmd.exe /C "+command;
            ((ChannelExec)channel).setCommand(command);

            InputStream in=channel.getInputStream();
            ((ChannelExec)channel).setErrStream(System.err);
            channel.connect();

            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    log.info("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public void sftp(String srcFile, String destFolder) throws JSchException, IOException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();

        ChannelSftp sftp = (ChannelSftp) channel;
        sftp.put(srcFile, destFolder);
        channel.disconnect();
    }

    public static void main(String[] args) throws JSchException, IOException, SftpException {
        JschAction ja = new JschAction("administrator", "12345", "192.168.1.226");
        ja.sudo("echo !!!");
        ja.sftp("E:\\start_shipyard", "ManagerStudio");
        ja.sessionDisconnect();
    }
}
