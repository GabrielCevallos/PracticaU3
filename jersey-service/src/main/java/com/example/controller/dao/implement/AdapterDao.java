package com.example.controller.dao.implement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.controller.tda.list.LinkedList;

public abstract class AdapterDao<T> implements InterfazDao<T> {
    private Class<?> clazz;
    private Gson g;
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

    //GUARDAR EL ARCHIVO JSON
    private void saveFile(Object data, String className) throws Exception {
        final String json = new GsonBuilder().setPrettyPrinting().create().toJson(data);

        final String fileName = URL + className + ".json";
        try (FileWriter f = new FileWriter(fileName)) {
            f.write(json);
            f.flush();
        } catch (Exception e) {
            System.out.println("JsonFileManager.saveFile() dice: " + e.getMessage());
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
            System.out.println("JsonFileManager.readFile(str) dice: " + e.getMessage());
            return "";
        }
    }
}