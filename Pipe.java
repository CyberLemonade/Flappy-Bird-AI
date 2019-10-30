import java.util.*;

class Pipe extends Unit {
    static final int speed = 2;
    
    Pipe(double x,double y,double w,double h) {
        super(x,y,w,h);
    }
    
    void simulate() {
        x -= speed;
        if (x < -w) dead = true;
    }
}