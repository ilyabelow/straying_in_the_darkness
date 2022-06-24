package animations;

import java.awt.Graphics;
import out.picture.Window;
import static out.picture.Window.speedCoefficient;

public abstract class Animation {

    public final int[] args;
    private int speed;
    private final int maxShot, CpS;
    public final String name;
    private int clicks = 1, shot = 1;
    private boolean wasShot = true, wasClick = true;

    public Animation(String name, int maxShot, int startShot, int speed, int[] args) {
        this.name = name;
        this.maxShot = maxShot;
        this.args = args;
        shot = startShot;
        CpS = speed;
        this.speed = Window.getCpS() / speedCoefficient * CpS;
    }

    public void click() {
        speed = Window.getCpS() / speedCoefficient * CpS;
        if (clicks < speed) {
            clicks++;
            wasClick = true;
        } else {
            clicks = 1;
            if (shot < maxShot) {
                shot++;
                shot();
            } else {
                end();
            }
            wasShot = true;
        }
    }

    public int getShot() {
        return shot;
    }
    public boolean getWasShot() {
        return wasShot;
    }

    public boolean getWasClick() {
        return wasClick;
    }

    public void setShot(int newShot) {
        if (newShot > 0 | newShot <= maxShot) {
            shot = newShot;
        }
    }

    public void reset() {
        wasShot = false;
        wasClick = false;
    }

    public abstract void shot();

    public abstract void end();

    public abstract void paint(Graphics g);
}
