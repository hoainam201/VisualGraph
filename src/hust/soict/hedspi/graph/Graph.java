package hust.soict.hedspi.graph;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
    private ArrayList<Edge> edges = new ArrayList<>();

    public static Graph createGraph(){
        Graph g = new Graph();
        return g;
    }

    public void addVertex(int v) {
        Vertex vertex = new Vertex();
        vertex.setId(v);
        vertices.add(vertex);
    }

    public void addEdge(int u,int v,int label) {
            Edge tmp = new Edge();
            Vertex v1 = new Vertex();
            Vertex v2 = new Vertex();
            v1.setId(u);
            v2.setId(v);
            for (Edge i : edges)
                if(i.getFrom().getId() == v1.getId() && i.getTo().getId() == v2.getId())
                    return;
            tmp.setFrom(v1);
            tmp.setTo(v2);
            tmp.setLabel(label);
            edges.add(tmp);
    }

    public ArrayList<Vertex> getAdjList(Vertex v){
        ArrayList<Vertex> adjList = new ArrayList<>();
        for (Edge i : edges)
            if (i.getFrom().getId() == v.getId())
                adjList.add(i.getTo());
//        for (Vertex j : adjList)
//            System.out.print(j.getId() + "+");
//        System.out.println(">");
        return adjList;
    }

    public int getLabelEdge(Vertex f, Vertex t){
        Edge tmp = new Edge();
        for (Edge i : edges) {
            if (i.getFrom().getId() == f.getId() && i.getTo().getId() == t.getId())
                tmp = i;
        }
        return tmp.getLabel();
    }

    public void printEdge(){
        for (Edge i : edges)
            System.out.println(i.getFrom().getId() + "->" + i.getTo().getId());
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}
