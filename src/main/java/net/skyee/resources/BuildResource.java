package net.skyee.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import net.skyee.Context;
import net.skyee.bean.Login;
import net.skyee.svn.SvnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
public class BuildResource {

    private static final Logger log = LoggerFactory.getLogger(BuildResource.class);
    private Context context;

    public BuildResource(Context context) {
        this.context = context;
    }

    @GET
    @Timed(name = "get-requests")
    public String sayHello(@QueryParam("name") Optional<String> name) {
        System.out.println("requests name:"+name);
        SvnRepository d = new SvnRepository(context);

        try {
            d.test(name.orNull());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "OK";
    }

}
