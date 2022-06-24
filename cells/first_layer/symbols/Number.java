package cells.first_layer.symbols;

import java.awt.Graphics;
import static out.picture.Textures.numbers;

public class Number extends Symbol {

    public Number(int A, int B, boolean onGround) {
        super(A, B, 10, "n", "number", onGround);
    }
    @Override
    public void paint(Graphics g) {
        if (!getGrounded()) {
            drawTexture(numbers, g, -15, 15, 75, 30, 0, (getArgument() - 1) * 30);
        } else {
            drawTexture(numbers, g, 6, 15, 48, 30, 75, (getArgument() - 1) * 30);
            
        }
    }
}
