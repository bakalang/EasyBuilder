package net.skyee.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import net.skyee.Context;
import net.skyee.ConstantMessages;
import net.skyee.bean.BuildJob;
import net.skyee.bean.DeployInfo;
import net.skyee.bean.Project;
import net.skyee.jsch.JschAction;
import net.skyee.svn.SvnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;

import static net.skyee.util.ResponseFactory.internalServerError;
import static net.skyee.util.ResponseFactory.ok;

@Path("/build")
@Produces(MediaType.APPLICATION_JSON)
public class BuildResource {

    private static final Logger log = LoggerFactory.getLogger(BuildResource.class);
    private Context context;

    public BuildResource(Context context) {
        this.context = context;
    }

    //@Timed(name = "get-requests")
    @GET
    public Response get(@QueryParam("name") Optional<String> name,
                        @QueryParam("deploy") Optional<Boolean> deploy) throws JsonProcessingException {
        log.info("build requests name: "+name.orNull()+", deploy:"+deploy.orNull());
        SvnRepository d = new SvnRepository(context);
        BuildJob bj = null;
        Calendar c = Calendar.getInstance();
        String remark = ConstantMessages.DATETIME_REMARK.format(c.getTime());


        try {
            Project p = context.getProjectDAO().getProjectByModule(name.orNull());
            //checkout compile
            bj = d.test(p, remark);
            log.info("build result: "+bj.toString());
            log.info("build remark: "+remark);

            //deploy
            if(deploy.orNull()){
                DeployInfo deployInfo = context.getDeployInfoDAO().getDeployInfoByNo(p.getProjectNo());
                JschAction ja = new JschAction(deployInfo.getUserid(), deployInfo.getPassword(), deployInfo.getHost());

                //stop tomcat
                ja.sudo("sc stop "+deployInfo.getService());
                Thread.sleep(3000);

                //copy source
                ja.sftp(getSrcFile(remark), deployInfo.getSource());

                ja.sudo(deployInfo.getRoot()+"\\deploy.bat");
                Thread.sleep(10000);

                //stop tomcat
                ja.sudo("sc start "+deployInfo.getService());
                Thread.sleep(3000);

                ja.sessionDisconnect();
            }
        }
        catch (SVNException svne){
            svne.printStackTrace();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
            return internalServerError(ConstantMessages.CHECKOUT_FAILED);
        }

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(bj));
    }

    private String getSrcFile(String remark) {
        File zipFolder = new File(context.getConfigration().getZipPath());
        for (File files : zipFolder.listFiles()){
            if(files.getAbsolutePath().indexOf(remark) > 0)
                return files.getAbsolutePath();
        }
        return null;
    }


//    @GET
//    public Response get() throws JsonProcessingException {
//        return ok("bye");
//    }
}
