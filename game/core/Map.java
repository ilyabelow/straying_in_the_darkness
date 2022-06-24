package game.core;

import cells.second_layer.Door;
import cells.second_layer.Torches;
import cells.third_layer.Player;
import cells.third_layer.Ghost;
import cells.second_layer.Coin;
import cells.second_layer.Chest;
import cells.first_layer.Sign;
import cells.first_layer.Way;
import cells.first_layer.Wall;
import cells.first_layer.symbols.Pointer;
import cells.first_layer.symbols.Letter;
import cells.*;
import static game.Game.r;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Map {

    public final int length, width, height;
    private Cell[][][] map = null;

    public Map(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
        map = new Cell[height][length][width];
        for (int lay = 0; lay < height; lay++) {
            for (int row = 0; row < length; row++) {
                for (int col = 0; col < width; col++) {
                    removeCell(row, col, lay);
                }
            }
        }
    }

    public void setCell(Cell setting) {
        if (setting.getValidLayer() != -1) {
            if (setting.getValidLayer() == 0 || setting.getValidLayer() == 3) {
                map[setting.getValidLayer()][setting.getA()][setting.getB()] = setting;
            }
            if (setting.getValidLayer() == 1) {
                for (int f = 0; f < setting.getValidCellsUnder()[0].length; f++) {
                    if (setting.getValidCellsUnder()[0][f].equals(getCell(setting.getA(), setting.getB(), 0).getName()) || setting.getValidCellsUnder()[0][f].equals(getCell(setting.getA(), setting.getB(), 0).getMark()) || setting.getValidCellsUnder()[0][f].equals("all")) {
                        map[1][setting.getA()][setting.getB()] = setting;
                    }
                }
            }
            if (setting.getValidLayer() == 2) {
                boolean a1 = false, a2 = false;
                for (int g = 0; g < setting.getValidCellsUnder().length; g++) {
                    for (int f = 0; f < setting.getValidCellsUnder()[g].length; f++) {
                        if (setting.getValidCellsUnder()[g][f].equals(getCell(setting.getA(), setting.getB(), g).getName()) || setting.getValidCellsUnder()[g][f].equals(getCell(setting.getA(), setting.getB(), g).getMark()) || setting.getValidCellsUnder()[g][f].equals("all")) {
                            if (g == 0) {
                                a1 = true;
                            }
                            if (g == 1) {
                                a2 = true;
                            }
                        }
                    }
                }
                if (a2 & a1) {
                    map[2][setting.getA()][setting.getB()] = setting;
                }
            }
        }
    }

    public void removeCell(int A, int B, int C) {
        map[C][A][B] = new Empty(A, B);
    }

    public Cell getCell(int A, int B, int C) {
        return map[C][A][B];
    }

    public void fromStringToCell(String[][][] from) {
        for (int lay = 0; lay < 3; lay++) {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    Cell[] validObjects = new Cell[]{
                        new Empty(row, col),
                        new Way(row, col), new Wall(row, col), new Player(row, col),
                        new Coin(row, col), new Ghost(row, col),
                        new Letter(row, col, "russian", false), new Letter(row, col, "russian", true),
                        new Pointer(row, col, false), new Pointer(row, col, true),
                        new cells.first_layer.symbols.Number(row, col, false), new cells.first_layer.symbols.Number(row, col, true),
                        new Chest(row, col), new Torches(row, col), new Door(row, col), new Sign(row, col)};
                    Cell set = null;
                    for (int x = 0; x < validObjects.length; x++) {
                        if (validObjects[x].getMark().equals(from[lay][row][col].substring(0, 1))) {
                            set = validObjects[x];
                        }
                    }
                    if (set == null) {
                        set = new Empty(row, col);
                    }
                    if ("empty".equals(set.getName())) {
                        removeCell(row, col, lay);
                    } else {
                        setCell(set);
                    }
                    if (from[lay][row][col].length() > 1) {
                        getCell(row, col, lay).setArgument(Integer.parseInt(from[lay][row][col].substring(1)));
                    }

                }
            }
        }
    }

    public String[][][] fromCellToString() {
        String[][][] returning = new String[3][10][10];
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                for (int lay = 0; lay < 3; lay++) {
                    returning[lay][row][col] = getCell(row, col, lay).getMark() + getCell(row, col, lay).getArgument();
                }
            }
        }
        return returning;
    }

    public boolean downloadFromFile(String url) {
        String[][][] file = new String[3][10][10];
        Scanner scn;
        try {
            scn = new Scanner(new File(url));
        } catch (FileNotFoundException ex) {
            System.out.println("Потерян файл " + url + "!");
            return false;
        }
        for (int lay = 0; lay < 3; lay++) {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    try {
                        file[lay][row][col] = scn.next();
                    } catch (NoSuchElementException e) {
                        System.out.println("Повреждён файл " + url + "!");
                        return false;
                    }
                }
            }
        }
        scn.close();
        fromStringToCell(file);
        return true;
    }

    public boolean uploadToFile(String url) {
        String futureFile = "";
        String[][][] futureFutureFile = fromCellToString();
        for (int lay = 0; lay < height; lay++) {
            for (int row = 0; row < length; row++) {
                for (int col = 0; col < width; col++) {
                    futureFile = futureFile + futureFutureFile[lay][row][col] + " ";
                }
                futureFile = futureFile + "\n";
            }
            futureFile = futureFile + "\n";
        }
        try {
            Formatter file = new Formatter(new File(url));
            file.format("%s", futureFile);
            file.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Потерян файл " + url + "!");
            return false;
        }

    }

    public void generationObjects() {
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < width; col++) {
                if ("wall".equals(getCell(row, col, 0).getName())) {
                    getCell(row, col, 0).setArgument(r.nextInt(5) + 1);
                }
                if ("way".equals(getCell(row, col, 0).getName())) {
                    if (r.nextInt(3) == 0) {
                        getCell(row, col, 0).setArgument(r.nextInt(4) + 2);
                    }else{
                        getCell(row, col, 0).setArgument(1);
                    }
                }
            }
        }
        for (int row = 1; row < 9; row++) {
            for (int col = 1; col < 9; col++) {
                if ("empty".equals(getCell(row, col, 1).getName())) {
                    if (new Random().nextInt(3) == 0) {
                        setCell(new Coin(row, col));
                        getCell(row, col, 1).setArgument(r.nextInt(5) + 1);
                    }
                    if (new Random().nextInt(15) == 0) {
                        setCell(new Chest(row, col));
                    }
                }
            }
        }
    }
}
