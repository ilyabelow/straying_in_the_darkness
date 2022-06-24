package cells.second_layer;

import game.core.Actions;
import game.Game;
import java.awt.Graphics;
import animations.Animation;
import animations.Dust;
import cells.Cell;
import static game.Game.r;
import out.picture.Window;
import static out.picture.Textures.door;

public class Door extends Cell {

    private int argument = 1;

    public Door(int A, int B) {
        super(A, B, new String[][]{{"~", "r", "f", "n"}}, 2, 1, "[", "door", 21);
    }

    @Override
    public void paint(Graphics g) {
        if (getAC().getAnimation() != null) {
            getAC().getAnimation().paint(g);
        } else {
            if (getArgument() == 1) {
                drawTexture(door, g, -30, 0, 90, 60, 0, 0);
            } else {
                drawTexture(door, g, 0, 0, 60, 60, 0, 0);
            }
        }
        for (int x = 1; x < 21; x++) {
            if (getAC().getAnimation(x) != null) {
                getAC().getAnimation(x).paint(g);
            }
        }
    }

    public void setArgument(int newArgument) {
        if (newArgument > 0 & newArgument <= 2) {
            argument = newArgument;
        }
    }

    public int getArgument() {
        return argument;
    }

    @Override
    public boolean canAction(Actions.Action action) {
        if ("actionOn".equals(action.name)) {
            if ("player".equals(action.args[0]) & getArgument() == 1 & Game.stats[4].getCount() > 0 & !getAC().hasAnimations()) {
                return true;
            }
        }
        if ("moveOn".equals(action.name)) {
            return getArgument() == 2;
        }
        return false;
    }

    @Override
    public void doAction(Actions.Action action) {
        if ("actionOn".equals(action.name) & canAction(action)) {
            Game.stats[4].minusCount(1);
            getAC().setAnimation(new Animation("opening", 10, 1, 2, new int[1]) {

                @Override
                public void shot() {
                    if (getShot() <= 3) {
                        args[0] = args[0] + 1;
                    }
                    if (getShot() >= 4 & getShot() <= 7) {
                        args[0] = args[0] + 2;
                    }
                    if (getShot() >= 8 & getShot() <= 10) {
                        args[0] = args[0] + 5;
                    }

                }

                @Override
                public void end() {
                    Window.boom();
                    getAC().setAnimation(1, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 - 10, 2, (float) r.nextInt(5) - 2, (float) -2.0, 3, 7, getAC(), 1));
                    getAC().setAnimation(2, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 - 10, 2, (float) r.nextInt(5) - 2, (float) -2.0, 4, 8, getAC(), 2));
                    getAC().setAnimation(3, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 - 10, 2, (float) r.nextInt(5) - 2, (float) -2.0, 3, 7, getAC(), 3));
                    getAC().setAnimation(4, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 - 10, 2, (float) r.nextInt(5) - 2, (float) -2.0, 4, 8, getAC(), 4));
                    getAC().setAnimation(5, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 - 10, 2, (float) r.nextInt(5) - 2, (float) -2.0, 3, 7, getAC(), 5));
                    getAC().setAnimation(6, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 + 60, 2, (float) r.nextInt(5) - 2, (float) 2.0, 4, 8, getAC(), 6));
                    getAC().setAnimation(7, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 + 60, 2, (float) r.nextInt(5) - 2, (float) 2.0, 3, 7, getAC(), 7));
                    getAC().setAnimation(8, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 + 60, 2, (float) r.nextInt(5) - 2, (float) 2.0, 4, 8, getAC(), 8));
                    getAC().setAnimation(9, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 + 60, 2, (float) r.nextInt(5) - 2, (float) 2.0, 3, 7, getAC(), 9));
                    getAC().setAnimation(10, new Dust(getA() * 60 + r.nextInt(61), getB() * 60 + 60, 2, (float) r.nextInt(5) - 2, (float) 2.0, 4, 8, getAC(), 10));
                    getAC().setAnimation(11, new Dust(getA() * 60 - 10, getB() * 60 + r.nextInt(61), 2, (float) -2.0, (float) r.nextInt(5) - 2, 3, 7, getAC(), 11));
                    getAC().setAnimation(12, new Dust(getA() * 60 - 10, getB() * 60 + r.nextInt(61), 2, (float) -2.0, (float) r.nextInt(5) - 2, 4, 8, getAC(), 12));
                    getAC().setAnimation(13, new Dust(getA() * 60 - 10, getB() * 60 + r.nextInt(61), 2, (float) -2.0, (float) r.nextInt(5) - 2, 3, 7, getAC(), 13));
                    getAC().setAnimation(14, new Dust(getA() * 60 - 10, getB() * 60 + r.nextInt(61), 2, (float) -2.0, (float) r.nextInt(5) - 2, 4, 8, getAC(), 14));
                    getAC().setAnimation(15, new Dust(getA() * 60 - 10, getB() * 60 + r.nextInt(61), 2, (float) -2.0, (float) r.nextInt(5) - 2, 3, 7, getAC(), 15));
                    getAC().setAnimation(16, new Dust(getA() * 60 + 60, getB() * 60 + r.nextInt(61), 2, (float) 2.0, (float) r.nextInt(5) - 2, 4, 8, getAC(), 16));
                    getAC().setAnimation(17, new Dust(getA() * 60 + 60, getB() * 60 + r.nextInt(61), 2, (float) 2.0, (float) r.nextInt(5) - 2, 3, 7, getAC(), 17));
                    getAC().setAnimation(18, new Dust(getA() * 60 + 60, getB() * 60 + r.nextInt(61), 2, (float) 2.0, (float) r.nextInt(5) - 2, 4, 8, getAC(), 18));
                    getAC().setAnimation(19, new Dust(getA() * 60 + 60, getB() * 60 + r.nextInt(61), 2, (float) 2.0, (float) r.nextInt(5) - 2, 3, 7, getAC(), 19));
                    getAC().setAnimation(20, new Dust(getA() * 60 + 60, getB() * 60 + r.nextInt(61), 2, (float) 2.0, (float) r.nextInt(5) - 2, 4, 8, getAC(), 20));
                    setArgument(2);
                    getAC().setAnimation(null);

                }

                @Override
                public void paint(Graphics g) {
                    drawTexture(door, g, 30 + args[0], 0, 27 - args[0], 60, 60, 0);
                    drawTexture(door, g, -30 + args[0], 0, 60, 60, 0, 0);
                    drawTexture(door, g, 57, 0, 3, 60, 87, 0);
                }
            });
        }
    }

}
