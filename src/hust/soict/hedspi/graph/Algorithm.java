package hust.soict.hedspi.graph;

public abstract class Algorithm {
    private Graph data = Graph.createGraph();

    public abstract void explore(Vertex vertex);

    public Graph getData() {
        return data;
    }

    public void setData(Graph data) {
        this.data = data;
    }
}
