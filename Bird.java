import java.util.*;

class Bird extends Unit {
    //static final double g = 40.0;
    //static final double t = 0.125;
    //static final double b = 90.0;
    
    static final double g = 2.0;
    static final double t = 0.25;
    static final double b = 19.0;
    
    double fitness = 0.0;
    NeuralNetwork brain;
    
    double v = 0.0;
    int ticks = 0;
    
    Bird(double x,double y,double w,double h) {
        super(x,y,w,h);
        //int[] layers = {3,1};
        int[] layers = {4,4,1};
        this.brain = new NeuralNetwork(layers);
    }
    
    Bird(double x,double y,double w,double h,Bird p1,boolean mutate) {
        super(x,y,w,h);
        this.brain = p1.brain.clone();
        if (mutate) this.brain.mutate();
    }
    
    Bird(double x,double y,double w,double h,Bird p1,Bird p2,boolean mutate) {
        super(x,y,w,h);
        this.brain = p1.brain.clone();
        this.brain.crossover(p2.brain);
        if (mutate) this.brain.mutate();
    }
    
    void simulate(double d,double y,double h) {
        tick();
        double inputs[] = {d-x+50, y-this.y, y+h-this.y, v};
        //double inputs[] = {y, y+h, this.y};
        double answer[] = brain.guess(inputs);
        if (answer[0] > 0.5) jump();
    }
    
    void jump() {
        v = b;
    }
    
    void tick() {
        y -= v*t;
        v -= g*t;
        if (v < -b) v = -b;
        ticks ++;
    }
    
    void die() {
        dead = true;
        fitness = ticks * ticks;
    }
}