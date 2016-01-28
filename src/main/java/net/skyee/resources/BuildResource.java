package net.skyee.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import net.skyee.Context;
import net.skyee.ConstantMessages;
import net.skyee.bean.BuildJob;
import net.skyee.svn.SvnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.SVNException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

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
    public Response get(@QueryParam("name") Optional<String> name) throws JsonProcessingException {
        log.info("build requests name: "+name.orNull());
        SvnRepository d = new SvnRepository(context);
        BuildJob bj = null;
        try {
            bj = d.test(name.orNull());
        }
        catch (SVNException svne){
            svne.printStackTrace();
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
        }
        catch (Exception e){
            internalServerError(ConstantMessages.CHECKOUT_FAILED);
            e.printStackTrace();
        }

        ObjectMapper mapper = new ObjectMapper();
        return ok(mapper.writeValueAsString(bj));
    }


//    @GET
//    public Response get() throws JsonProcessingException {
//        return ok("bye");
//    }
}
