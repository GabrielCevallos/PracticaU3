package com.example.controller.dao;
import com.example.controller.tda.list.LinkedList;
import com.example.controller.dao.implement.AdapterDao;
import com.example.models.Gimnasio;

public class GimnasioDao extends AdapterDao<Gimnasio> {
    private Gimnasio gimnasio;
    private LinkedList<Gimnasio> listAll;

    //CONSTRUCTOR
    public GimnasioDao() {
        super(Gimnasio.class);
    }

    //GETTERS Y SETTERS
    public Gimnasio getGimnasio() {
        if (gimnasio == null) {
            gimnasio = new Gimnasio();
        }
        return this.gimnasio;
    }

    public void setGimnasio(Gimnasio gimnasio) {
        this.gimnasio = gimnasio;
    }

    //CRUD
    public LinkedList<Gimnasio> getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        gimnasio.setId(id);
        if(!camposLLenos()) {
            throw new Exception("Los campos estan vacios, por favor completarlos");
        }
        if(!checkNroStars()) {
            throw new Exception("El número de estrellas debe ser entre 0 y 5");
        }
        this.persist(this.gimnasio);
        this.listAll = listAll();
        return true;
    }

    public Boolean update() throws Exception {
        final Integer id = this.getGimnasio().getId();
        if(!camposLLenos()) {
            throw new Exception("Los campos estan vacios, por favor completarlos");
        }
        if(!checkNroStars()) {
            throw new Exception("El número de estrellas debe ser entre 0 y 5");
        }
        this.merge(getGimnasio(), id);
        this.listAll = listAll();        
        return true;
    }

    public Boolean deleteGimnasio(Integer id) throws Exception {
        this.remove(id);  
        this.listAll = listAll();        
        return true;
    }
    
    //VALIDADORES
    public Boolean camposLLenos() {
        if(this.getGimnasio().getNombre() == null) return false;
        if(this.getGimnasio().getDescripcion() == null) return false;
        if(this.getGimnasio().getLatitud() == null) return false;
        if(this.getGimnasio().getLongitud() == null) return false;
        if(this.getGimnasio().getNroEstrellas() == null) return false;
        return true;
    }

    public Boolean checkNroStars() throws Exception {
        if (this.getGimnasio().getNroEstrellas() < 0 || this.getGimnasio().getNroEstrellas() > 5) {
            throw new Exception("El número de estrellas debe ser entre 0 y 5");
        }
        return true;
    }

}
