package cells.second_layer;

import game.core.Actions;
import game.Game;
import java.awt.Graphics;
import java.util.Random;
import animations.Animation;
import cells.Cell;
import static out.picture.Textures.chest;

public class Chest extends Cell {

    public Chest(int A, int B) {
        super(A, B, new String[][]{{"way"}}, 2, 1, "+", "chest", 1);
    }

    @Override
    public void paint(Graphics g) {
        if (getAC().hasAnimations()) {
            getAC().getAnimation().paint(g);
        } else {
            drawTexture(chest, g, -9, 9, 39, 39, 0, (getArgument() - 1) * 39);
        }
    }

    @Override
    public boolean canAction(Actions.Action action) {
        return "moveOn".equals(action.name);
    }

    @Override
    public void doAction(Actions.Action action) {
        if ("moveOn".equals(action.name) & getArgument() == 1) {
            if ("player".equals(action.args[0])) {
                Game.stats[0].plusCount(new Random().nextInt(10) + 1);
                Game.stats[4].plusCount(new Random().nextInt(7) - 5);
                getAC().setAnimation(new Animation("opening", 3, 1, 3, null) {
                    @Override
                    public void shot() {
                    }

                    @Override
                    public void end() {
                        getAC().setAnimation(null);
                        setArgument(2);
                    }

                    @Override
                    public void paint(Graphics g) {
                        drawTexture(chest, g, -9, 9, 39, 39, getShot() * 39, 0);
                    }
                });
            }
        }
    }

}
