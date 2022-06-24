package game.play;

import java.awt.Graphics;
import static out.picture.Window.HO;
import static out.picture.Window.VO;

public class Darkness {

    private static int[][] darkness = new int[130][124];
    private static boolean turn = false;

    public static void turn(boolean what) {
        turn = what;
    }

    public static void clear() {
        for (int A = 0; A < 130; A++) {
            for (int B = 0; B < 124; B++) {
                darkness[A][B] = 20;
            }
        }
    }

    public static boolean getTurn() {
        return turn;
    }

    public static void paint(Graphics g) {
        for (int A = 0; A < 130; A++) {
            for (int B = 0; B < 124; B++) {
                if (darkness[A][B] > 1) {
                    g.drawImage(out.picture.Textures.darkness[darkness[A][B] - 1], B * 5 + HO - 12, A * 5 + VO - 30 - 12, null);
                }
            }
        }
    }

    public static void setLight(int A, int B, int radius, float intensity) {
        if (turn) {
            int height = radius;
            int width = -radius;
            float light = (float) 0.2;
            float x = (float) 1.25;
            while (height < (float) radius * 1.5) {
                drawLine(A - (height / 2), height, B + width, light);
                height++;
                height++;
                width++;
                light = (float) (light + (float) (intensity * x));
            }
            while (height < radius * 2) {
                drawLine(A - (height / 2), height, B + width, light);
                height++;
                width++;
                light = (float) (light + (float) (intensity / x));
            }
            while (width < 0) {
                drawLine(A - (height / 2), height, B + width, light);
                width++;
                light = (float) (light + (float) (intensity / x));
            }
            while (width < radius / 2) {
                drawLine(A - (height / 2), height, B + width, light);
                width++;
                light = (float) (light - (float) (intensity / x));
            }
            while (height > (float) radius * 1.5) {
                drawLine(A - (height / 2), height, B + width, light);
                height--;
                width++;
                light = (float) (light - (float) (intensity / x));
            }
            while (height >= radius) {
                drawLine(A - (height / 2), height, B + width, light);
                height--;
                height--;
                width++;
                light = (float) (light - (float) (intensity * x));
            }
        }
    }

    private static void drawLine(int startA, int lenght, int B, float light) {
        float number = 25;
        boolean minusA = false, minusB = false;
        for (int A = 0; A <= lenght; A++) {
            if (A > lenght / 2) {
                number = number + (light * (float) (number / lenght));
            } else {
                number = number - (light * (float) (number / lenght));
            }
            try {
                if (number < darkness[A + startA][B]) {
                    darkness[A + startA][B] = (int) number;
                }
            } catch (java.lang.ArrayIndexOutOfBoundsException ex) {
            }
        }
    }
}
