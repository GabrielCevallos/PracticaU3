package com.example.controller.dao.implement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.example.controller.tda.graph.GraphLabelDirect;
import com.example.controller.tda.list.LinkedList;

public abstract class AdapterDao<T> implements InterfazDao<T> {
    private Class<?> clazz;
    protected Gson g;
    protected String className;
    public static String URL = "./media/";
    
    //CONSTRUCTOR VACIO
    public AdapterDao() {}

    //CONSTRUCTOR
    public AdapterDao(Class<?> clazz) {
        this.clazz = clazz;
        this.g = new Gson();
    }

    //OBTENER LA LISTA DE TODOS LOS OBJETOS
    public LinkedList<T> listAll() {
        LinkedList<T> list = new LinkedList<>();
        try {
            String data = readFile(clazz.getSimpleName());
            @SuppressWarnings("unchecked")
            T[] matrix = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            list.toList(matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //OBTENER UN OBJETO
    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        System.out.println("ID: " + id);
        System.out.println("LISTA: " + list);
        try {
            return list.busquedaBinaria("id", id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se encontro el objeto con el id: " + id);
        }
    }

    //GUARDAR UN OBJETO
    public T persist(T object) throws Exception {
        LinkedList<T> list = listAll();
        list.add(object);
        saveFile(list.toArray(), clazz.getSimpleName());
        return object;
    }

    //ACTUALIZAR UN OBJETO
    public T merge(T object, Integer id) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, list.getIndice("id", id));
        saveFile(list.toArray(), clazz.getSimpleName());
        return object;
    }
  
    //ELIMINAR UN OBJETO
    public T remove(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        Integer indice = list.getIndice("id", id);
        T object = list.get(indice);
        list.delete(indice);
        saveFile(list.toArray(), clazz.getSimpleName());
        return object;
        
    }

    //GUARDAR EN EL GRAFO JSON
    public static JsonElement graphToJson(GraphLabelDirect<?> labeledGraph) {
        return labeledGraph.grafoJson();
    }

    public static GraphLabelDirect<Object> graphFromJson(Class<?> classs, Boolean check) throws Exception {
        String grafog = classs.getSimpleName();
        JsonElement element = null;
        try {
            element = new Gson().fromJson(readFile("Graph" + grafog), JsonElement.class);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        boolean nullElement = element == null;

        if (nullElement) {
            return new GraphLabelDirect<>(1, Object.class);
        }

        
        JsonObject vertices = element.getAsJsonObject().get("vertices").getAsJsonObject();

        Integer nroVertices = (check) ? vertices.size() : vertices.size() + 1;
        
        GraphLabelDirect<Object> graph = new GraphLabelDirect<>(nroVertices, Object.class);

        Gson gson = new Gson();
        for (int j = 0; j < vertices.size(); j++) {
            JsonObject obj = vertices.get("v" + Integer.toString(j + 1)).getAsJsonObject();
            graph.labelsVertices(j + 1, gson.fromJson(obj.toString(), classs));
        }

        JsonObject obj = element.getAsJsonObject().get("adjacencies").getAsJsonObject();
        for (int j = 1; j <= obj.size(); j++) {
            String key = "v" + Integer.toString(j);
            JsonArray adjs = obj.get(key).getAsJsonArray();
            
            for (int i = 0; i < adjs.size(); i++) {
                JsonObject adjacency = adjs.get(i).getAsJsonObject();
                graph.addEdge(j, adjacency.get("destination").getAsInt(), adjacency.get("weight").getAsFloat());
            }
        }
        
        return graph;
    }

    public static GraphLabelDirect<Object> graphFromJson(Class<?> class1) throws Exception {
        return graphFromJson(class1,false);
    } 
    
    public void saveGrafoJson(T obj, Class<?> class1, Integer destination, Float weight) throws Exception {
        GraphLabelDirect<Object> graph = graphFromJson(class1);
        graph.labelsVertices(graph.nroVertices(), obj);
        graph.addEdge(graph.nroVertices(), destination, weight);
        saveFile(graph.grafoJson(), "Graph" + class1.getSimpleName());
    }

    public void saveGrafoJson(T obj, Class<?> class1) throws Exception {
        GraphLabelDirect<Object> graph = graphFromJson(class1);
        graph.labelsVertices(graph.nroVertices(), obj);
        saveFile(graph.grafoJson(), "Graph" + class1.getSimpleName());
    }

    //GUARDAR EL ARCHIVO JSON
    protected void saveFile(Object data, String className) throws Exception {
        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(data);

        final String fileName = URL + className + ".json";
        try (FileWriter f = new FileWriter(fileName)) {
            f.write(json);
            f.flush();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }

    //LEER EL ARCHIVO JSON
    public static String readFile(String className) {
        final String fileName = URL + className + ".json";
        try (BufferedReader bfr = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = bfr.readLine()) != null) {
                sb.append(line).append('\n');
            }
            return sb.toString();
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            return "";
        }
    }
}