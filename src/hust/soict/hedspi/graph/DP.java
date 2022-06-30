package hust.soict.hedspi.graph;

import java.util.ArrayList;

import java.util.Stack;

public class DP extends Algorithm{
    private ArrayList<Edge> edges = new ArrayList<>();
    static int[] color = new int[0];
    static boolean[] visited = new boolean[0];
    static Stack<Vertex> topoStack = new Stack<>();
    static ArrayList<Vertex> adjList= new ArrayList<>();
    static Vertex tmp = new Vertex();


    @Override
    public void explore(Vertex vertex) {
        int verticesSize = super.getData().getVertices().size();
        long[] dist = new long[verticesSize];
        int[] pre = new int[verticesSize];
        int r;

        for (int i = 0; i < verticesSize; i++) {
            dist[i] = Integer.MAX_VALUE ;
            pre[i] = -1;
        }

        topoSort();
        getTopoStack();

        dist[vertex.getId()] = 0;

        while (!topoStack.isEmpty()){
            tmp = topoStack.lastElement();
            adjList = super.getData().getAdjList(tmp);

            for (Vertex vertex1 : adjList) {
                r = super.getData().getLabelEdge(tmp, vertex1);

                if (dist[vertex1.getId()] > dist[tmp.getId()] + r){
                    dist[vertex1.getId()] = dist[tmp.getId()] + r;
                    pre[vertex1.getId()] = tmp.getId();
                }
            }

            topoStack.remove(tmp);
        }
        for (int i = 0; i < verticesSize; i++) {
            System.out.print(i + " ");
            System.out.println(dist[i] == Integer.MAX_VALUE ? "Inf" : String.valueOf(dist[i]));
        }

    }

    public boolean checkCyclic(Vertex u) {

        color[u.getId()] = 1;
        for(Vertex v : super.getData().getAdjList(u)) {
            if(color[v.getId()] == 0) {
                if(checkCyclic(v))
                    return true;
            }
            else if(color[v.getId()] == 1) return true;//exist (v,u)
        }

        color[u.getId()] = 2;
        return false;

    }

    public boolean isDAG() {
        int colorSize = super.getData().getVertices().size();
        color = new int[colorSize];

        for(int i = 0;i < colorSize; i++) {
            color[i] = 0;
        }

        for(Vertex v : super.getData().getVertices()) {
            if(checkCyclic(v))
                return false;

        }
        return true;
    }

    public void topoTraverser(Vertex v){

        visited[v.getId()] = true;
        for(Vertex u : super.getData().getAdjList(v)) {
            if(!visited[u.getId()])
                topoTraverser(u);
        }

        topoStack.push(v);
    }

    public void topoSort() {

        int visitSize = super.getData().getVertices().size();
        visited = new boolean[visitSize];

        for(int i = 0;i < visitSize; i++) {
            visited[i] = false;
        }

        for(Vertex v : super.getData().getVertices()) {
            if(!visited[v.getId()])
                topoTraverser(v);
        }
    }

    public Stack<Vertex> getTopoStack() {
        return topoStack;
    }






}
