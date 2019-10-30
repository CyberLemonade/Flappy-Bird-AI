import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

class Frame extends JFrame {
    final double mult = 1;
    Simulator g;

    Frame() {
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setSize((int)(550*mult),(int)(750*mult));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Flappy Bird");
        setResizable(false);
        g = new Simulator(getWidth(),getHeight());
        add(g);
        setIconImage(new ImageIcon("Assets/bird.png").getImage());
        g.init();
    }

    public static void main(String[] args) {
        try {
            GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Kenney Blocks.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println("Could not create Font");
        }
        new Frame();
    }
}
