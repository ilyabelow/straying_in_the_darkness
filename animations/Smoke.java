package animations;

import static game.Game.r;
import java.awt.Graphics;
import static out.picture.Textures.dust;
import static out.picture.Textures.smoke;
import static out.picture.Window.HO;
import static out.picture.Window.VO;

public class Smoke extends Animation {

    private float fA, fB;
    private int A, B;
    private int startA, startB;
    private float speedLeft, speedRight, speedUp;
    private int size;
    private final int endSize, place;
    private final AnimationsController owner;
    private final float startSpeedLeft, startSpeedRight;

    public Smoke(int A, int B, int startA, int startB, int speedV, float speedUp, float speedLeft, float speedRight, int startSize, int endSize, AnimationsController owner, int place) {
        super("smoke", endSize - startSize, 1, speedV, new int[]{});
        this.endSize = endSize;
        size = startSize;
        this.speedLeft = speedLeft;
        this.speedRight = speedRight;
        this.speedUp = speedUp;
        this.A = A;
        this.B = B;
        startSpeedLeft = speedLeft;
        startSpeedRight = speedRight;
        this.startA = startA;
        this.startB = startB;
        this.owner = owner;
        this.place = place;
        fA = (float) A;
        fB = (float) B;
    }

    @Override
    public void shot() {

        fA = fA - speedUp;
        if (r.nextBoolean()) {
            fB = fB + speedRight;
        } else {
            fB = fB - speedLeft;
        }
        A = (int) fA;
        B = (int) fB;
    }

    @Override
    public void end() {
        if (size != endSize) {
            size++;
            speedRight = speedRight - (float) (speedRight / 3);
            speedLeft = speedLeft - (float) (speedLeft / 3);
            speedUp = speedUp - (float) (speedUp / 5);
            setShot(1);
            fA = (float) (fA - 1.0);
            fB = (float) (fB - 1.0);
        } else {
            int offset = r.nextInt(21) - 20;
            float RS = (float) (2 - (0.5 * (((float) offset / -10) + 1)));
            float LS = 2 - RS;
            owner.setAnimation(place, new Smoke(startA, startB - offset + 10, startA, startB, 1, (float) 1.25, LS, RS, 2, 9, owner, place));
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(smoke, B + HO, A + VO, B + HO + 24, A + VO + 24, size * 24, 0, size * 24 + 24, 24, null);
    }

}
