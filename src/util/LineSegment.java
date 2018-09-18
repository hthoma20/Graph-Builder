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
        Orientation o1= orientation(p1,p2,segment.p1);
        Orientation o2= orientation(p1,p2,segment.p2);

        if(o1 == o2){
            return false;
        }

        o1= orientation(segment.p1,segment.p2,p1);
        o2= orientation(segment.p1,segment.p2,p2);

        return o1 != o2;
    }

    public boolean intersects(Point p1, Point p2){
        return intersects(new LineSegment(p1,p2));
    }

    public static Orientation orientation(Point p1, Point p2, Point p3){
        int centerX= (p1.x+p2.x+p3.x)/3;
        int centerY= (p1.y+p2.y+p3.y)/3;

        //find angles from center point to each point
        double theta1= Math.atan2(centerY - p1.y, centerX - p1.x);
        double theta2= Math.atan2(centerY - p2.y, centerX - p2.x);
        double theta3= Math.atan2(centerY - p3.y, centerX - p3.x);

        //rotate points so p1 is at angle 0
        theta2-= theta1;
        theta2%= Math.PI*2;
        theta3-= theta1;
        theta3%= Math.PI*2;

        double diff= theta3-theta2;

        //if the diff is 0, theyre colinear
        if(almostEqual(diff,0)){
            return Orientation.COLINEAR;
        }
        if(diff > 0){
            return Orientation.COUTERCLOCKWISE;
        }
        return Orientation.CLOCKWISE;
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

    public enum Orientation{
        CLOCKWISE,
        COUTERCLOCKWISE,
        COLINEAR;
    }
}
