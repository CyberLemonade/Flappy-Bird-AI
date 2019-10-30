import java.util.*;

class Unit {
    boolean dead = false;
    double x, y;
    double w, h;
    
    Unit(double x,double y,double w,double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    boolean collides(Unit u) {
        return (((x + w) >= (u.x)) && ((x) <= (u.x + u.w)) && ((y + h) >= (u.y)) && ((y) <= (u.y + u.h)));
    }

    boolean contains(Unit u) {
        /*if (((x + w) >= (u.x)) && ((x) <= (u.x + u.w))) {//&& !(((y) <= (u.y)) && ((y + h) >= (u.y + u.h)))) {
            System.err.println("Gap = ("+x+","+y+","+w+","+h+") Bird = ("+u.x+","+u.y+","+u.w+","+u.h+")");
        }*/
        return (((x + w) >= (u.x)) && ((x) <= (u.x + u.w)) && !(((y) <= (u.y)) && ((y + h) >= (u.y + u.h))));
    }
}