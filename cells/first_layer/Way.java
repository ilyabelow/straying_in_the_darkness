package cells.first_layer;

import cells.Cell;
import game.core.Actions;
import java.awt.Graphics;
import static out.picture.Textures.ways;

public class Way extends Cell {

    public Way(int A, int B) {
        super(A, B, null, 5, 0, "~", "way", 0);
    }

    public void paint(Graphics g) {
        drawTexture(ways[getArgument() - 1], g, -20, 0);
    }

    public boolean canAction(Actions.Action action) {
        return "moveOn".equals(action.name);
    }

    public void doAction(Actions.Action action) {

    }

}
