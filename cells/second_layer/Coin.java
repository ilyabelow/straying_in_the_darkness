package cells.second_layer;

import game.core.Actions;
import java.awt.Graphics;
import java.util.Random;
import game.Game;
import static game.Game.m;
import animations.Animation;
import cells.Cell;
import static out.picture.Textures.coins;
import static out.picture.Textures.coin_shadow;

public class Coin extends Cell {

    private boolean rising = false;

    public Coin(int A, int B) {
        super(A, B, new String[][]{{"way"}}, 5, 1, "0", "coin", 1);
        rising = new Random().nextBoolean();
        getAC().setAnimation(new Animation("up", 2, 1, 2, new int[]{new Random().nextInt(9)}) {

            @Override
            public void shot() {
                if (rising) {
                    if (args[0] < 8) {
                        args[0]++;
                    } else {
                        args[0]--;
                        rising = false;
                    }
                } else {
                    if (args[0] > 0) {
                        args[0]--;
                    } else {
                        args[0]++;
                        rising = true;
                    }
                }
            }

            @Override
            public void end() {
                setShot(1);
                shot();
            }

            @Override
            public void paint(Graphics g) {
                drawTexture(coin_shadow, g, 27, 18, 6, 24, args[0] * 6, 0);
                drawTexture(coins, g, -20 - (args[0] * 2), 12, 36, 36, 0, (getArgument() - 1) * 36);
            }
        });
    }

    public void paint(Graphics g) {
        getAC().getAnimation().paint(g);

    }

    public boolean canAction(Actions.Action action) {
        return "moveOn".equals(action.name);
    }

    public void doAction(Actions.Action action) {
        if ("moveOn".equals(action.name) & "player".equals(action.args[0])) {
            if (getArgument() < 5) {
                Game.stats[0].plusCount(1);
            } else {
                Game.stats[4].plusCount(1);
            }
            getAC().setAnimation(new Animation("remove", 7, 0, 2, new int[]{getAC().getAnimation().args[0], 0}) {
                @Override
                public void shot() {
                    args[0] = args[0] + 2;
                    if (args[1] == 0) {
                        args[1] = 1;
                    } else {
                        args[1] = 0;
                    }

                }

                @Override
                public void end() {
                    m.removeCell(getA(), getB(), 1);
                }

                @Override
                public void paint(Graphics g) {
                    if (args[1] == 0) {
                        drawTexture(coins, g, -20 - (args[0] * 2), 12, 36, 36, 0, (getArgument() - 1) * 36);
                    }
                    drawTexture(coin_shadow, g, 27, 18, 6, 24, args[0] * 6, 0);
                }
            });
        }
    }

}
