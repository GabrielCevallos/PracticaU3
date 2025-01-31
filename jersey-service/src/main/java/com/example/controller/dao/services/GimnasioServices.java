package com.example.controller.dao.services;

import com.example.controller.tda.list.LinkedList;
import com.example.controller.dao.GimnasioDao;
import com.example.models.Gimnasio;

public class GimnasioServices {
    private GimnasioDao obj;

    public GimnasioServices() {
        this.obj = new GimnasioDao();
    }
    
    public Gimnasio getGimnasio() {
        return this.obj.getGimnasio();
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.obj.setGimnasio(gimnasio);
    } 

    public LinkedList<Gimnasio> listAll() {
        return obj.getListAll();
    }

    public Gimnasio get(Integer id) throws Exception {
        System.out.println("GimnasioServices.get");
        return obj.get(id);
    }

    public Boolean save(String json) throws Exception {
        this.obj.gimnasioFromJson(json);
        return obj.save();
    }

    public Boolean update(String json) throws Exception {
        this.obj.gimnasioFromJson(json);
        return obj.update();
    }

    public Boolean deleteGimnasio(Integer id) throws Exception {
        return obj.deleteGimnasio(id);
    }

    public String calcularAdjs(Integer v1, Integer v2) throws Exception {
        return obj.calcularAdjs(v1, v2);
    }
}
