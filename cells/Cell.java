package cells;

import game.core.Actions;
import java.awt.Graphics;
import java.awt.Image;
import animations.AnimationsController;
import out.picture.Window;

public abstract class Cell {

    private int argument = 1;
    private final int maxArgument;
    private final String mark;
    private final String name;
    private final int validLayer;
    private int A, B;
    private final String[][] validCellsUnder;
    private AnimationsController AC;
    private final int numberOfAnimations;

    public Cell(int A, int B, String[][] validCellsUnder, int maxArgument, int validLayer, String mark, String name, int numberOfAnimations) {
        this.validCellsUnder = validCellsUnder;
        this.maxArgument = maxArgument;
        this.validLayer = validLayer;
        this.mark = mark;
        this.name = name;
        this.A = A;
        this.B = B;
        this.numberOfAnimations = numberOfAnimations;
        if (numberOfAnimations > 0) {
            AC = new AnimationsController(numberOfAnimations);
        }
    }

    public void setArgument(int newArgument) {
        if (newArgument > 0 & newArgument <= maxArgument) {
            argument = newArgument;
        }
    }

    public int getA() {
        return A;
    }

    public int getB() {
        return B;
    }

    public final String getMark() {
        return mark;
    }

    public int getArgument() {
        return argument;
    }

    public final int getValidLayer() {
        return validLayer;
    }

    public final String getName() {
        return name;
    }

    public final String[][] getValidCellsUnder() {
        return validCellsUnder;
    }

    public final AnimationsController getAC() {
        return AC;
    }

    public final boolean hasAC() {
        return AC != null;
    }

    public final void drawTexture(Image texture, Graphics g, int VO, int HO) {
        drawTexture(texture, g, A, B, VO, HO);
    }

    public final void drawTexture(Image texture, Graphics g, int A, int B, int VO, int HO) {
        g.drawImage(texture, B * 60 + HO + Window.HO,A * 60 + VO + Window.VO,  null);
    }

    public final void drawTexture(Image texture, Graphics g, int cellVO, int cellHO, int textureLength, int textureWidth, int textureA, int textureB) {
        drawTexture(texture, g, A, B, cellVO, cellHO, textureLength, textureWidth, textureA, textureB);
    }

    public final void drawTexture(Image texture, Graphics g, int windowA, int windowB, int cellVO, int cellHO, int textureLength, int textureWidth, int textureA, int textureB) {
        g.drawImage(texture,
                windowB * 60 + cellHO + Window.HO,
                windowA * 60 + cellVO + Window.VO,
                windowB * 60 + cellHO + textureWidth + Window.HO,
                windowA * 60 + cellVO + textureLength + Window.VO,
                textureB,
                textureA,
                textureB + textureWidth,
                textureA + textureLength,
                null);
    }

    public abstract void paint(Graphics g);

    public abstract boolean canAction(Actions.Action action);

    public abstract void doAction(Actions.Action action);
}
