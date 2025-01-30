package com.example.controller.tda.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import com.example.controller.exception.LabelException;
import com.example.controller.tda.list.LinkedList;
import com.example.models.Gimnasio;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

@SuppressWarnings("unchecked")
public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels [];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nroVertices, Class<E> clazz) {
        super(nroVertices);
        this.clazz = clazz;
        labels = (E[])Array.newInstance(clazz, nroVertices + 1);
        dictVertices = new HashMap<>();
    }

    public Boolean isEdgeLabel(E v1, E v2) throws Exception {
        if(isLabeledGraph()) {
            return isEdge(getVerticeLabel(v1), getVerticeLabel(v2));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeLabel(E v1, E v2, Float weight) throws Exception {
        if(isLabeledGraph()) {
            addEdge(getVerticeLabel(v1), getVerticeLabel(v2), weight);
            addEdge(getVerticeLabel(v2), getVerticeLabel(v1), weight);
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void insertEdgeLabel(E v1, E v2) throws Exception {
        if(isLabeledGraph()) {
            insertEdgeLabel(v1, v2, Float.NaN);
            /* System.out.println(getVerticeLabel(v1) + "-----" + getVerticeLabel(v2));
            addEdge(getVerticeLabel(v1), getVerticeLabel(v2), Float.NaN); */
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public LinkedList<Adyacencia> adyacenciasLabel(E v) throws Exception {
        if(isLabeledGraph()) {
            return adyacencias(getVerticeLabel(v));
        } else {
            throw new LabelException("Grafo no etiquetado");
        }
    }

    public void labelsVertices(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabeledGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVerticeLabel(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nroVertices(); i++) {
                grafo += "V" + i + "[" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adyacencia> lista = adyacencias(i);
                if (!lista.isEmpty()) {
                    Adyacencia[] ady = lista.toArray();
                    for(int j = 0; j < ady.length; j++) {
                        Adyacencia a = ady[j];
                        grafo += "ady " + "V" + a.getDestination() + "weight: " + a.getWeight() + "[" + getLabelL(a.getDestination()).toString() + "]" + "\n";
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return grafo;
    }


    public String drawGraph() throws Exception {
        String grafo = "var nodes = new vis.DataSet([\n";
        try {
            for (int i = 1; i <= this.nroVertices(); i++) {
                grafo += "\t{ id: " + i + ", label: " + "\"" + getLabelL(i).toString() + "\" }";
                if (i < nroVertices()) {
                    grafo += ",\n";
                }
            }
            grafo += "\n]);\n\n";
            
            grafo += "var edges = new vis.DataSet([\n";

            LinkedList<Adyacencia>[] lista = getListAdyacencias();
            for (int i = 1; i < lista.length; i++) {
                LinkedList<Adyacencia> adjacencies = lista[i];
                    for (int j = 0; j < adjacencies.getSize(); j++) {
                    grafo += "\t{ from: " + i + ", to: " + adjacencies.get(j).getDestination() + ", label: "+ "\'" + adjacencies.get(j).getWeight() + "\'" + " }";
                    if (j < adjacencies.getSize() - 1) 
                    grafo += ",\n";
                }
                if (i < lista.length - 1 && !adjacencies.isEmpty()) {
                    grafo += ",\n";
                }
            }
            grafo += "\n]);\n\n";

            grafo += "var container = document.getElementById(\"mynetwork\");\n\n";
            grafo += "var data = {" + "\n";
            grafo += "\tnodes: nodes," + "\n";
            grafo += "\tedges: edges," + "\n";
            grafo += "}," + "\n\n";
            grafo += "var options = {};" + "\n\n";
            grafo += "var network = new vis.Network(container, data, options);" + "\n";
            
            FileWriter file = new FileWriter("resources" + File.separatorChar + "graph" + File.separatorChar + "graph.js");
            file.write(grafo);
            file.flush();
            file.close();
        } catch (Exception e) { 
            e.getMessage();
            e.printStackTrace();
        }
        return grafo;
    }

    public void edgeAllVertices() throws Exception {
        int minimo = 1;
        int maximo = nroVertices();
        for (int i = 1; i <= nroVertices(); i++) {

            double random = (Math.random() * (maximo - minimo + 1) + minimo);
            int destinoAleatorio = (int)random;
            float weight = (float)random;
            
            if (destinoAleatorio == i) { 
                destinoAleatorio = (int)(Math.random() * (maximo - minimo + 1) + minimo);
            }

            this.addEdge(i, destinoAleatorio, weight);
        }
    }

    //GUARDAR GRAFO EN JSON
    public JsonElement grafoJson() {
        return saveGraphJson(this);
    }

    public static JsonElement saveGraphJson(GraphLabelDirect<?> grafoLabel) {
        String grafo = "{";
        Gson gson = new Gson();
        grafo += "\"vertices\": {";

        for (int i = 1; i <= grafoLabel.nroVertices(); i++) {
            String labelJson = gson.toJson(grafoLabel.getLabelL(i));

            grafo += "\"v" + i + "\": " + labelJson;
            if(i < grafoLabel.nroVertices()) {
                grafo += ",";
            }
        }

        grafo += "}";
        grafo += ",\"adjacencies\": {";

        LinkedList<Adyacencia>[] adjacencies = grafoLabel.getListAdyacencias();

        for (int i = 1; i < adjacencies.length; i++) {
            grafo += "\"v" + i + "\": [";
            for (int j = 0; j < adjacencies[i].getSize(); j++) {

                try {
                    grafo += gson.toJson(adjacencies[i].get(j));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (j < adjacencies[i].getSize() - 1) {
                    grafo += ",";
                }
            }
            grafo += "]";

            if (i < adjacencies.length - 1) {
                grafo += ",";
            }
        }

        grafo += "} }";

        return gson.fromJson(grafo.toString(), JsonElement.class);
    }

    public void bellmanF(int source) {
        float[] distancia = new float[nroVertices() + 1];

        for (int i = 0; i < distancia.length; i++) distancia[i] = Float.POSITIVE_INFINITY;


        distancia[source] = 0;
        for (int i = 1; i <= nroVertices() - 1; i++) {
            for (int u = 1; u <= nroVertices(); u++) {
                for (Adyacencia adj : adyacencias(u).toArray()) {
                    int v = adj.getDestination();
                    float weight = adj.getWeight();
                    if (distancia[u] != Float.POSITIVE_INFINITY && distancia[u] + weight < distancia[v]) {
                        distancia[v] = distancia[u] + weight;
                    }
                }
            }
        }

        for (int u = 1; u <= nroVertices(); u++) {
            for (Adyacencia adj : adyacencias(u).toArray()) {
                int v = adj.getDestination();
                float weight = adj.getWeight();
                if (distancia[u] != Float.POSITIVE_INFINITY && distancia[u] + weight < distancia[v]) {
                    System.out.println("El grafo contiene un ciclo negativo");
                    return;
                }
            }
        }

        System.out.println("Distancias más cortas desde el vértice " + source + ":");
        for (int i = 1; i <= nroVertices(); i++) {
            if (distancia[i] == Float.POSITIVE_INFINITY) {
                System.out.println("V" + i + " : INFINITO");
            } else {
                System.out.println("V" + i + " : " + distancia[i]);
            }
        }
    }

    public void floydW() throws Exception {
        float[][] distance = new float[nroVertices() + 1][nroVertices() + 1];

        for (int i = 1; i <= nroVertices(); i++) {
            for (int j = 1; j <= nroVertices(); j++) {
                if (i == j) {
                    distance[i][j] = 0;
                } else {
                    distance[i][j] = Float.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 1; i <= nroVertices(); i++) {
            for (Adyacencia adj : adyacencias(i).toArray()) {
                distance[i][adj.getDestination()] = adj.getWeight();
            }
        }

        for (int k = 1; k <= nroVertices(); k++) {
            for (int i = 1; i <= nroVertices(); i++) {
                for (int j = 1; j <= nroVertices(); j++) {
                    if (distance[i][k] != Float.POSITIVE_INFINITY && distance[k][j] != Float.POSITIVE_INFINITY) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }

        for (int i = 1; i <= nroVertices(); i++) {
            for (int j = 1; j <= nroVertices(); j++) {
                if (distance[i][j] == Float.POSITIVE_INFINITY) {
                    System.out.print("INF ");
                } else {
                    System.out.print(distance[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args ) throws Exception{
        GraphLabelDirect<String> grafo = new GraphLabelDirect<>(10, String.class);
        grafo.labelsVertices(1, "Goku");
        grafo.labelsVertices(2, "Vegeta");
        grafo.labelsVertices(3, "Gohan");
        grafo.labelsVertices(4, "Piccolo");
        grafo.labelsVertices(5, "Trunks");
        grafo.labelsVertices(6, "Goten");
        grafo.labelsVertices(7, "Krillin");
        grafo.labelsVertices(8, "Yamcha");
        grafo.labelsVertices(9, "Tenshinhan");
        grafo.labelsVertices(10, "Chaoz");

        grafo.addEdge(1, 2, 4f);
        grafo.addEdge(1, 3, 5f);
        grafo.addEdge(3, 4, 3f);
        grafo.addEdge(4, 5, 2f);
        grafo.addEdge(5, 6, 1f);
        grafo.addEdge(6, 7, 3f);
        grafo.addEdge(7, 8, 2f);
        grafo.addEdge(8, 9, 1f);
        grafo.addEdge(9, 10, 4f);
        grafo.addEdge(10, 1, 5f);

        System.out.println(grafo.drawGraph());
    }

}
