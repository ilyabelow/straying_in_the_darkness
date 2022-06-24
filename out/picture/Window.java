package out.picture;

import game.play.Bubble;
import game.play.Darkness;
import game.Game;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static game.Game.m;
import static game.Game.stats;
import game.core.Message;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import static out.picture.Textures.logo;
//3QRH-62TQ5K-EBPCTA-4CN2W5-VG95NH

public class Window extends JFrame {

    public static final int speedCoefficient = 20;
    private static int CpS = 100;
    private static int clicks = 0;
    public static final boolean[][] wasRedrew = new boolean[10][10];
    private static final Out o = new Out();
    public static int VO = 55, HO = 25;
    private static int direction = 0, shakes = 0;
    private static Timer BOOM = new Timer(20, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (shakes != 0) {
                shakes--;
                direction = new Random().nextInt(5);
            } else {
                direction = 0;
                BOOM.stop();
            }
            if (direction == 0) {
                VO = 55;
                HO = 25;

            }
            if (direction == 1) {
                VO = 50;
                HO = 25;
            }
            if (direction == 2) {
                VO = 60;
                HO = 25;
            }
            if (direction == 3) {
                VO = 55;
                HO = 20;
            }
            if (direction == 4) {
                VO = 55;
                HO = 30;
            }
        }
    });

    private static Timer gameSpeed = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            CpS = clicks;
            System.out.println("CpS: "+CpS);
            clicks = 0;
        }
    });

    public Window() {
        super("Блуждающий во Тьме up.2.0 «Эй, кто выключил свет?!»");
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setSize(830, 710);
        setIconImage(null);
        setIconImage(logo);
        setContentPane(o);
        //setResizable(false);
        gameSpeed.start();
    }

    public static void boom() {
        shakes = 5;
        BOOM.start();
    }

    public static int getCpS() {
        return CpS;
    }

    public static void clickCell(int A, int B, Graphics g) {
        boolean redrew = wasRedrew[A][B];
        wasRedrew[A][B] = true;
        for (int layer = 0; layer < m.height; layer++) {
            if (m.getCell(A, B, layer).hasAC()) {
                if (m.getCell(A, B, layer).getAC().hasAnimations() && !Message.isThere()) {
                    if (!m.getCell(A, B, layer).getAC().getAnyWasClick()) { 
                       m.getCell(A, B, layer).getAC().clickAll();
                    }
                }
            }
            if (!redrew) {
                m.getCell(A, B, layer).paint(g);
            }
        }
    }

    private static class Out extends JPanel {

        public Out() {
        }

        public void paintComponent(Graphics g) {
            clicks++;
            Image frame = null;
            try {
                frame = ImageIO.read(new File("textures/frame/frame.png")).getScaledInstance(3, 1, 0);
            } catch (IOException ex) {
            }
            Darkness.clear();
            g.drawImage(frame, 0, -1, this);
            g.drawImage(frame, 25, 25, 625, 655, 2, 0, 3, 1, this);
            for (int row = 0; row < m.length; row++) {
                for (int col = 0; col < m.width; col++) {
                    clickCell(row, col, g);
                }
            }
            for (int row = 0; row < m.length; row++) {
                for (int col = 0; col < m.width; col++) {
                    for (int lay = 0; lay < m.height; lay++) {
                        if (m.getCell(row, col, lay).hasAC()) {
                            m.getCell(row, col, lay).getAC().resetAll();
                        }
                    }
                }
            }
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    wasRedrew[row][col] = false;
                }
            }

            g.drawImage(frame, 0, 25, 25, 655, 0, 0, 1, 1, this);
            g.drawImage(frame, 625, 25, 650, 655, 0, 0, 1, 1, this);
            g.drawImage(frame, 795, 25, 830, 655, 0, 0, 1, 1, this);
            g.drawImage(frame, 650, 25, 805, 655, 1, 0, 2, 1, this);
            g.drawImage(frame, 0, 0, 830, 25, 0, 0, 1, 1, this);
            g.drawImage(frame, 0, 655, 830, 680, 0, 0, 1, 1, this);
            if (!Game.statsVisible) {
                g.drawImage(frame, 650, 25, 805, 655, 1, 0, 2, 1, this);
            } else {
                g.drawImage(frame, 650, 25, 805, 655, 2, 0, 3, 1, this);
                for (int x = 0; x < 5; x++) {
                    stats[x].paint(g);
                }
            }
            if (Darkness.getTurn()) {
                Darkness.paint(g);
            }
            if (!Message.isThere()) {
                Bubble.click();
            }
            if (Bubble.isVisible()) {
                Bubble.paint(g);
            }
            if (Message.isThere()) {
                Message.paint(g);
            } else {
                Bubble.click();
            }
        }
    }
}
