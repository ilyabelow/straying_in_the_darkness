package cells.second_layer;

import game.core.Actions;
import game.Game;
import java.awt.Graphics;
import animations.Animation;
import animations.Smoke;
import cells.Cell;
import static game.Game.m;
import static game.Game.r;
import game.play.Darkness;
import static out.picture.Textures.flame;
import static out.picture.Textures.handle;
import static out.picture.Window.clickCell;

public class Torches extends Cell {

    private int argument = 1;

    public Torches(int A, int B) {
        super(A, B, new String[][]{{"wall"}}, 15, 1, "^", "torch", 16);
    }

    @Override
    public void setArgument(int newArgument) {
        setTorches(newArgument);
    }

    @Override
    public int getArgument() {
        return argument;
    }

    @Override
    public void paint(Graphics g) {
        for (int x = 0; x < 16; x++) {
            if (getAC().getAnimation(x) != null) {
                getAC().getAnimation(x).paint(g);
            }
        }
    }

    @Override
    public boolean canAction(Actions.Action action) {
        if ("actionOn".equals(action.name)) {
            if ("player".equals(action.args[0])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void doAction(Actions.Action action) {
        if ("actionOn".equals(action.name)) {
            if ("player".equals(action.args[0])) {
                if ("up".equals(action.args[1])) {
                    if (isTorchOn(2)) {
                        Game.stats[3].plusCount(1);
                        removeTorch(2);
                    } else {
                        Game.stats[3].minusCount(1);
                        setTorch(2);
                    }
                }
                if ("down".equals(action.args[1])) {
                    if (isTorchOn(1)) {
                        Game.stats[3].plusCount(1);
                        removeTorch(1);
                    } else {
                        Game.stats[3].minusCount(1);
                        setTorch(1);
                    }
                }
                if ("left".equals(action.args[1])) {
                    if (isTorchOn(4)) {
                        Game.stats[3].plusCount(1);
                        removeTorch(4);
                    } else {
                        Game.stats[3].minusCount(1);
                        setTorch(4);
                    }
                }
                if ("right".equals(action.args[1])) {
                    if (isTorchOn(3)) {
                        Game.stats[3].plusCount(1);
                        removeTorch(3);
                    } else {
                        Game.stats[3].minusCount(1);
                        setTorch(3);
                    }
                }
                if (!isTorchOn(1) & !isTorchOn(2) & !isTorchOn(3) & !isTorchOn(4)) {
                    Game.m.removeCell(getA(), getB(), 1);
                } else {
                    checkTorches(isTorchOn(1), isTorchOn(2), isTorchOn(3), isTorchOn(4));
                }
            }
        }
    }

    private void removeTorch(int place) {
        if (place == 1) {
            getAC().setAnimation(0, null);
            getAC().setAnimation(4, null);
            getAC().setAnimation(5, null);
            getAC().setAnimation(6, null);
        }
        if (place == 2) {
            getAC().setAnimation(1, null);
            getAC().setAnimation(7, null);
            getAC().setAnimation(8, null);
            getAC().setAnimation(9, null);
        }
        if (place == 3) {
            getAC().setAnimation(2, null);
            getAC().setAnimation(10, null);
            getAC().setAnimation(11, null);
            getAC().setAnimation(12, null);
        }
        if (place == 4) {
            getAC().setAnimation(3, null);
            getAC().setAnimation(13, null);
            getAC().setAnimation(14, null);
            getAC().setAnimation(15, null);
        }
    }

    private boolean isTorchOn(int place) {
        return getAC().getAnimation(place - 1) != null;
    }

    private void setTorch(int place) {
        if (place == 1) {
            getAC().setAnimation(0, new Animation("up", 2, 1, 2, new int[1]) {

                @Override
                public void shot() {
                    args[0] = r.nextInt(7);
                }

                @Override
                public void end() {
                    setShot(1);
                }

                @Override
                public void paint(Graphics g) {
                    drawTexture(flame, g, -48, 21, 18, 18, 0, 18 * args[0]);
                    Darkness.setLight(getA() * 12, getB() * 12 + 8, 20, (float) 0.25 + (float) args[0] / 100);
                }
            });
            makeSmoke(4, 2, -60, -60, 0);
            makeSmoke(5, 4, -75, -60, 0);
            makeSmoke(6, 6, -90, -60, 0);
        }
        if (place == 2) {
            getAC().setAnimation(1, new Animation("down", 2, 1, 2, new int[1]) {

                @Override
                public void shot() {
                    args[0] = r.nextInt(7);
                }

                @Override
                public void end() {
                    setShot(1);
                }

                @Override
                public void paint(Graphics g) {
                    drawTexture(flame, g, 12, 21, 18, 18, 0, 18 * args[0]);
                    drawTexture(handle, g, 30, 18, 21, 24, 0, 36);
                    Darkness.setLight(getA() * 12 + 12, getB() * 12 + 8, 20, (float) 0.25 + (float) args[0] / 100);
                }
            });
            makeSmoke(7, 2, 0, 0, 0);
            makeSmoke(8, 4, -15, 0, 0);
            makeSmoke(9, 6, -30, 0, 0);
        }
        if (place == 3) {
            getAC().setAnimation(2, new Animation("left", 2, 1, 2, new int[1]) {

                @Override
                public void shot() {
                    args[0] = r.nextInt(7);
                }

                @Override
                public void end() {
                    setShot(1);
                }

                @Override
                public void paint(Graphics g) {
                    drawTexture(flame, g, -18, -18, 18, 18, 0, 18 * args[0]);
                    drawTexture(handle, g, 0, -18, 21, 18, 0, 0);
                    Darkness.setLight(getA() * 12 + 6, getB() * 12, 20, (float) 0.25 + (float) args[0] / 100);
                }
            });
            makeSmoke(10, 2, -30, -30, -40);
            makeSmoke(11, 4, -45, -30, -40);
            makeSmoke(12, 6, -60, -30, -40);

        }
        if (place == 4) {
            getAC().setAnimation(3, new Animation("right", 2, 1, 2, new int[1]) {

                @Override
                public void shot() {
                    args[0] = r.nextInt(7);
                }

                @Override
                public void end() {
                    setShot(1);
                }

                @Override
                public void paint(Graphics g) {
                    clickCell(getA(), getB() + 1, g);
                    drawTexture(flame, g, -18, 60, 18, 18, 0, 18 * args[0]);
                    drawTexture(handle, g, 0, 60, 21, 18, 0, 18);
                    Darkness.setLight(getA() * 12 + 6, getB() * 12 + 15, 20, (float) 0.25 + (float) args[0] / 100);
                }
            });
            makeSmoke(13, 2, -30, -30, 40);
            makeSmoke(14, 4, -45, -30, 40);
            makeSmoke(15, 6, -60, -30, 40);
        }
    }

    private void makeSmoke(int place, int startSize, int VO, int startVO, int HO) {
        int offset = r.nextInt(21) - 20;
        float RS = (float) (2 - (0.5 * (((float) offset / -10) + 1)));
        float LS = 2 - RS;
        getAC().setAnimation(place, new Smoke(getA() * 60 + VO, getB() * 60 + HO - offset + 10, getA() * 60 + startVO, getB() * 60 + HO, 1, (float) 1.25, LS, RS, startSize, 9, getAC(), place));
    }

    private void setTorches(int argument) {
        if (argument == 1) {
            checkTorches(true, false, false, false);
        }
        if (argument == 2) {
            checkTorches(false, true, false, false);
        }
        if (argument == 3) {
            checkTorches(false, false, true, false);
        }
        if (argument == 4) {
            checkTorches(false, false, false, true);
        }
        if (argument == 5) {
            checkTorches(true, true, false, false);
        }
        if (argument == 6) {
            checkTorches(true, false, true, false);
        }
        if (argument == 7) {
            checkTorches(true, false, false, true);
        }
        if (argument == 8) {
            checkTorches(false, true, true, false);
        }
        if (argument == 9) {
            checkTorches(false, true, false, true);
        }
        if (argument == 10) {
            checkTorches(false, false, true, true);
        }
        if (argument == 11) {
            checkTorches(false, true, true, true);
        }
        if (argument == 12) {
            checkTorches(true, false, true, true);
        }
        if (argument == 13) {
            checkTorches(true, true, false, true);
        }
        if (argument == 14) {
            checkTorches(true, true, true, false);
        }
        if (argument == 15) {
            checkTorches(true, true, true, true);
        }
    }

    private void checkTorches(boolean up, boolean down, boolean left, boolean right) {

        if (getA() != 0) {
            if ("wall".equals(m.getCell(getA() - 1, getB(), 0).getName()) | "sign".equals(m.getCell(getA() - 1, getB(), 0).getName())) {
                up = false;
            }
            if ("door".equals(m.getCell(getA() - 1, getB(), 1).getName()) & m.getCell(getA() - 1, getB(), 1).getArgument() == 1) {
                up = false;
            }
        }
        if (getA() != 9) {
            if ("wall".equals(m.getCell(getA() + 1, getB(), 0).getName()) | "sign".equals(m.getCell(getA() + 1, getB(), 0).getName())) {
                down = false;
            }
            if ("door".equals(m.getCell(getA() + 1, getB(), 1).getName()) & m.getCell(getA() + 1, getB(), 1).getArgument() == 1) {
                down = false;
            }
        }
        if (getB() != 0) {
            if ("wall".equals(m.getCell(getA(), getB() - 1, 0).getName()) | "sign".equals(m.getCell(getA(), getB() - 1, 0).getName())) {
                left = false;
            }
            if ("door".equals(m.getCell(getA(), getB() - 1, 1).getName()) & m.getCell(getA(), getB() - 1, 1).getArgument() == 1) {
                left = false;
            }
        }
        if (getB() != 9) {
            if ("wall".equals(m.getCell(getA(), getB() + 1, 0).getName()) | "sign".equals(m.getCell(getA(), getB() + 1, 0).getName())) {
                right = false;
            }
            if ("door".equals(m.getCell(getA(), getB() + 1, 1).getName()) & m.getCell(getA(), getB() + 1, 1).getArgument() == 1) {
                right = false;
            }
        }
        argument = getArgument(up, down, left, right);
        if (up) {
            setTorch(1);
        } else {
            removeTorch(1);
        }
        if (down) {
            setTorch(2);
        } else {
            removeTorch(2);
        }
        if (left) {
            setTorch(3);
        } else {
            removeTorch(3);
        }
        if (right) {
            setTorch(4);
        } else {
            removeTorch(4);
        }
    }

    public static int getArgument(boolean up, boolean down, boolean left, boolean right) {
        if (up) {
            return 1;
        }
        if (down) {
            return 2;
        }
        if (left) {
            return 3;
        }
        if (right) {
            return 4;
        }
        if (up & down) {
            return 5;
        }
        if (up & left) {
            return 6;
        }
        if (up & right) {
            return 7;
        }
        if (down & left) {
            return 8;
        }
        if (down & right) {
            return 9;
        }
        if (right & left) {
            return 10;
        }
        if (down & left & right) {
            return 11;
        }
        if (up & left & right) {
            return 12;
        }
        if (up & down & right) {
            return 13;
        }
        if (up & down & left) {
            return 14;
        }
        if (up & down & left & right) {
            return 15;
        }
        return 0;
    }

}
