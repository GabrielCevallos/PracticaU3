package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.example.controller.dao.services.GimnasioServices;
import com.example.controller.tda.graph.GraphLabelNoDirect;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Gimnasio;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() {
        HashMap mapa = new HashMap<>();
        GraphLabelNoDirect graph = null;
        try {
            GimnasioServices gym = new GimnasioServices();
            LinkedList<Gimnasio> gimnasios = gym.listAll();
            if(!gimnasios.isEmpty()) {
                graph = new GraphLabelNoDirect<>(gimnasios.getSize(), Gimnasio.class);
                Gimnasio[] g = gimnasios.toArray();
                for(int i = 0; i < gimnasios.getSize(); i++) {
                    graph.labelsVertices((i + 1), g[i]);
                }
            }
            graph.drawGraph();
        } catch (Exception e) {
            mapa.put("status", "ERROR");
            mapa.put("data", e.toString());
            return Response.status(Status.BAD_REQUEST).entity(mapa).build();
        }
        System.out.println(graph.toString());
        return Response.ok(mapa).build();
    }
}
