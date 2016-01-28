package net.skyee.bean;

/**
 * Created by Sky on 2016/1/28.
 */
public class Config {

    private String config;
    private String action;

    public Config(String config, String action) {
        this.config = config;
        this.action = action;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
