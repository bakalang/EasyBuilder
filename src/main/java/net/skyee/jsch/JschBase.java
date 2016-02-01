package net.skyee.jsch;


import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import net.skyee.bean.JschUserInfo;

public class JschBase {

    protected Session session = null;

    public void setup(String userid, String password, String host) throws JSchException {
//        if(session.isConnected())
//            return session;

        JSch jsch = new JSch();
        session=jsch.getSession(userid, host, 22);
        session.setUserInfo(new JschUserInfo(password));
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
//        return session;
    }
    public void sessionDisconnect(){
        session.disconnect();
    }

}
