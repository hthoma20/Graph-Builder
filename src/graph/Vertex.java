package graph;

public class Vertex {
    private int x;
    private int y;
    private String label;

    public Vertex(int x, int y, String label){
        this.x= x;
        this.y= y;
        this.label= label;
    }

    public Vertex(int x, int y){
        this(x,y,"");
    }

    /**
     * @return a deep copy of this vertex
     */
    public Vertex copy(){
        return new Vertex(this.x, this.y, new String(this.label));
    }

    public void moveTo(int x, int y){
        this.x= x;
        this.y= y;
    }

    /**
     * tells whether the specified point is within the specifided radius
     * of this vertex
     *
     * @param x the x-coord of the point to test
     * @param y the y-coord of the point to test
     * @param rad the radius to test if (x,y) is within
     * @return whether the point (x,y) is within rad of this vertex
     */
    public boolean within(int x, int y, int rad){
        double dist= Math.sqrt(pow(this.x-x)+pow(this.y-y));

        return dist <= rad;
    }

    private double pow(double x){
        return x*x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }
}
