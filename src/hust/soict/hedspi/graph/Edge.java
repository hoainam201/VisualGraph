package hust.soict.hedspi.graph;

public class Edge {
    private Vertex from;
    private Vertex to;
    private int label;

    public Vertex getFrom() {
        return from;
    }

    public void setFrom(Vertex from) {
        this.from = from;
    }

    public Vertex getTo() {
        return to;
    }

    public void setTo(Vertex to) {
        this.to = to;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public boolean isWeighted(){
        if (getLabel() != 0)
            return true;
        return false;
    }

    public boolean sDireteid(){
        if(getFrom() != null && getTo() != null)
            return true;
        return false;
    }
}
