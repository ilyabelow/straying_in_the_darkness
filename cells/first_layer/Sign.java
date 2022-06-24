package cells.first_layer;

import cells.Cell;
import game.core.Actions;
import game.play.Bubble;
import java.awt.Graphics;
import static out.picture.Textures.sign;

public class Sign extends Cell {

    public Sign(int A, int B) {
        super(A, B, null, 17, 0, "/", "sign",0);
    }

    @Override
    public void paint(Graphics g) {
        drawTexture(sign, g, -30, 0, 90,60,0,0);
    }

    @Override
    public boolean canAction(Actions.Action action) {
        return "actionOn".equals(action.name);
    }

    @Override
    public void doAction(Actions.Action action) {
        if ("actionOn".equals(action.name)) {
            if ("player".equals(action.args[0]) && !Bubble.isVisible()) {
                if (getArgument() == 1) {
                    Bubble.say(new String[]{"", "", " «Выход сверху»", "", ""});
                }
                if (getArgument() == 2) {
                    Bubble.say(new String[]{"", "", " «Выход снизу»", "", ""});
                }
                if (getArgument() == 3) {
                    Bubble.say(new String[]{"", "", " «Выход слева»", "", ""});
                }
                if (getArgument() == 4) {
                    Bubble.say(new String[]{"", "", " «Выход справа»", "", ""});
                }
                if (getArgument() == 5) {
                    Bubble.say(new String[]{"", "", " «Выход совсем ", "      рядом!»", ""});
                }
                if (getArgument() == 6) {
                    Bubble.say(new String[]{"", "«Per Googlium", "      Ad Astra»", "", ""});
                }
                if (getArgument() == 7) {
                    Bubble.say(new String[]{"", "", "   «Hello World!»", "", ""});
                }
                if (getArgument() == 8) {
                    Bubble.say(new String[]{"", "     «Съесть", "         после ", "    прочтения»", ""});
                }
                if (getArgument() == 9) {
                    Bubble.say(new String[]{"", "", "«Вы находитесь", "       здесь»", ""});
                }
                if (getArgument() == 10) {
                    Bubble.say(new String[]{"", "       «Это", "  утверждение", "      ложно!»", ""});
                }
                if (getArgument() == 11) {
                    Bubble.say(new String[]{"", "", "        «42!»", "", ""});
                }
                if (getArgument() == 12) {
                    Bubble.say(new String[]{"",           "    «Туалет",    " не работает»", "", ""});
                }
                if (getArgument() == 13) {
                    Bubble.say(new String[]{"", "         Всё", "  неразборчиво", "", ""});
                }
                if (getArgument() == 14) {
                    Bubble.say(new String[]{"«Переходи", "   на сторону", "       зла - у", "     нас есть", " печеньки!»"});
                }
                if (getArgument() == 15) {      
                    Bubble.say(new String[]{"", "  «Не забудьте", "    взять каску", "   при входе!»", ""});
                }
                if (getArgument() == 16) {      
                    Bubble.say(new String[]{" «Ключи от ", "   аварийных ", "  выходов нахо-", "    дятся на ", "   вахте»"});
                }
                if (getArgument() == 17) {
                    Bubble.say(new String[]{"", "", "  «Выход там»", "", ""});
                }
            }
        }
    }

}
