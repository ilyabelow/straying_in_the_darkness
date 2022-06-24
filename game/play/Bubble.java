package game.play;

import java.awt.Graphics;
import out.picture.Window;
import static out.picture.Window.speedCoefficient;
import static out.picture.Textures.bubble;

public class Bubble {

    private static int edge;
    private static int A = 6, B = 5;
    private static int mineVO = 0, mineHO = 0, jumpO = 0;
    private static String[] message;
    private static int messageClicks = 0;

    public static void paint(Graphics g) {
        if (A != 0 && B != 9) {
            drawTexture(g, mineVO - jumpO - 100, mineHO + 50, 0, 120);
        }
        if (A == 0 && B != 9) {
            drawTexture(g, mineVO - jumpO, mineHO + 50, 90, 120);
        }
        if (A != 0 && B == 9) {
            drawTexture(g, mineVO - jumpO - 100, mineHO - 110, 0, 0);
        }
        if (A == 0 && B == 9) {
            drawTexture(g, mineVO - jumpO, mineHO - 110, 90, 0);
        }
    }

    public static void setOffsets(int VO, int HO, int JO) {
        mineVO = VO;
        mineHO = HO;
        jumpO = JO;
    }

    public static void click() {
        if (messageClicks < (Window.getCpS() / speedCoefficient) * edge) {
            messageClicks++;
        } else {
            messageClicks = 0;
            edge = 0;
            message = null;
        }
    }

    public static void say(String[] newMessage) {
        message = newMessage;
        String allMessage = message[0] + message[1] + message[2] + message[3] + message[4];
        edge = allMessage.length();
    }

    public static boolean isVisible() {
        return message != null;
    }

    public static void setCoordinates(int newA, int newB) {
        A = newA;
        B = newB;
    }

    private static void drawTexture(Graphics g, int VO, int HO, int textureA, int textureB) {
        g.drawImage(bubble,
                B * 60 + HO + Window.HO,
                A * 60 + VO + Window.VO,
                B * 60 + HO + 120 + Window.HO,
                A * 60 + VO + 90 + Window.VO,
                textureB,
                textureA,
                textureB + 120,
                textureA + 90,
                null);
        
        g.drawString(message[0], B * 60 + HO + Window.HO + 20, A * 60 + VO + Window.VO + 17);
        g.drawString(message[1], B * 60 + HO + Window.HO + 10, A * 60 + VO + Window.VO + 32);
        g.drawString(message[2], B * 60 + HO + Window.HO + 5, A * 60 + VO + Window.VO + 47);
        g.drawString(message[3], B * 60 + HO + Window.HO + 10, A * 60 + VO + Window.VO + 62);
        g.drawString(message[4], B * 60 + HO + Window.HO + 20, A * 60 + VO + Window.VO + 77);
    }
}
