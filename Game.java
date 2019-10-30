import java.util.*;

class Game {
    final int width, height;
    final double w, h;
    
    ArrayList<Pipe> pipes;
    
    int score;
    
    Game(int width,int height) {
        this.width = width;
        this.height = height;
        //this.w = width * 1.14/5.57;
        this.w = width * 1.05/5.57;
        //this.h = height * 1.96/7.5;
        this.h = height * 2.06/7.5;
        this.score = 0;
        this.pipes = new ArrayList<Pipe>();
        init();
    }
    
    void init() {
        double x = 2*width;
        double y = height/2 - h/2;
        pipes.add(new Pipe(x,y,w,h));
    }
    
    void simulate() {
        for (int i = 0; i < pipes.size(); i++) {
            pipes.get(i).simulate();
            if (pipes.get(i).dead) {
                pipes.remove(i);
                i--;
            }
        }
        if (pipes.get(0).x < width/2 && pipes.get(0).x + Pipe.speed > width/2) score++;
        if (pipes.get(pipes.size()-1).x < width*1/2) add();
    }
    
    void add() {
        double x = width * 1.2;
        double y = h + Math.random() * (height - h * 3.0);
        pipes.add(new Pipe(x,y,w,h));
    }
    
    void simulate(Bird[] birds,boolean playable) {
        Pipe c = (birds[0].x > pipes.get(0).x + pipes.get(0).w) ? pipes.get(1) : pipes.get(0);
        for (int i = 0; i < birds.length; i++) {
            Bird cnt = birds[i];
            if (cnt.y + cnt.h > height) cnt.die();
            else if (c.contains(cnt)) cnt.die();
            if (cnt.dead) continue;
            if (!playable) cnt.simulate(c.x, c.y, c.h);
        }
    }
}