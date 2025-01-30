package com.example.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.google.gson.Gson;

import com.example.controller.dao.services.GimnasioServices;


@Path("gym")
public class GimnasioApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap<String, Object> map = new HashMap<>();
        GimnasioServices gym = new GimnasioServices();
        try {
            map.put("status", "OK");
            map.put("data", gym.listAll().toArray());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "ERROR");
            map.put("data", e.getMessage());
        }
        if (gym.listAll().isEmpty()) {
            map.put("status", "OK");
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }


    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GimnasioServices gym = new GimnasioServices();
        try {
            gym.setGimnasio(gym.get(id));
            map.put("status", "OK");
            map.put("data", gym.getGimnasio());
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "ERROR");
            map.put("data", e.getMessage()); 
        }
        if (gym.getGimnasio().getId() == null) {
            map.put("data", "No existe Gimnasio con ese identificador");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }


    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        System.out.println("*********************\n" + a);
        try {
            GimnasioServices gym = new GimnasioServices();
            gym.getGimnasio().setNombre(map.get(("nombre")).toString());
            gym.getGimnasio().setDescripcion(map.get(("descripcion")).toString());
            gym.getGimnasio().setLatitud(Double.parseDouble(map.get("latitud").toString()));
            gym.getGimnasio().setLongitud(Double.parseDouble(map.get("longitud").toString()));
            gym.getGimnasio().setNroEstrellas(Float.parseFloat(map.get("nroEstrellas").toString()));
            gym.save();
            res.put("status", "OK");
            res.put("data", "Gimnasio registrado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", "ERROR");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }

    }


    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            GimnasioServices gym = new GimnasioServices();
            gym.setGimnasio(gym.get(Integer.parseInt(map.get("id").toString())));
            gym.getGimnasio().setNombre(map.get(("nombre")).toString());
            gym.getGimnasio().setDescripcion(map.get(("descripcion")).toString());
            gym.getGimnasio().setLatitud(Double.parseDouble(map.get("latitud").toString()));
            gym.getGimnasio().setLongitud(Double.parseDouble(map.get("longitud").toString()));
            gym.getGimnasio().setNroEstrellas(Float.parseFloat(map.get("nroEstrellas").toString()));
            gym.update();
            res.put("status", "OK");
            res.put("data", "Gimnasio actualizado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace();
            res.put("status", "ERROR");
            res.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @Path("/delete/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        GimnasioServices gym = new GimnasioServices();
        try {
            gym.deleteGimnasio(id);
            map.put("status", "OK");
            map.put("data", "Gimnasio eliminado correctamente");
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "ERROR");
            map.put("data", e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }
}
