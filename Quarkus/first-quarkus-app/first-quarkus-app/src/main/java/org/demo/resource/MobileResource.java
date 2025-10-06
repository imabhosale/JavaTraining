package org.demo.resource;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/mobile")
public class MobileResource {
    List<String> monileList = new ArrayList<String>();


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMobileList() {
        return Response.ok(monileList).build();
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addMobile(String monile) {
        monileList.add(monile);
        return Response.status(Response.Status.CREATED).build();
    }


    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response deleteMobile(String monile) {
        monileList.remove(monile);
        return Response.status(Response.Status.NO_CONTENT).build();
    }



    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{oldMobileName}")
    public Response updateMobile(@PathParam("oldMobileName") String oldMob,@QueryParam("newMobileName")  String newMob) {
        monileList= monileList.stream()
                .map(m -> m.equals(oldMob) ? newMob : m).collect(Collectors.toList());

        return Response.ok(monileList).build();
    }
}
