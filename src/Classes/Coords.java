package Classes;

import java.io.Serializable;

public class Coords implements Serializable {

    private double x;
    private double y;

    public Coords(){
        this.x = 0;
        this.y = 0;
    }
    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public String toString() {
        return ("x: " + this.x + ". y: " + this.y + ".");
    }
}
