package net.skyee.svn;

import net.skyee.Context;
import net.skyee.bean.Login;
import net.skyee.bean.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SvnRepository {
    Logger log = LoggerFactory.getLogger(SvnRepository.class);

    private Context context;

    public SvnRepository(Context context) {
        this.context= context;
    }


    public void test(String moduleName) throws Exception {

        // declare
        Login login = new Login(context.getConfigration().getSvnUser(), context.getConfigration().getSvnPwd());
        Map<Integer, String> dc = new LinkedHashMap<Integer, String>();
        Project p = context.getProjectDAO().getProjectByModule(moduleName);
        getDependenceLoop(p.getProjectNo(), dc);
        dc.put(p.getProjectNo(), p.getRepository() + "/" + p.getModule());

        SVNAction svnaction = new SVNAction();
        for ( int projectNo : dc.keySet() )
        {
            String path = dc.get(projectNo);
            svnaction.setup(context.getConfigration().getSvnURL()+ "/" +path, login);

            // chechout
            log.info("start  " + path + " checkout!");
            svnaction.checkout(projectNo, path, projectNo==p.getProjectNo() );
            log.info("----------------------------------------------");
        }
        System.out.println(p);
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