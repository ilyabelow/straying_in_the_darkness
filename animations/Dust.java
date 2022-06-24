package animations;

import animations.Animation;
import java.awt.Graphics;
import static out.picture.Textures.dust;
import static out.picture.Window.HO;
import static out.picture.Window.VO;

public class Dust extends Animation {

    private float fA, fB;
    private int A, B;
    private float speedA, speedB;
    private int size;
    private final int endSize, place;
    private final AnimationsController owner;

    public Dust(int A, int B, int speedV, float speedA, float speedB, int startSize, int endSize, AnimationsController owner, int place) {
        super("dust", endSize - startSize, 1, speedV, new int[]{});
        this.endSize = endSize;
        size = startSize;
        this.speedA = speedA;
        this.speedB = speedB;
        this.A = A;
        this.B = B;
        this.owner = owner;
        this.place = place;
        fA = (float) A;
        fB = (float) B;
    }

    @Override
    public void shot() {
        fA = fA + speedA;
        fB = fB + speedB;
        A = (int) fA;
        B = (int) fB;
    }

    @Override
    public void end() {
        if (size != endSize) {
            size++;
            speedA = speedA - (float) (speedA / 2.5);
            speedB = speedB - (float) (speedA / 2.5);
            fA = (float) (fA - 1.0);
            fB = (float) (fB - 1.0);
            setShot(1);
        } else {
            owner.setAnimation(place, null);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(dust[size - 1], B + HO, A + VO, null);
    }

}
