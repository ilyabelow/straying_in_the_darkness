package game;

import build.Build;
import cells.second_layer.Door;
import cells.second_layer.Torches;
import cells.third_layer.Player;
import cells.first_layer.Way;
import out.picture.Window;
import cells.*;
import game.core.Actions;
import game.play.Bubble;
import game.play.Darkness;
import game.core.Map;
import game.core.Message;
import game.core.Records;
import game.core.Stat;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Random;
import java.util.Scanner;
import javax.swing.Timer;
import out.picture.Textures;

public class Game {

    public final static String[] name = new String[]{"_", "_", "_", "_", "_"};
    private static boolean inputing = false;
    public static boolean statsVisible = false;
    private static boolean canReset = false;
    private static Cell player;
    public static Map m = new Map(10, 10, 3);
    public static boolean nightVision = true;
    private static int map, save = 1, location = 2;
    public static Stat[] stats = new Stat[]{new Stat(1), new Stat(2), new Stat(3), new Stat(4), new Stat(5)};
    private static Window p = new Window();
    public static final Random r = new Random();
    private static String[][] empty = new String[10][10];
    private static Timer reseting = new Timer(500, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            canReset = true;
            reseting.stop();
        }
    });

    public static void main(String[] args) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                empty[row][col] = "_";
            }
        }
        reseting.start();
        Textures.initialization();
        p.addKeyListener(new keHandler());
        loading();
        m.setCell(new Player(6, 5));
        findPlayer();
        player.setArgument(2);
    }

    private static boolean endMapTest(String direction) {
        boolean ret = false;
        if ("right".equals(direction) || direction == null) {
            if (map == 5 || map == 10 || map == 15 || map == 20 || map == 25) {
                for (int row = 0; row < 10; row++) {
                    if ("~".equals(m.getCell(row, 9, 0).getMark())) {
                        ret = true;
                    }
                }
            }
        }
        if ("left".equals(direction) || direction == null) {
            if (map == 1 || map == 6 || map == 11 || map == 16 || map == 21) {
                for (int row = 0; row < 10; row++) {
                    if ("~".equals(m.getCell(row, 0, 0).getMark())) {
                        ret = true;
                    }
                }
            }
        }
        if ("down".equals(direction) || direction == null) {
            if (map == 21 || map == 22 || map == 23 || map == 24 || map == 25) {
                for (int col = 0; col < 10; col++) {
                    if ("~".equals(m.getCell(9, col, 0).getMark())) {
                        ret = true;
                    }
                }
            }
        }
        if ("up".equals(direction) || direction == null) {
            if (map == 1 || map == 2 || map == 3 || map == 4 || map == 5) {
                for (int col = 0; col < 10; col++) {
                    if ("~".equals(m.getCell(0, col, 0).getMark())) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    private static boolean loading() {
        Darkness.turn(false);
        if (location == 1) {
            if (loadStats()) {
                if (m.downloadFromFile("saves/save" + save + "/maps/map" + map + ".txt")) {
                    Darkness.turn(!endMapTest(null));
                    setLastSave(save);
                    statsVisible = true;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            statsVisible = false;
            if (location == 2) {
                //        А 1 	Б 2 	В 3 	Г 4 	Д 5 	Е 6 	Ё 7
                //        Ж 8 	З 9 	И 10 	Й 11 	К 12 	Л 13 	М 14
                //        Н 15 	О 16 	П 17 	Р 18 	С 19 	Т 20 	У 21
                //        Ф 22 	Х 23 	Ц 24 	Ч 25 	Ш 26 	Щ 27 	Ъ 28
                //        Ы 29 	Ь 30 	Э 31 	Ю 32 	Я 33
                m.fromStringToCell(new String[][][]{
                    {
                        {"R2", "R13", "R21", "R8", "R5", "R1", "R32", "R27", "R10", "R11",},
                        {"~", "R3", "R16", "~", "~", "R20", "R30", "R14", "R6", "~",},
                        {"#", "#", "#", "#", "#", "#", "#", "#", "#", "#",},
                        {"#", "r5", "r1", "r13", "r6", "r6", "f11", "~", "~", "~",},
                        {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#",},
                        {"~", "~", "f8", "r10", "r4", "r18", "r1", "r20", "r30", "#",},
                        {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#",},
                        {"#", "~", "r31", "r12", "r19", "r20", "r18", "r1", "f11", "~",},
                        {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#",},
                        {"#", "f9", "r18", "r6", "r12", "r16", "r18", "r5", "r29", "#",},},
                    {
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"^4", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                        {"^4", "_", "_", "_", "_", "_", "_", "_", "_", "^3",},}, empty});
                if (!getLastSave()) {
                    m.setCell(new Door(3, 9));
                } else {
                    m.setCell(new Torches(2, 9));
                }

            }

            if (location == 3) {
                m.fromStringToCell(new String[][][]{{
                    {"#", "R3", "R29", "R2", "R6", "R18", "R10", "R20", "R6", "#"},
                    {"#", "~", "~", "R19", "R13", "R16", "R20", "~", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"~", "~", "f8", "n1", "r19", "r13", "r16", "r20", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"~", "~", "f8", "n2", "r19", "r13", "r16", "r20", "~", "~"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"~", "~", "f8", "n3", "r19", "r13", "r16", "r20", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "#", "#", "#", "#", "#", "#", "#", "#", "#"},},
                {
                    {"^", "_", "_", "_", "_", "_", "_", "_", "_", "^",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"^1", "_", "_", "_", "_", "_", "_", "_", "_", "^1",},}, empty});
            }
            if (location == 4) {
                m.fromStringToCell(new String[][][]{{
                    {"~", "R9", "F10", "~", "~", "#", "#", "~", "#", "#"},
                    {"~", "R1", "~", "~", "~", "#", "~", "~", "~", "#"},
                    {"~", "R15", "~", "~", "~", "#", "~", "r19", "~", "#"},
                    {"~", "R16", "~", "~", "~", "#", "~", "r13", "~", "#"},
                    {"~", "R3", "~", "R10", "~", "#", "~", "r16", "~", "#"},
                    {"~", "R16", "~", "R4", "~", "#", "~", "r20", "~", "#"},
                    {"~", "~", "~", "R18", "~", "#", "~", "~", "~", "#"},
                    {"~", "~", "~", "R1", "~", "#", "~", "~", "~", "#"},
                    {"~", "~", "~", "R20", "~", "#", "~", "~", "~", "#"},
                    {"~", "~", "~", "R30", "F12", "#", "#", "~", "#", "#"},},
                {
                    {"_", "_", "_", "_", "_", "^", "_", "_", "_", "^",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "^", "_", "_", "_", "^1",},}, empty});
                m.setCell(new Way(player.getA(), 9));
                m.setCell(new cells.first_layer.symbols.Number(7, 7, true));
                m.getCell(7, 7, 0).setArgument(save);
            }
            //        А 1 	Б 2 	В 3 	Г 4 	Д 5 	Е 6 	Ё 7
            //        Ж 8 	З 9 	И 10 	Й 11 	К 12 	Л 13 	М 14
            //        Н 15 	О 16 	П 17 	Р 18 	С 19 	Т 20 	У 21
            //        Ф 22 	Х 23 	Ц 24 	Ч 25 	Ш 26 	Щ 27 	Ъ 28
            //        Ы 29 	Ь 30 	Э 31 	Ю 32 	Я 33
            if (location == 5) {
                Darkness.turn(true);
                m.fromStringToCell(new String[][][]{{
                    {"#", "#", "R31", "R12", "R19", "R20", "R18", "R1", "#", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "~", "#", "~", "#", "~", "~", "#", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "#", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "~", "#", "~", "~", "~", "~", "~", "~", "#"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "~", "~", "~", "~", "~", "~", "~", "~", "#"},
                    {"#", "#", "#", "#", "#", "#", "#", "#", "#", "#"},},
                {
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "^1", "_", "^2", "_", "_", "^3", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "^4", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "^15", "_", "_", "_", "05", "[", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},},
                {
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},
                    {"_", "_", "_", "_", "_", "_", "_", "_", "_", "_",},}});
            }
            if (location == 6) {
                m.fromStringToCell(new String[][][]{{
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},
                    {"~", "~", "~", "~", "~", "~", "~", "~", "~", "~"},},
                empty, empty
                });
                Records.load();

            }

            m.generationObjects();
            return true;
        }
    }

    private static boolean loadStats() {
        try {
            Scanner scn = new Scanner(new File("saves/save" + save + "/stats.txt"));
            map = scn.nextInt();
            for (int st = 0; st < 5; st++) {
                stats[st].setCount(scn.nextInt());
            }
            scn.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Отсутствуют или повреждены статы " + save + " сохранения!");
            return false;
        }
    }

    private static boolean saveStats() {
        try {
            Formatter f = new Formatter(new File("saves/save" + save + "/stats.txt"));
            f.format("%d %d %d %d %d %d", map, stats[0].getCount(), stats[1].getCount(), stats[2].getCount(), stats[3].getCount(), stats[4].getCount());
            f.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Отсутствуют статы " + save + " сохранения!");
            return false;
        }
    }

    private static boolean saving(String url) {
        return m.uploadToFile(url) && saveStats();
    }

    private static void trek(String direction) {
        boolean goFromBound = false;
        if (location == 1 && endMapTest(direction)) {
            Message.setMess(Message.WIN);
            goFromBound = true;
        } else {
            if (location == 1) {
                int pMap = map, newA = player.getA(), newB = player.getB();
                if ("up".equals(direction)) {
                    map = map - 5;
                    newA = 9;
                }
                if ("down".equals(direction)) {
                    map = map + 5;
                    newA = 0;
                }
                if ("left".equals(direction)) {
                    map = map - 1;
                    newB = 9;
                }
                if ("right".equals(direction)) {
                    map = map + 1;
                    newB = 0;
                }
                m.removeCell(player.getA(), player.getB(), 2);
                saving("saves/save" + save + "/maps/map" + pMap + ".txt");
                if (!loading()) {
                    m.setCell(player);
                    map = pMap;
                    saveStats();
                } else {
                    goFromBound = true;
                    if (!"way".equals(m.getCell(newA, newB, 2).getName())) {
                        m.setCell(new Way(newA, newB));
                    }
                    m.setCell(new Player(newA, newB));
                    m.uploadToFile("saves/save" + save + "/maps/map" + map + ".txt");
                }
            }
            if ("up".equals(direction) & location == 6) {
                goFromBound = true;
                location = 2;
                loading();
                m.setCell(new Player(9, player.getB()));
            }
            if ("down".equals(direction) & location == 2) {
                goFromBound = true;
                location = 6;
                loading();
                m.setCell(new Player(0, player.getB()));
            }
            if ("down".equals(direction) & location == 4) {
                fromMenuToGame(4);
            }
            if ("up".equals(direction) & location == 4) {
                Message.setMess(Message.RESET);
            }
            if ("left".equals(direction) & location == 3) {
                goFromBound = true;
                location = 4;
                save = (player.getA() - 1) / 2;
                loading();
                m.setCell(new Player(player.getA(), 9));
            }
            if ("left".equals(direction) & location == 2) {
                goFromBound = true;
                location = 3;
                loading();
                m.setCell(new Player(5, 9));
            }
            if ("left".equals(direction) & location == 5) {
                goFromBound = true;
                location = 2;
                loading();
                m.setCell(new Player(7, 9));
            }
            if ("right".equals(direction) && location == 2 && player.getA() == 7) {
                goFromBound = true;
                location = 5;
                loading();
                m.setCell(new Player(7, 0));
            }
            if ("right".equals(direction) && location == 2 && player.getA() == 3) {
                fromMenuToGame(2);
            }
            if ("right".equals(direction) & location == 3) {
                goFromBound = true;
                location = 2;
                loading();
                m.setCell(new Player(5, 0));
            }
            if ("right".equals(direction) & location == 4) {
                goFromBound = true;
                location = 3;
                loading();
                m.setCell(new Player(save * 2 + 1, 0));
            }
            findPlayer();
            Bubble.setCoordinates(player.getA(), player.getB());
            if (goFromBound) {
                stats[1].plusCount(1);
                player.doAction(Actions.getAction("turn", new String[]{direction}));
                player.doAction(Actions.getAction("moveFromBound", new String[]{direction}));
            }
        }
    }

    public static void fromGameToMenu() {
        location = 2;
        loading();
        m.setCell(new Player(5, 0));
        findPlayer();
        player.doAction(Actions.TURN_RIGHT);
        player.doAction(Actions.MOVE_RIGHT_FROM_BOUND);
    }

    public static void fromGameToRecords() {
        location = 6;
        loading();
        m.setCell(new Player(0, 4));
        findPlayer();
        player.doAction(Actions.MOVE_DOWN_FROM_BOUND);

    }

    public static void fromMenuToGame(int oldLocation) {
        location = 1;
        if (!loading()) {
            location = oldLocation;
        }
    }

    private static void go(String way) {
        if ("up".equals(way)) {
            if (player.getA() == 0) {
                trek("up");
            } else {
                if (player.canAction(Actions.THIS_MOVE_UP)) {
                    player.doAction(Actions.THIS_MOVE_UP);
                    stats[1].plusCount(1);
                }
            }
        }
        if ("down".equals(way)) {
            if (player.getA() == 9) {
                trek("down");
            } else {
                if (player.canAction(Actions.THIS_MOVE_DOWN)) {
                    player.doAction(Actions.THIS_MOVE_DOWN);
                    stats[1].plusCount(1);
                }
            }
        }
        if ("left".equals(way)) {
            if (player.getB() == 0) {
                trek("left");
            } else {
                if (player.canAction(Actions.THIS_MOVE_LEFT)) {
                    player.doAction(Actions.THIS_MOVE_LEFT);
                    stats[1].plusCount(1);
                }
            }
        }
        if ("right".equals(way)) {
            if (player.getB() == 9) {
                trek("right");
            } else {
                if (player.canAction(Actions.THIS_MOVE_RIGHT)) {
                    player.doAction(Actions.THIS_MOVE_RIGHT);
                    stats[1].plusCount(1);
                }
            }
        }
    }

    private static boolean findPlayer() {
        for (int row = 0; row < m.length; row++) {
            for (int col = 0; col < m.width; col++) {
                if ("player".equals(m.getCell(row, col, 2).getName())) {
                    player = m.getCell(row, col, 2);
                    return true;
                }
            }
        }
        m.setCell(new Way(4, 4));
        m.removeCell(4, 4, 1);
        m.setCell(new Player(4, 4));
        player = m.getCell(4, 4, 2);
        return false;
    }

    public static int getSave() {
        return save;
    }

    public static String getDirection(int newA, int newB, int oldA, int oldB) {
        String direction = null;
        if (oldA > newA & oldB == newB) {
            direction = "up";
        }
        if (oldA < newA & oldB == newB) {
            direction = "down";
        }
        if (oldA == newA & oldB > newB) {
            direction = "left";
        }
        if (oldA == newA & oldB < newB) {
            direction = "right";
        }
        return direction;
    }

    public static String getDirection(int arg) {
        if (arg == 1) {
            return "up";
        }
        if (arg == 2) {
            return "down";
        }
        if (arg == 3) {
            return "left";
        }
        if (arg == 4) {
            return "right";
        }
        return null;
    }

    public static int getNewA(String direction, int oldA) {
        if ("up".equals(direction)) {
            oldA--;
        }
        if ("down".equals(direction)) {
            oldA++;
        }
        return oldA;

    }

    public static int getNewB(String direction, int oldB) {
        if ("left".equals(direction)) {
            oldB--;
        }
        if ("right".equals(direction)) {
            oldB++;
        }
        return oldB;

    }

    public static int getOldA(String direction, int newA) {
        if ("up".equals(direction)) {
            newA++;
        }
        if ("down".equals(direction)) {
            newA--;
        }
        return newA;

    }

    public static int getOldB(String direction, int newB) {
        if ("left".equals(direction)) {
            newB++;
        }
        if ("right".equals(direction)) {
            newB--;
        }
        return newB;

    }

    public static void outMap() {
        for (int lay = 0; lay < 3; lay++) {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    System.out.print(m.getCell(row, col, lay).getMark() + m.getCell(row, col, lay).getArgument() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }

    }

    private static boolean getLastSave() {
        try {
            Scanner scn = new Scanner(new File("resources//last_game.txt"));
            save = scn.nextInt();
            return save != 0;
        } catch (FileNotFoundException ex) {
            System.out.println("Отсутствует файл last_game.txt!");
            return false;
        }

    }

    public static void setLastSave(int save) {
        try {
            Formatter f = new Formatter("resources//last_game.txt");
            f.format("%d", save);
            f.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Отсутствует файл last_game.txt!");

        }
    }

    public static void inputName(boolean turn) {
        inputing = turn;
        for (int c = 0; c < 5; c++) {
            name[c] = "_";
        }
    }

    private static void addSymbol(String symbol) {
        if ("_".equals(symbol)) {
            if (!"_".equals(name[4])) {
                name[4] = "_";
            } else {
                for (int c = 0; c < 4; c++) {
                    if (!"_".equals(name[c]) & "_".equals(name[c + 1])) {
                        name[c] = "_";
                        c = 5;
                    }
                }
            }
        } else {
            if ("_".equals(name[0])) {
                name[0] = symbol;
            } else {
                for (int c = 1; c < 5; c++) {
                    if ("_".equals(name[c]) & !"_".equals(name[c - 1])) {
                        name[c] = symbol;
                        c = 5;
                    }
                }
            }
        }
    }

    public static class keHandler implements KeyListener {

        public void keyTyped(KeyEvent ke) {
        }

        public void keyPressed(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_R && canReset && !Message.isThere()) {
                loading();
                canReset = false;
                reseting.restart();
                if (location != 1) {
                    m.setCell(new Player(player.getA(), player.getB()));
                } else {
                    stats[2].plusCount(1);
                    saveStats();
                }
                findPlayer();
                Bubble.setCoordinates(player.getA(), player.getB());
            }

            if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (!Message.isThere()) {
                    if (location != 1) {
                        Message.setMess(Message.MENU_PAUSE);
                    } else {
                        Message.setMess(Message.GAME_PAUSE);
                    }
                } else {
                    Message.escapeAction();
                }
            }

            if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                if (!Message.isThere() && location == 1 | location == 5) {
                    if (player.getArgument() == 1) {
                        player.doAction(Actions.ACTION_UP);
                    }
                    if (player.getArgument() == 2) {
                        player.doAction(Actions.ACTION_DOWN);
                    }
                    if (player.getArgument() == 3) {
                        player.doAction(Actions.ACTION_LEFT);
                    }
                    if (player.getArgument() == 4) {
                        player.doAction(Actions.ACTION_RIGHT);
                    }
                } else {
                    Message.spaseAction();
                }
            }
            if (!Message.isThere()) {
                if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    player.doAction(Actions.TURN_UP);
                    if (player.canAction(Actions.THIS_MOVE_AT_ALL)) {
                        go("up");
                    }
                }
                if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.doAction(Actions.TURN_DOWN);
                    if (player.canAction(Actions.THIS_MOVE_AT_ALL)) {
                        go("down");
                    }
                }
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.doAction(Actions.TURN_LEFT);
                    if (player.canAction(Actions.THIS_MOVE_AT_ALL)) {
                        go("left");
                    }
                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.doAction(Actions.TURN_RIGHT);
                if (player.canAction(Actions.THIS_MOVE_AT_ALL)) {
                    go("right");
                }
            }
            if (inputing) {
                if (ke.getKeyCode() == KeyEvent.VK_F) {
                    addSymbol("А");
                }
                if (ke.getKeyCode() == KeyEvent.VK_COMMA) {
                    addSymbol("Б");
                }
                if (ke.getKeyCode() == KeyEvent.VK_D) {
                    addSymbol("В");
                }
                if (ke.getKeyCode() == KeyEvent.VK_U) {
                    addSymbol("Г");
                }
                if (ke.getKeyCode() == KeyEvent.VK_L) {
                    addSymbol("Д");
                }
                if (ke.getKeyCode() == KeyEvent.VK_T) {
                    addSymbol("Е");
                }
                if (ke.getKeyCode() == KeyEvent.VK_BACK_QUOTE) {
                    addSymbol("Ё");
                }
                if (ke.getKeyCode() == KeyEvent.VK_SEMICOLON) {
                    addSymbol("Ж");
                }
                if (ke.getKeyCode() == KeyEvent.VK_P) {
                    addSymbol("З");
                }
                if (ke.getKeyCode() == KeyEvent.VK_B) {
                    addSymbol("И");
                }
                if (ke.getKeyCode() == KeyEvent.VK_Q) {
                    addSymbol("Й");
                }
                if (ke.getKeyCode() == KeyEvent.VK_R) {
                    addSymbol("К");
                }
                if (ke.getKeyCode() == KeyEvent.VK_K) {
                    addSymbol("Л");
                }
                if (ke.getKeyCode() == KeyEvent.VK_V) {
                    addSymbol("М");
                }
                if (ke.getKeyCode() == KeyEvent.VK_Y) {
                    addSymbol("Н");
                }
                if (ke.getKeyCode() == KeyEvent.VK_J) {
                    addSymbol("О");
                }
                if (ke.getKeyCode() == KeyEvent.VK_G) {
                    addSymbol("П");
                }
                if (ke.getKeyCode() == KeyEvent.VK_H) {
                    addSymbol("Р");
                }
                if (ke.getKeyCode() == KeyEvent.VK_C) {
                    addSymbol("С");
                }
                if (ke.getKeyCode() == KeyEvent.VK_N) {
                    addSymbol("Т");
                }
                if (ke.getKeyCode() == KeyEvent.VK_E) {
                    addSymbol("У");
                }
                if (ke.getKeyCode() == KeyEvent.VK_A) {
                    addSymbol("Ф");
                }
                if (ke.getKeyCode() == KeyEvent.VK_OPEN_BRACKET) {
                    addSymbol("Х");
                }
                if (ke.getKeyCode() == KeyEvent.VK_W) {
                    addSymbol("Ц");
                }
                if (ke.getKeyCode() == KeyEvent.VK_X) {
                    addSymbol("Ч");
                }
                if (ke.getKeyCode() == KeyEvent.VK_I) {
                    addSymbol("Ш");
                }
                if (ke.getKeyCode() == KeyEvent.VK_O) {
                    addSymbol("Щ");
                }
                if (ke.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET) {
                    addSymbol("Ъ");
                }
                if (ke.getKeyCode() == KeyEvent.VK_S) {
                    addSymbol("Ы");
                }
                if (ke.getKeyCode() == KeyEvent.VK_M) {
                    addSymbol("Ь");
                }
                if (ke.getKeyCode() == KeyEvent.VK_QUOTE) {
                    addSymbol("Э");
                }
                if (ke.getKeyCode() == KeyEvent.VK_PERIOD) {
                    addSymbol("Ю");
                }
                if (ke.getKeyCode() == KeyEvent.VK_Z) {
                    addSymbol("Я");
                }
                if (ke.getKeyCode() == KeyEvent.VK_1) {
                    addSymbol("1");
                }
                if (ke.getKeyCode() == KeyEvent.VK_2) {
                    addSymbol("2");
                }
                if (ke.getKeyCode() == KeyEvent.VK_3) {
                    addSymbol("3");
                }
                if (ke.getKeyCode() == KeyEvent.VK_4) {
                    addSymbol("4");
                }
                if (ke.getKeyCode() == KeyEvent.VK_5) {
                    addSymbol("5");
                }
                if (ke.getKeyCode() == KeyEvent.VK_6) {
                    addSymbol("6");
                }
                if (ke.getKeyCode() == KeyEvent.VK_7) {
                    addSymbol("7");
                }
                if (ke.getKeyCode() == KeyEvent.VK_8) {
                    addSymbol("8");
                }
                if (ke.getKeyCode() == KeyEvent.VK_9) {
                    addSymbol("9");
                }
                if (ke.getKeyCode() == KeyEvent.VK_0) {
                    addSymbol("0");
                }
                if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    addSymbol("_");
                }
            }
        }

        public void keyReleased(KeyEvent ke) {

        }
    }

}
