package com.example.controller.dao;
import com.example.controller.tda.graph.Adyacencia;
import com.example.controller.tda.graph.GraphLabelDirect;
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
        saveGrafoJson(this.gimnasio, gimnasio.getClass());
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

    public void gimnasioFromJson(String json) {
        this.gimnasio = this.g.fromJson(json, Gimnasio.class);
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

    //CALCULAR DISTANCIA METODO HAVERSINE
    public static Double distanciaHaversine(Gimnasio gym1, Gimnasio gym2) {
        if(gym1.getLatitud() == null || gym1.getLongitud() == null || gym2.getLatitud() == null || gym2.getLongitud() == null) {
            return Double.NaN;
        }

        Double latitud1 = Math.toRadians(gym1.getLatitud().doubleValue());
        Double longitud1 = Math.toRadians(gym1.getLongitud().doubleValue());
        Double latitud2 = Math.toRadians(gym2.getLatitud().doubleValue());
        Double longitud2 = Math.toRadians(gym2.getLongitud().doubleValue());
        final Double earthRadius = 6378.10;
        Double latitudF = latitud2 - latitud1;
        Double longitudF = longitud2 - longitud1;

        Double distancia = 2 * earthRadius * Math.asin(Math.sqrt(Math.pow(Math.sin(latitudF / 2), 2) + Math.cos(latitud1) * Math.cos(latitud2) * Math.pow(Math.sin(longitudF / 2), 2)));
        
        return distancia;
    }

    //CALCULAR ADYACENCIAS
    public String calcularAdjs(Integer v1, Integer v2) throws Exception {
        GraphLabelDirect<Object> graph = graphFromJson(Gimnasio.class, true);
        Gimnasio gym1 = (Gimnasio)graph.getLabelL(v1);
        Gimnasio gym2 = (Gimnasio)graph.getLabelL(v2);
        Float distancia = distanciaHaversine(gym1, gym2).floatValue();
        graph.addEdge(v1, v2, distancia);
        saveFile(graph.grafoJson(), "GraphGimnasio");
        if(distancia.isNaN()) {
            return "No se puede calcular la distancia entre los gimnasios";
        }
        return "La distancia entre los gimnasios es de: " + distancia + " km";
    }

    //IMPLEMENTACION DE FLOYD 
    public void floydW() throws Exception {
        GraphLabelDirect<Object> graph = graphFromJson(Gimnasio.class, true);
        graph.floydW();
        saveFile(graph.grafoJson(), "GraphGimnasio");
    }

    //IMPLEMENTACION DE BELLMAN FORD
    public void bellmanF(Integer v) throws Exception {
        GraphLabelDirect<Object> graph = graphFromJson(Gimnasio.class, true);
        graph.bellmanF(v);
        saveFile(graph.grafoJson(), "GraphGimnasio");
    }
}
