package net.skyee.bean;


import java.util.ArrayList;
import java.util.List;

public class BuildJob {
    private List<Component> components = new ArrayList<Component>();
    private String remark;

    public BuildJob(String remark) {
        this.remark = remark;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public void addComponents(Component component) {
        if(null != component)
            this.components.add(component);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
