package game.core;

import game.core.Records;
import build.Build;
import game.Game;
import static game.core.Records.getLastRecord;
import java.awt.Graphics;
import static out.picture.Textures.darkness;
import static out.picture.Textures.game_pause_message;
import static out.picture.Textures.generating_done_message;
import static out.picture.Textures.input_message;
import static out.picture.Textures.menu_pause_message;
import static out.picture.Textures.records_font;
import static out.picture.Textures.reset_message;
import static out.picture.Textures.score_message;
import static out.picture.Textures.win_message;

public class Message {

    static int score;

    private static Mess currentMess;

    public static final Mess MENU_PAUSE = new Mess() {

        @Override
        public void spase() {
            setMess(null);
        }

        @Override
        public void escape() {
            System.exit(1);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(menu_pause_message, 85, 235, null);
        }

    };
    public static final Mess SCORE = new Mess() {

        @Override
        public void spase() {
            if (score >= getLastRecord()) {
                setMess(INPUT_NAME);
                Game.inputName(true);
            } else {
                Build.start(Game.getSave());
                Game.fromGameToRecords();
                setMess(null);
            }
        }

        @Override
        public void escape() {
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(score_message, 85, 55, null);
            int[] stats = new int[4];
            for (int stat = 0; stat < 4; stat++) {
                boolean statIsMinus = false;
                stats[stat] = Game.stats[stat].getCount();
                if (stat == 0) {
                    stats[stat] = stats[stat] * 10;
                }
                if (stat == 1) {
                    stats[stat] = stats[stat] * 1;
                }
                if (stat == 2) {
                    stats[stat] = stats[stat] * 15;
                }
                if (stat == 3) {
                    stats[stat] = stats[0] - stats[1] - stats[2];
                }
                if (stats[stat] > 9000) {
                    stats[stat] = 9000;
                }
                if (stats[stat] < 0) {
                    stats[stat] = -stats[stat];
                    statIsMinus = true;
                }
                String strStat = String.valueOf(stats[stat]);
                for (int y = strStat.length(), pl = 4; y > 0 & pl > 0; y--, pl--) {
                    int z = Integer.parseInt(strStat.substring(y - 1, y));
                    g.drawImage(records_font, pl * 30 + 360, 85 * stat + 160, pl * 30 + 30 + 360, 85 * stat + 35 + 160, z * 30, 0, z * 30 + 30, 35, null);
                }
                if (statIsMinus) {
                    g.drawImage(records_font, 360, 85 * stat + 175, 385, 85 * stat + 5 + 175, 35, 30, 60, 35, null);
                    stats[stat] = -stats[stat];
                }
            }
            score = stats[3];
        }
    };
    public static final Mess GAME_PAUSE = new Mess() {

        @Override
        public void spase() {
            setMess(null);

        }

        @Override
        public void escape() {
            setMess(null);
            Game.fromGameToMenu();
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(game_pause_message, 85, 235, null);
        }

    };

    public static final Mess INPUT_NAME = new Mess() {
        String[] name = Game.name;

        @Override
        public void spase() {
            setMess(null);
            if ("_".equals(name[0]) && "_".equals(name[1]) && "_".equals(name[2]) && "_".equals(name[3]) && "_".equals(name[4])) {
                name[0] = "П";
                name[1] = "У";
                name[2] = "С";
                name[3] = "Т";
                name[4] = "О";
            }
            Records.add(name[0] + name[1] + name[2] + name[3] + name[4], score);
            Game.inputName(false);
            Build.start(Game.getSave());
            Game.fromGameToRecords();
        }

        @Override
        public void escape() {

        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(input_message, 85, 175, null);
            String[][] validSymbols = new String[][]{
                {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я",},
                {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", },};

            for (int x = 0; x < 5; x++) {
                //Цикл установки имени на карту
                for (int y = 0; y < 5; y++) {
                    //Цикл букв
                    for (int z = 0; z < 33; z++) {
                        //Сравнение символа в имени и сомволом из массива
                        if (name[y].equals(validSymbols[0][z])) {
                            //Установка и присваевание соответсвуещего аргумента
                            g.drawImage(records_font, y * 65 + 155, 280, y * 65 + 210, 330, z * 55, 35, z * 55 + 55, 85, null);
                        }
                    }
                    //То же с цифрами
                    for (int z = 0; z < 10; z++) {
                        if (name[y].equals(validSymbols[1][z])) {
                            g.drawImage(records_font, y * 65 + 170, 290, y * 65 +200, 325, z * 30, 0, z * 30 + 30, 35, null);
                        }
                    }
                }
            }
        }

    };

    public static final Mess WIN = new Mess() {

        @Override
        public void spase() {
            setMess(SCORE);
        }

        @Override
        public void escape() {
            setMess(null);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(win_message, 85, 235, null);
        }

    };
    public static final Mess RESET = new Mess() {

        @Override
        public void spase() {
            Build.start(Game.getSave());
            setMess(GENERATING_DONE);
        }

        @Override
        public void escape() {
            setMess(null);
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(reset_message, 85, 235, null);
        }

    };
    public static final Mess GENERATING_DONE = new Mess() {

        @Override
        public void spase() {
            setMess(null);
        }

        @Override
        public void escape() {
        }

        @Override
        public void paint(Graphics g) {
            g.drawImage(generating_done_message, 85, 235, null);
        }

    };

    public static void paint(Graphics g) {
        if (isThere()) {
            g.drawImage(darkness[9], 0, 0, 830, 680, 0, 0, 5, 5, null);
            currentMess.paint(g);
        }
    }

    public static void spaseAction() {
        if (isThere()) {
            currentMess.spase();
        }
    }

    public static void escapeAction() {
        if (isThere()) {
            currentMess.escape();
        }
    }

    public static void setMess(Mess newMess) {
        currentMess = newMess;
    }

    public static boolean isThere() {
        return currentMess != null;
    }

    private static abstract class Mess {

        public abstract void spase();

        public abstract void escape();

        public abstract void paint(Graphics g);

    }
}
