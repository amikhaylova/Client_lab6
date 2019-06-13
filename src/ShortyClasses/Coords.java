package ShortyClasses;

import java.io.Serializable;

public class Coords implements Serializable {

    private double x;
    private double y;

    public Coords(){
        this.x = 0;
        this.y = 0;
    }
    public Coords(double x, double y){
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            Coords other = (Coords) obj;
            return ((this.x==other.x)&&(this.y==other.y));
        }

    }
}
