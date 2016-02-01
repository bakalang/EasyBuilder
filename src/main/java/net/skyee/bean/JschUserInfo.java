package net.skyee.bean;

import com.jcraft.jsch.UserInfo;


public class JschUserInfo implements UserInfo {

    String password = null;

    public JschUserInfo(String password) {
        this.password = password;
    }

    public String getPassphrase() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public boolean promptPassword(String s) {
        return false;
    }

    public boolean promptPassphrase(String s) {
        return false;
    }

    public boolean promptYesNo(String s) {
        return false;
    }

    public void showMessage(String s) {
        System.out.println("showMessage="+s);
    }
}
