package game.core;

import java.awt.Graphics;
import static out.picture.Textures.OVER9000;
import static out.picture.Textures.numbers;
import static out.picture.Textures.stats_icons;

public class Stat {

    private int count = 0;
    private final int number;

    public Stat(int number) {
        this.number = number;
    }

    public void paint(Graphics g) {
        g.drawImage(stats_icons, 655, number * 115, 679, 24 + number * 115, (number - 1) * 24, 0, (number - 1) * 24 + 24, 24, null);
        if (count <= 9000) {
            for (int h = 0; h < String.valueOf(count).length(); h++) {
                g.drawImage(numbers, 690 + h * 25, number * 115 - 5, 710 + h * 25, 29 + number * 115, Integer.parseInt(String.valueOf(count).substring(h, h + 1)) * 30, 123, Integer.parseInt(String.valueOf(count).substring(h, h + 1)) * 30 + 30, 171, null);
            }
        } else {
            g.drawImage(OVER9000, 690, number * 115 - 5, null);
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int newCount) {
        count = newCount;
    }

    public void plusCount(int plus) {
        if (plus > 0) {
            count = count + plus;
        }
    }

    public void minusCount(int minus) {
        if (minus > 0&& count - minus >= 0) {
            count = count - minus;
        }
    }
}
