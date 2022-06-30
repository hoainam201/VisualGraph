package hust.soict.hedspi.graph;

public class main {
    public static void main(String args[]) {
        Graph graph = Graph.createGraph();
        DFS dfs = new DFS();
        Dijkstra dijkstra = new Dijkstra();
        Vertex temp = new Vertex();
        DP dp = new DP();

        for(int i = 0;i < 10; i++) {
            graph.addVertex(i);
        }
//Graph test 1
//        graph.addEdge(0, 1, 1);
//        graph.addEdge(0, 2, 1);
//        graph.addEdge(1, 2, 1);
//        graph.addEdge(1, 3, 1);
//        graph.addEdge(3, 4, 1);
//        graph.addEdge(3, 5, 1);
//        graph.addEdge(4, 5, 1);
//        graph.addEdge(5, 6, 1);
//        graph.addEdge(5, 8, 1);
//        graph.addEdge(4, 7, 1);
//        graph.addEdge(7, 8, 1);
//        graph.addEdge(7, 3, 1);

        //graph test 2
//        graph.addEdge(0, 1, 5);
//        graph.addEdge(0, 2, 2);
//        graph.addEdge(0, 3, 1);
//        graph.addEdge(1, 6, 8);
//        graph.addEdge(1, 2, 2);
//        graph.addEdge(2, 0, 1);
//        graph.addEdge(2, 5, 1);
//        graph.addEdge(3, 1, 3);
//        graph.addEdge(4, 2, 7);
//        graph.addEdge(5, 1, 3);
//        graph.addEdge(5, 3, 3);
//        graph.addEdge(7, 5, 3);


        //graph test 3
        graph.addEdge(0, 1, 10);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 7, 13);
        graph.addEdge(1, 6, 8);
        graph.addEdge(2, 3, 10);
        graph.addEdge(2, 7, 8);
        graph.addEdge(2, 8, 13);
        graph.addEdge(3, 4, 10);
        graph.addEdge(3, 8, 8);
        graph.addEdge(5, 6, 10);
        graph.addEdge(6, 7, 10);
        graph.addEdge(7, 8, 10);
        graph.addEdge(8, 9, 10);



//        graph.printEdge();
        temp.setId(0);
        System.out.println("DFS:");
        dfs.setData(graph);
        dfs.explore(temp);
        System.out.println(dfs.getSteps());

//        dijkstra.setData(graph);
//        dijkstra.explore(temp);

//        dp.setData(graph);
//        dp.explore(temp);

    }
}
