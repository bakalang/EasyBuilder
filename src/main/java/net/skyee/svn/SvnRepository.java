package net.skyee.svn;

import net.skyee.Context;
import net.skyee.bean.BuildJob;
import net.skyee.bean.Component;
import net.skyee.bean.Login;
import net.skyee.bean.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SvnRepository {
    private Logger log = LoggerFactory.getLogger(SvnRepository.class);
    private Context context;

    public SvnRepository(Context context) {
        this.context= context;
    }


    public BuildJob test(Project p, String remark) throws Exception {

        // declare
        Login login = new Login(context.getConfigration().getSvnUser(), context.getConfigration().getSvnPwd());
        Map<Integer, String> dc = new LinkedHashMap<Integer, String>();
//        Project p = context.getProjectDAO().getProjectByModule(moduleName);
        getDependenceLoop(p.getProjectNo(), dc);
        dc.put(p.getProjectNo(), p.getRepository() + "/" + p.getModule());

        SVNAction svnaction = new SVNAction();
        svnaction.setAntBuild(true);
        BuildJob bj = new BuildJob(remark);
        for ( int projectNo : dc.keySet() )
        {
            String path = dc.get(projectNo);
            svnaction.setup(context.getConfigration().getSvnURL()+ "/" +path, login);

            // chechout
            bj.addComponents(svnaction.checkout(projectNo, path, projectNo==p.getProjectNo(), remark));
        }
        return bj;
    }

    private void getDependenceLoop(Integer projectNo, Map<Integer, String> dc) throws ClassNotFoundException
    {
        List<Project> tmpDepList = context.getProjectDependenceDAO().getDependenceProjectByProjectNo(projectNo);

        if ( tmpDepList != null )
        {
            for ( Project project : tmpDepList )
            {
                dc.put(project.getProjectNo(), project.getRepository() + "/" + project.getModule());
                getDependenceLoop(project.getProjectNo(), dc);
            }
        }
    }

//    public static void main(String[] args) {
//        SvnRepository d = new SvnRepository();
//        try {
//            d.test(login, "servicegatewayapi");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}