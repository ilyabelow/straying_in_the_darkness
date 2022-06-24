package cells.first_layer;

import animations.Dust;
import cells.Cell;
import cells.second_layer.Torches;
import game.core.Actions;
import game.Game;
import java.awt.Graphics;
import static out.picture.Textures.walls;
import static game.Game.m;

public class Wall extends Cell {

    public static final int maxArgument = 5;

    public Wall(int A, int B) {
        super(A, B, null, maxArgument, 0, "#", "wall", 0);
    }

    public void paint(Graphics g) {
        drawTexture(walls[getArgument() - 1], g, -30, 0);
    }

    public boolean canAction(Actions.Action action) {
        return "actionOn".equals(action.name) && "empty".equals(m.getCell(getA(), getB(), 1).getName()) && "empty".equals(m.getCell(getA(), getB(), 2).getName()) && Game.stats[3].getCount() > 0;
    }

    public void doAction(Actions.Action action) {
        if ("actionOn".equals(action.name)) {
            if (canAction(action)) {
                Game.stats[3].minusCount(1);
                m.setCell(new Torches(getA(), getB()));
                if ("up".equals(action.args[1])) {
                    m.getCell(getA(), getB(), 1).setArgument(2);
                }
                if ("down".equals(action.args[1])) {
                    m.getCell(getA(), getB(), 1).setArgument(1);
                }
                if ("left".equals(action.args[1])) {
                    m.getCell(getA(), getB(), 1).setArgument(4);
                }
                if ("right".equals(action.args[1])) {
                    m.getCell(getA(), getB(), 1).setArgument(3);
                }
            }
        }
    }
}
