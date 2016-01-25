package net.skyee;


import net.skyee.dao.ProjectCheckoutDAO;
import net.skyee.dao.ProjectConfigurationHstoryDAO;
import net.skyee.dao.ProjectDAO;
import net.skyee.dao.ProjectDependenceDAO;
import org.skife.jdbi.v2.DBI;

public class Context extends SampleConf {
    private static Context context;
    private ProjectDAO projectDAO;
    private SampleConf configration;
    private ProjectDependenceDAO projectDependenceDAO;
    private ProjectCheckoutDAO projectCheckoutDAO;
    private ProjectConfigurationHstoryDAO projectConfigurationHstoryDAO;
    private DBI dbInterface;

    public static Context getInstance() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

    public Context updateDBInterface(DBI dbInterface) {
        this.dbInterface = dbInterface;
        return this;
    }

    public SampleConf getConfigration() {
        return configration;
    }

    public Context setConfigration(SampleConf configration) {
        this.configration = configration;
        return this;
    }

    public ProjectDAO getProjectDAO() throws ClassNotFoundException {

        if (projectDAO == null) {
            projectDAO = dbInterface.onDemand(ProjectDAO.class);
        }
        return projectDAO;
    }

    public ProjectDependenceDAO getProjectDependenceDAO() throws ClassNotFoundException {

        if (projectDependenceDAO == null) {
            projectDependenceDAO = dbInterface.onDemand(ProjectDependenceDAO.class);
        }
        return projectDependenceDAO;
    }

    public ProjectCheckoutDAO getProjectCheckoutDAO() throws ClassNotFoundException {

        if (projectCheckoutDAO == null) {
            projectCheckoutDAO = dbInterface.onDemand(ProjectCheckoutDAO.class);
        }
        return projectCheckoutDAO;
    }

    public ProjectConfigurationHstoryDAO getProjectConfigurationHstoryDAO() throws ClassNotFoundException {

        if (projectConfigurationHstoryDAO == null) {
            projectConfigurationHstoryDAO = dbInterface.onDemand(ProjectConfigurationHstoryDAO.class);
        }
        return projectConfigurationHstoryDAO;
    }

}
