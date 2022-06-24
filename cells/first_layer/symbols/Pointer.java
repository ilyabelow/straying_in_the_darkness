package cells.first_layer.symbols;

import java.awt.Graphics;
import static out.picture.Window.HO;
import static out.picture.Window.VO;
import static out.picture.Textures.pointers;

public class Pointer extends Symbol {

    private int argument = 1;
    private int pointerStyle, tailStyle, turnStyle;

    public Pointer(int A, int B, boolean onGround) {
        super(A, B, 12, "f", "pointer", onGround);
    }

    @Override
    public void setArgument(int newArgument) {
        if (newArgument > 0 & newArgument <= 12) {
            argument = newArgument;
        }
        if (argument == 2 || argument == 7 || argument == 10) {
            pointerStyle = 1;
        }
        if (argument == 5 || argument == 9 || argument == 12) {
            pointerStyle = 2;
        }
        if (argument == 1 || argument == 4 || argument == 8) {
            pointerStyle = 3;
        }
        if (argument == 3 || argument == 6 || argument == 11) {
            pointerStyle = 4;
        }
        if (argument == 1 || argument == 2 || argument == 3) {
            tailStyle = 1;
        }
        if (argument == 4 || argument == 5 || argument == 6) {
            tailStyle = 2;
        }
        if (argument == 7 || argument == 8 || argument == 9) {
            tailStyle = 3;
        }
        if (argument == 10 || argument == 11 || argument == 12) {
            tailStyle = 4;
        }
        if (argument == 1 || argument == 12) {
            turnStyle = 1;
        }
        if (argument == 2 || argument == 5) {
            turnStyle = 2;
        }
        if (argument == 3 || argument == 9) {
            turnStyle = 3;
        }
        if (argument == 4 || argument == 10) {
            turnStyle = 4;
        }
        if (argument == 6 || argument == 7) {
            turnStyle = 5;
        }
        if (argument == 8 || argument == 11) {
            turnStyle = 6;
        }
    }

    @Override
    public int getArgument() {
        return argument;
    }

    @Override
    public void paint(Graphics g) {
        if (!getGrounded()) {
            if (pointerStyle != 2) {
                drawTexture(pointers, g, -30, 0, 90, 60, 0, (pointerStyle - 1) * 60);
                drawTexture(pointers, g, -30, 0, 90, 60, 90, (tailStyle - 1) * 60);
                drawTexture(pointers, g, -6, 27, 36, 6, 180, (turnStyle - 1) * 6);
            } else {
                drawTexture(pointers, g, -30, 0, 90, 60, 90, (tailStyle - 1) * 60);
                drawTexture(pointers, g, -30, 0, 90, 60, 0, (pointerStyle - 1) * 60);
                drawTexture(pointers, g, -6, 27, 36, 6, 180, (turnStyle - 1) * 6);
            }
        } else {
            drawTexture(pointers, g, 0, 0, 60, 60, 216, (pointerStyle - 1) * 60);
            drawTexture(pointers, g, 0, 0, 60, 60, 276, (tailStyle - 1) * 60);
            drawTexture(pointers, g, 24, 27, 9, 6, 336, (turnStyle - 1) * 6);
        }
    }
}
