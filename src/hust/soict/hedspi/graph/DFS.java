package hust.soict.hedspi.graph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class DFS extends Algorithm{
    static boolean[] visited = new boolean[200];
    private ArrayList<String> steps = new ArrayList<>();

    @Override
    public void explore(Vertex vertex) {
        visited[vertex.getId()] = true;

        String visit = "visted[" + vertex.getId() + "] = true" ;
        steps.add(visit);

        System.out.print(vertex.getId() + "->");


        Iterator<Vertex> ite = super.getData().getAdjList(vertex).iterator();

        while (ite.hasNext()){
            Vertex adj = ite.next();
            String showAdj = "Adj = ite.next =" + adj.getId();
            steps.add(showAdj);
            String visitAdj = "if(!visited[" + adj.getId() +"])";
            steps.add(visitAdj);
            if(!visited[adj.getId()]) {
                String exploreAdj = "explore(" + adj.getId() + ")";
                steps.add(exploreAdj);
                steps.add("\n");
                explore(adj);
            }
        }
    }



    public static boolean[] getVisited() {
        return visited;
    }



    public ArrayList<String> getSteps() {
        return steps;
    }


}

//    public void explore(Vertex vertex) {
//        visited[vertex.getId()] = true;
//
//        int getIdVertex = vertex.getId();
//        System.out.print(vertex.getId() + "->");
//
//
//        Iterator<Vertex> ite = super.getData().getAdjList(vertex).iterator();
//
//        while (ite.hasNext()){
//            Vertex adj = ite.next();
//
//            int getAdjId = adj.getId();
//            if(!visited[adj.getId()])
//                explore(adj);
//        }
//    }
//}

//pseudocode:
//    visited[vertex] = true;
//    System.out.print(vertex + " ");
//
//    Iterator<Integer> ite = adjLists[vertex].listIterator();
//    while (ite.hasNext()) {
//      int adj = ite.next();
//      if (!visited[adj])
//        DFS(adj);
//	}
