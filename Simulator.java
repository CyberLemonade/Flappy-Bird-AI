import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.*;

class Simulator extends JPanel implements KeyListener {
    Game game;
    final boolean playable = true;

    Bird[] players;
    Bird best = null;
    int generation = 1;
    int gX;

    final int width, height;
    final double bX, bY;
    final double bW, bH;
    final double gH;

    Image back;
    Image birdUp, birdDown;
    Image pipeUp, pipeDown;
    Image ground;

    Simulator(int width,int height) {
        this.width = width;
        this.height = height;
        this.bW = (double)width * 0.66/5.5;
        this.bH = (double)height * 0.57/7.5;
        this.bX = width/2 - bW/2;
        this.bY = height/2 - bH/2;
        this.gX = 0;
        this.gH = height*0.89/7.5;

        setSize(width,height);
        setVisible(true);
        setFont(new Font("Kenney Blocks",Font.PLAIN,80));
        if (playable) { 
            addKeyListener(this);
            setFocusable(true);
        }

        game = new Game(width,(int)(height-gH));
        players = !playable ? new Bird[100] : new Bird[1];

        loadImages();
        init();
    }

    void loadImages() {
        back = load(width,height,"Assets/back.png");
        birdUp = load(bW,bH,"Assets/bird up.png");
        birdDown = load(bW,bH,"Assets/bird down.png");
        pipeUp = load(game.w,height,"Assets/pipe up.png");
        pipeDown = load(game.w,height,"Assets/pipe down.png");
        ground = load(width,gH,"Assets/ground.png");
    }

    Image load(double w,double h,String dir) {
        try{
            BufferedImage FIRST = ImageIO.read(new File(dir));
            BufferedImage SECOND = new BufferedImage((int)(w),(int)(h),FIRST.getType());
            Graphics2D g2d = SECOND.createGraphics();
            g2d.drawImage(FIRST,0,0,(int)(w),(int)(h),null);
            g2d.dispose();
            return SECOND;
        } catch (Exception e) {System.err.println("Could not load: "+dir); return null;}
    }

    void init() {
        if (playable) requestFocus();
        for (int i = 0; i < players.length; i++) {
            players[i] = new Bird(bX,bY,bW,bH);
        }
        simulate();
    }

    public void keyTyped(KeyEvent e) {
        players[0].jump();
        simulate();
    }

    public void keyPressed(KeyEvent e) {
        players[0].jump();
        simulate();
    }

    public void keyReleased(KeyEvent e) {}

    void simulate() {
        moveGround();
        movePipes();
        moveBirds();
        getDelay(6);
        repaint();
    }

    void moveGround() {
        gX -= Pipe.speed;
        if (gX < - width) gX = 0;
    }

    void movePipes() {
        game.simulate();
        game.simulate(players,playable);
    }

    void moveBirds() {
        if (playable) {players[0].tick();}
        applyBrain();
    }

    void getDelay(int time) {
        try {Thread.sleep(time);}
        catch (Exception e) {System.err.println("Delay error");}
    }

    void applyBrain() {
        boolean nextGen = true;
        for (int i = 0; i < players.length; i++) {
            if (players[i].dead) continue;
            nextGen = false;
            break;
        }
        if (nextGen) createNewGen();
    }

    void createNewGen() {
        getDelay(100);
        game = new Game(width,(int)(height-gH));
        generation++;
        tweakFitness();
        players = getOffsprings();
    }

    void tweakFitness() {
        best = players[0];
        double total_fitness = 0.0;
        for (int i = 0; i < players.length; i++) {
            total_fitness += players[i].fitness;
            if (players[i].fitness > best.fitness) best = players[i];
        }
        for (int i = 0; i < players.length; i++) {
            players[i].fitness /= total_fitness;
        }
    }

    int naturalSelection() {
        double num = Math.random();
        for (int i = 0; i < players.length; i++) {
            num -= players[i].fitness;
            if (num < 0) return i;
        }
        return 0;
    }

    Bird[] getOffsprings() {
        Bird[] nextGen = new Bird[players.length];
        nextGen[0] = new Bird(bX,bY,bW,bH,best,false);
        for (int i = 0; i < nextGen.length; i++) {
            int p1 = naturalSelection();
            //int p2 = naturalSelection(); // comment to disable cross-over
            nextGen[i] = getNewBird(p1); // uncomment to disable cross-over
            //nextGen[i] = getNewBird(p1,p2); // comment to disable cross-over
        }
        return nextGen;
    }

    Bird getNewBird(int p1,int p2) {return new Bird(bX,bY,bW,bH,players[p1],players[p2],true);}

    Bird getNewBird(int p1) {return new Bird(bX,bY,bW,bH,players[p1],true);}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintBackground(g);
        paintWalls(g);
        paintGround(g);
        paintBirds(g);
        paintScore(g);
        simulate();
    }

    void paintBackground(Graphics g) {
        g.drawImage(back,0,0,null);
    }

    void paintBirds(Graphics g) {
        for (int i = 0; i < players.length; i++) {
            Bird cnt = players[i];
            if (cnt.dead) continue;
            if (cnt.v > -Bird.b) g.drawImage(birdUp, (int)cnt.x, (int)cnt.y, null);
            else g.drawImage(birdDown, (int)cnt.x, (int)cnt.y, null);
            break;
        }
    }

    void paintWalls(Graphics g) {
        for (int i = 0; i < game.pipes.size(); i++) {
            Pipe cnt = game.pipes.get(i);
            g.drawImage(pipeUp, (int)cnt.x, (int)(cnt.y - height), null);
            g.drawImage(pipeDown, (int)cnt.x, (int)(cnt.y+cnt.h), null);
        }
    }

    void paintGround(Graphics g) {
        g.drawImage(ground,gX,(int)(height-gH),null);
        g.drawImage(ground,gX+width,(int)(height-gH),null);
    }

    void paintScore(Graphics g) {
        String score = String.valueOf(game.score);
        g.drawString(score, getWidth()/2 - g.getFontMetrics().stringWidth(score)/2, 95);
    }
}