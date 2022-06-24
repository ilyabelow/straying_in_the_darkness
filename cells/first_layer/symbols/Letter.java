package cells.first_layer.symbols;

import java.awt.Graphics;
import static out.picture.Textures.russian_letters;

public class Letter extends Symbol {

    private final String language;

    public Letter(int A, int B, String language, boolean onGround) {
        super(A, B, getLetters(language), language.substring(0, 1), language + "_letter", onGround);
        this.language = language;
    }

    @Override
    public void paint(Graphics g) {
        if (!getGrounded()) {
            drawTexture(russian_letters, g, -30, 0, 90, 60, ((getArgument() - 1) / 6) * 90, ((getArgument() - 1) % 6) * 60);
        } else {
            drawTexture(russian_letters, g, 0, 0, 60, 60, ((getArgument() - 1) / 6) * 60, ((getArgument() - 1) % 6) * 60 + 360);
        }
    }

    private static int getLetters(String language) {
        int number = 0;
        if (language.equals("russian")) {
            number = 33;
        }
        return number;
    }

}
