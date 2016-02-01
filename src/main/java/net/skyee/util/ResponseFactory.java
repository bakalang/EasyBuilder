package net.skyee.util;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import static javax.ws.rs.core.Response.Status.*;

public class ResponseFactory {
    public static Response ok(String entity) {
        return Response
                .ok()
                .entity(entity)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    public static Response ok(StreamingOutput sout) {
        return Response
                .ok()
                .entity(sout)
                .type(MediaType.TEXT_PLAIN)
                .build();
    }

    public static Response internalServerError(String entity) {
        return Response
                .status(INTERNAL_SERVER_ERROR)
                .entity(entity)
                .build();
    }
}
