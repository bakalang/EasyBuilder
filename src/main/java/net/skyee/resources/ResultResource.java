package net.skyee.resources;

import net.skyee.Context;
import net.skyee.ConstantMessages;
import net.skyee.util.ResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/zip/{" + ConstantMessages.TOKEN_PATH_PARAM + "}")
public class ResultResource {
    private static final Logger log = LoggerFactory.getLogger(ResultResource.class);
    private Context context;
    public ResultResource(Context context) {
        this.context = context;
    }

    @GET
    public Response get(@PathParam(ConstantMessages.TOKEN_PATH_PARAM) String token) {
        return ResponseFactory.ok(token);
    }
}
