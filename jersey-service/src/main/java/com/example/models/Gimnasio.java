package com.example.models;

public class Gimnasio {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    private Float nroEstrellas;

    //CONSTRUCTOR
    public Gimnasio() {}

    //GETTERS Y SETTERS
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLatitud() {
        return this.latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return this.longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
    
    public Float getNroEstrellas() {
        return this.nroEstrellas;
    }

    public void setNroEstrellas(Float nroEstrellas) {
        this.nroEstrellas = nroEstrellas;
    }
}
