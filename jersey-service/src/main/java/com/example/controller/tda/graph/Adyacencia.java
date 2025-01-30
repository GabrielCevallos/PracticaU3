package com.example.controller.tda.graph;

public class Adyacencia {
    private Integer destination;
    private Float weight;

    public Adyacencia() {}

    public Adyacencia(Integer destination, Float weight) {
        this.destination = destination;
        this.weight = weight;
    }

    public Integer getDestination() {
        return this.destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Float getWeight() {
        return this.weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Adyacencia {" + "destino = " + destination + ", peso = " + weight + "}";
    }

}
