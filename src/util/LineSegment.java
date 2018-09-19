package util;

import java.awt.*;

public class LineSegment {
    private Point p1;
    private Point p2;

    public LineSegment(Point p1, Point p2){
        this.p1= p1;
        this.p2 = p2;
    }

    public LineSegment(int x1, int y1, int x2, int y2) {
        this(new Point(x1,y1), new Point(x2,y2));
    }

    public boolean intersects(LineSegment segment){
        Line thisLine= new Line(this);
        Line otherLine= new Line(segment);

        //the other points must lie on opposite ends of thisLine
        //and this' points must lie on opposite ends of otherLine

        return Math.signum(thisLine.compare(segment.p1)) == -Math.signum(thisLine.compare(segment.p2))
                && Math.signum(otherLine.compare(p1)) == -Math.signum(otherLine.compare(p2));
    }

    public boolean intersects(Point p1, Point p2){
        return intersects(new LineSegment(p1,p2));
    }


    public static boolean almostEqual(double d1, double d2){
        return Math.abs(d1-d2) < 4*Math.ulp(d1);
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getP1(){
        return p1;
    }

    public Point getP2(){
        return p2;
    }

    public int getX1(){
        return p1.x;
    }

    public int getX2(){
        return p2.x;
    }

    public int getY1(){
        return p1.y;
    }

    public int getY2(){
        return p2.y;
    }

    public class Line{
        double m; //slope of the line
        double b; //y-intercept of the line

        public Line(Point p1, Point p2){
            double deltaY= p1.y-p2.y;
            double deltaX= p1.x-p2.x;

            m= deltaY/deltaX;
            b= p1.y-m*p1.x;
        }

        public Line(LineSegment segment){
            this(segment.p1,segment.p2);
        }

        public Line(double m, double b) {
            this.m = m;
            this.b = b;
        }

        /**
         *
         * @param p point to test
         * @return  a negative value if the point is less than the line
         *          a positive value if the point is greater than the line
         *          0 if the point is on the line
         */
        public double compare(Point p){
            return p.y - (m*p.x + b);
        }
    }
}
