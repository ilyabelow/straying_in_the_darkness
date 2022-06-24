package build;

import cells.first_layer.Way;
import game.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Random;

public class Build {

    private static String[][][] level = new String[3][50][50];
    private static String[][][][] maps = new String[25][3][10][10];
    private static int minerA, minerB, endA, endB, startA, startB, startMap, endMap;
    private static String direction;
    private static boolean Aup = true, Adown = true, Aleft = true, Aright = true, mainWay = true;
    private static Random r = new Random();
    private static Formatter f;
    private static int exitDirection = r.nextInt(4) + 1;

    public static void start(int save) {
        for (int row = 0; row < level[0].length; row++) {
            for (int col = 0; col < level[0][row].length; col++) {
                level[0][row][col] = "#";
                level[1][row][col] = "_";
                level[2][row][col] = "_";
            }
        }

        if (exitDirection == 2) {
            exitDirection = r.nextInt(4) + 1;
        }
        if (exitDirection == 1) {
            while (startB == 0 || startB == 9 || startB == 10 || startB == 19 || startB == 20 || startB == 29 || startB == 30 || startB == 39 || startB == 40 || startB == 49) {
                startB = r.nextInt(48) + 1;
            }
            startA = r.nextInt(8) + 41;
            while (endB == 0 || endB == 9 || endB == 10 || endB == 19 || endB == 20 || endB == 29 || endB == 30 || endB == 39 || endB == 40 || endB == 49) {
                endB = r.nextInt(48) + 1;
            }
            endA = 0;
        }

        if (exitDirection == 2) {
            while (startB == 0 || startB == 9 || startB == 10 || startB == 19 || startB == 20 || startB == 29 || startB == 30 || startB == 39 || startB == 40 || startB == 49) {
                startB = r.nextInt(48) + 1;
            }
            startA = r.nextInt(8) + 1;
            while (endB == 0 || endB == 9 || endB == 10 || endB == 19 || endB == 20 || endB == 29 || endB == 30 || endB == 39 || endB == 40 || endB == 49) {
                endB = r.nextInt(48) + 1;
            }
            endA = 49;
        }
        if (exitDirection == 3) {
            while (startA == 0 || startA == 9 || startA == 10 || startA == 19 || startA == 20 || startA == 29 || startA == 30 || startA == 39 || startA == 40 || startA == 49) {
                startA = r.nextInt(48) + 1;
            }
            startB = r.nextInt(8) + 41;
            while (endA == 0 || endA == 9 || endA == 10 || endA == 19 || endA == 20 || endA == 29 || endA == 30 || endA == 39 || endA == 40 || endA == 49) {
                endA = r.nextInt(48) + 1;
            }
            endB = 0;
        }
        if (exitDirection == 4) {
            while (startA == 0 || startA == 9 || startA == 10 || startA == 19 || startA == 20 || startA == 29 || startA == 30 || startA == 39 || startA == 40 || startA == 49) {
                startA = r.nextInt(48) + 1;
            }
            startB = r.nextInt(8) + 1;
            while (endA == 0 || endA == 9 || endA == 10 || endA == 19 || endA == 20 || endA == 29 || endA == 30 || endA == 39 || endA == 40 || endA == 49) {
                endA = r.nextInt(48) + 1;
            }
            endB = 49;
        }
        minerA = startA;
        minerB = startB;
        mainWay = true;
        minerOperator();
        level[0][endA][endB] = "~";
        level[0][startA][startB] = "~";
        level[2][startA][startB] = "A";
        findStartMap();
        findEndMap();
        edgeTest();
        generDoors();
        generSigns();
        slicer();
        generGhosts();
        generObjects();
        filer(save);
        outMaps();
        Game.setLastSave(0);
    }

    private static void outLevel() {
        System.out.println("================================================NEW LEVEL=============================================");
        for (int lay = 0; lay < level.length; lay++) {
            for (int row = 0; row < level[lay].length; row++) {
                for (int col = 0; col < level[lay][row].length; col++) {
                    System.out.print(level[lay][row][col] + " ");
                    if (col == 9 || col == 19 || col == 29 || col == 39) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
                if (row == 9 || row == 19 || row == 29 || row == 39) {
                    System.out.println();
                }
            }
            System.out.println("======================================================================================================");
        }
    }

    private static void outMaps() {
        System.out.println("================================================NEW LEVEL=============================================");
        for (int lay = 0; lay < 3; lay++) {
            for (int level = 0; level < 5; level++) {
                for (int row = 0; row < 10; row++) {
                    for (int map = level * 5; map < level * 5 + 5; map++) {
                        for (int col = 0; col < 10; col++) {
                            System.out.print(maps[map][lay][row][col] + " ");
                        }
                        System.out.print("  ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
            System.out.println("======================================================================================================");
        }
    }

    private static void generDoors() {

        for (int row = 1; row < 49; row++) {
            for (int col = 1; col < 49; col++) {
                if ("~".equals(level[0][row][col]) & r.nextInt(10) == 0 & row != 9 & row != 10 & row != 19 & row != 20 & row != 29 & row != 30 & row != 39 & row != 40 & col != 9 & col != 10 & col != 19 & col != 20 & col != 29 & col != 30 & col != 39 & col != 40) {
                    int doorA = row;
                    int doorB = col;
                    level[1][doorA][doorB] = "[";
                    int waysAround = 0;
                    if ("~".equals(level[0][doorA - 1][doorB])) {
                        waysAround++;
                        level[1][doorA - 1][doorB] = String.valueOf(waysAround) + "!";
                    }
                    if ("~".equals(level[0][doorA][doorB + 1])) {
                        waysAround++;
                        level[1][doorA][doorB + 1] = String.valueOf(waysAround) + "!";
                    }
                    if ("~".equals(level[0][doorA + 1][doorB])) {
                        waysAround++;
                        level[1][doorA + 1][doorB] = String.valueOf(waysAround) + "!";
                    }
                    if ("~".equals(level[0][doorA][doorB - 1])) {
                        waysAround++;
                        level[1][doorA][doorB - 1] = String.valueOf(waysAround) + "!";
                    }
                    if (!testDoor(waysAround)) {
                        level[1][doorA][doorB] = "_";
                    }
                    clear();
                }
            }
        }
    }

    private static boolean testDoor(int waysAround) {
        if (waysAround < 2) {
            return false;
        } else {
            boolean one_with_two = false, one_with_three = false, one_with_four = false, two_with_three = false, two_with_four = false, three_with_four = false;
            int crashs = 0;
            for (int x = 1; x < 7; x++) {
                for (int row = 0; row < 50; row++) {
                    for (int col = 0; col < 50; col++) {
                        try {
                            if ("1!".equals(level[1][row][col])) {
                                if ("~".equals(level[0][row - 1][col]) & "_".equals(level[1][row - 1][col])) {
                                    level[1][row - 1][col] = "1/";
                                }

                                if ("~".equals(level[0][row][col + 1]) & "_".equals(level[1][row][col + 1])) {
                                    level[1][row][col + 1] = "1/";
                                }
                                if ("~".equals(level[0][row + 1][col]) & "_".equals(level[1][row + 1][col])) {
                                    level[1][row + 1][col] = "1/";
                                }
                                if ("~".equals(level[0][row][col - 1]) & "_".equals(level[1][row][col - 1])) {
                                    level[1][row][col - 1] = "1/";
                                }
                                if ("2".equals(level[1][row][col - 1].substring(0, 1))
                                        | "2".equals(level[1][row][col + 1].substring(0, 1))
                                        | "2".equals(level[1][row - 1][col].substring(0, 1))
                                        | "2".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_two == false) {
                                        one_with_two = true;
                                        crashs++;
                                    }

                                }
                                if ("3".equals(level[1][row][col - 1].substring(0, 1))
                                        | "3".equals(level[1][row][col + 1].substring(0, 1))
                                        | "3".equals(level[1][row - 1][col].substring(0, 1))
                                        | "3".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_three == false) {
                                        one_with_three = true;
                                        crashs++;
                                    }

                                }
                                if ("4".equals(level[1][row][col - 1].substring(0, 1))
                                        | "4".equals(level[1][row][col + 1].substring(0, 1))
                                        | "4".equals(level[1][row - 1][col].substring(0, 1))
                                        | "4".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_four == false) {
                                        one_with_four = true;
                                        crashs++;
                                    }

                                }
                                level[1][row][col] = "1x";
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ("2!".equals(level[1][row][col])) {
                                if ("~".equals(level[0][row - 1][col]) & "_".equals(level[1][row - 1][col])) {
                                    level[1][row - 1][col] = "2/";
                                }

                                if ("~".equals(level[0][row][col + 1]) & "_".equals(level[1][row][col + 1])) {
                                    level[1][row][col + 1] = "2/";
                                }
                                if ("~".equals(level[0][row + 1][col]) & "_".equals(level[1][row + 1][col])) {
                                    level[1][row + 1][col] = "2/";
                                }
                                if ("~".equals(level[0][row][col - 1]) & "_".equals(level[1][row][col - 1])) {
                                    level[1][row][col - 1] = "2/";
                                }
                                if ("1".equals(level[1][row][col - 1].substring(0, 1))
                                        | "1".equals(level[1][row][col + 1].substring(0, 1))
                                        | "1".equals(level[1][row - 1][col].substring(0, 1))
                                        | "1".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_two == false) {
                                        one_with_two = true;
                                        crashs++;
                                    }

                                }
                                if ("3".equals(level[1][row][col - 1].substring(0, 1))
                                        | "3".equals(level[1][row][col + 1].substring(0, 1))
                                        | "3".equals(level[1][row - 1][col].substring(0, 1))
                                        | "3".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (two_with_three == false) {
                                        two_with_three = true;
                                        crashs++;
                                    }

                                }
                                if ("4".equals(level[1][row][col - 1].substring(0, 1))
                                        | "4".equals(level[1][row][col + 1].substring(0, 1))
                                        | "4".equals(level[1][row - 1][col].substring(0, 1))
                                        | "4".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (two_with_four == false) {
                                        two_with_four = true;
                                        crashs++;
                                    }

                                }
                                level[1][row][col] = "2x";
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ("3!".equals(level[1][row][col])) {
                                if ("~".equals(level[0][row - 1][col]) & "_".equals(level[1][row - 1][col])) {
                                    level[1][row - 1][col] = "3/";
                                }

                                if ("~".equals(level[0][row][col + 1]) & "_".equals(level[1][row][col + 1])) {
                                    level[1][row][col + 1] = "3/";
                                }
                                if ("~".equals(level[0][row + 1][col]) & "_".equals(level[1][row + 1][col])) {
                                    level[1][row + 1][col] = "3/";
                                }
                                if ("~".equals(level[0][row][col - 1]) & "_".equals(level[1][row][col - 1])) {
                                    level[1][row][col - 1] = "3/";
                                }
                                if ("1".equals(level[1][row][col - 1].substring(0, 1))
                                        | "1".equals(level[1][row][col + 1].substring(0, 1))
                                        | "1".equals(level[1][row - 1][col].substring(0, 1))
                                        | "1".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_three == false) {
                                        one_with_three = true;
                                        crashs++;
                                    }

                                }
                                if ("2".equals(level[1][row][col - 1].substring(0, 1))
                                        | "2".equals(level[1][row][col + 1].substring(0, 1))
                                        | "2".equals(level[1][row - 1][col].substring(0, 1))
                                        | "2".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (two_with_three == false) {
                                        two_with_three = true;
                                        crashs++;
                                    }

                                }
                                if ("4".equals(level[1][row][col - 1].substring(0, 1))
                                        | "4".equals(level[1][row][col + 1].substring(0, 1))
                                        | "4".equals(level[1][row - 1][col].substring(0, 1))
                                        | "4".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (three_with_four == false) {
                                        three_with_four = true;
                                        crashs++;
                                    }

                                }
                                level[1][row][col] = "3x";
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if ("4!".equals(level[1][row][col])) {
                                if ("~".equals(level[0][row - 1][col]) & "_".equals(level[1][row - 1][col])) {
                                    level[1][row - 1][col] = "4/";
                                }

                                if ("~".equals(level[0][row][col + 1]) & "_".equals(level[1][row][col + 1])) {
                                    level[1][row][col + 1] = "4/";
                                }
                                if ("~".equals(level[0][row + 1][col]) & "_".equals(level[1][row + 1][col])) {
                                    level[1][row + 1][col] = "4/";
                                }
                                if ("~".equals(level[0][row][col - 1]) & "_".equals(level[1][row][col - 1])) {
                                    level[1][row][col - 1] = "4/";
                                }
                                if ("1".equals(level[1][row][col - 1].substring(0, 1))
                                        | "1".equals(level[1][row][col + 1].substring(0, 1))
                                        | "1".equals(level[1][row - 1][col].substring(0, 1))
                                        | "1".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (one_with_four == false) {
                                        one_with_four = true;
                                        crashs++;
                                    }

                                }
                                if ("2".equals(level[1][row][col - 1].substring(0, 1))
                                        | "2".equals(level[1][row][col + 1].substring(0, 1))
                                        | "2".equals(level[1][row - 1][col].substring(0, 1))
                                        | "2".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (two_with_four == false) {
                                        two_with_four = true;
                                        crashs++;
                                    }

                                }
                                if ("3".equals(level[1][row][col - 1].substring(0, 1))
                                        | "3".equals(level[1][row][col + 1].substring(0, 1))
                                        | "3".equals(level[1][row - 1][col].substring(0, 1))
                                        | "3".equals(level[1][row + 1][col].substring(0, 1))) {
                                    if (three_with_four == false) {
                                        three_with_four = true;
                                        crashs++;
                                    }

                                }
                                level[1][row][col] = "4x";
                            }
                        } catch (Exception e) {
                        }

                    }
                }
                for (int row = 0; row < 50; row++) {
                    for (int col = 0; col < 50; col++) {
                        if ("1/".equals(level[1][row][col])) {
                            level[1][row][col] = "1!";
                        }
                        if ("2/".equals(level[1][row][col])) {
                            level[1][row][col] = "2!";
                        }
                        if ("3/".equals(level[1][row][col])) {
                            level[1][row][col] = "3!";
                        }
                        if ("4/".equals(level[1][row][col])) {
                            level[1][row][col] = "4!";
                        }
                    }
                }

                if (crashs >= 1 & waysAround == 2 || crashs >= 2 & waysAround == 3 || crashs >= 3 & waysAround == 4) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void clear() {
        for (int row = 0; row < 50; row++) {
            for (int col = 0; col < 50; col++) {
                if (!"[".equals(level[1][row][col])) {
                    level[1][row][col] = "_";
                }
            }
        }
    }

    private static void generGhosts() {
        for (int map = 0; map < 25; map++) {
            int ghostsOnLevel = 0;
            for (int row = 1; row < 9; row++) {
                for (int col = 1; col < 9; col++) {
                    if ("~".equals(maps[map][0][row][col]) && ghostsOnLevel < 3 && r.nextInt(2) == 0) {
                        if (map != endMap & map != startMap) {
                            int waysAround = 0;
                            if ("~".equals(maps[map][0][row - 1][col]) && row != 1) {
                                waysAround++;
                            }
                            if ("~".equals(maps[map][0][row + 1][col]) && row != 8) {
                                waysAround++;
                            }
                            if ("~".equals(maps[map][0][row][col - 1]) && col != 1) {
                                waysAround++;
                            }
                            if ("~".equals(maps[map][0][row][col + 1]) && col != 8) {
                                waysAround++;
                            }
                            if (waysAround >= 3) {
                                maps[map][2][row][col] = "$" + (r.nextInt(4) + 1);
                                ghostsOnLevel++;
                            }
                        }
                    }
                }
            }

        }
    }

    private static void generSigns() {
        for (int row = 1; row < 49; row++) {
            for (int col = 1; col < 49; col++) {
                if (r.nextInt(10) == 0 & "#".equals(level[0][row][col])) {
                    if ("~".equals(level[0][row - 1][col]) || "~".equals(level[0][row + 1][col]) || "~".equals(level[0][row][col - 1]) || "~".equals(level[0][row][col + 1])) {
                        if (r.nextInt(2) == 0) {
                            if (row > endA & col == endB) {
                                level[0][row][col] = "/1";
                            }
                            if (row < endA & col == endB) {
                                level[0][row][col] = "/2";
                            }
                            if (row == endA & col > endB) {
                                level[0][row][col] = "/3";
                            }
                            if (row == endA & col < endB) {
                                level[0][row][col] = "/4";
                            }
                            if (row > endA & col > endB) {
                                if (row - endA > 10 || col - endB > 10) {
                                    int first = 0, second = 0;
                                    while (first == second) {
                                        first = r.nextInt(row - endA);
                                        second = r.nextInt(col - endB);
                                    }
                                    if (first > second) {
                                        level[0][row][col] = "/1";
                                    } else {
                                        level[0][row][col] = "/3";
                                    }

                                } else {
                                    level[0][row][col] = "/5";
                                }
                            }
                            if (row < endA & col > endB) {
                                if (endA - row > 10 || col - endB > 10) {
                                    level[0][row][col] = "/5";
                                    int first = 0, second = 0;
                                    while (first == second) {
                                        first = r.nextInt(endA - row);
                                        second = r.nextInt(col - endB);
                                    }
                                    if (first > second) {
                                        level[0][row][col] = "/2";
                                    } else {
                                        level[0][row][col] = "/3";
                                    }
                                } else {
                                    level[0][row][col] = "/5";
                                }
                            }
                            if (row > endA & col < endB) {
                                if (row - endA > 10 || endB - col > 10) {
                                    int first = 0, second = 0;
                                    while (first == second) {
                                        first = r.nextInt(row - endA);
                                        second = r.nextInt(endB - col);
                                    }
                                    if (first > second) {
                                        level[0][row][col] = "/1";
                                    } else {
                                        level[0][row][col] = "/4";
                                    }

                                } else {
                                    level[0][row][col] = "/5";
                                }
                            }
                            if (row < endA & col < endB) {
                                if (endA - row > 10 || endB - col > 10) {
                                    int first = 0, second = 0;
                                    while (first == second) {
                                        first = r.nextInt(endA - row);
                                        second = r.nextInt(endB - col);
                                    }
                                    if (first > second) {
                                        level[0][row][col] = "/2";
                                    } else {
                                        level[0][row][col] = "/4";
                                    }
                                } else {
                                    level[0][row][col] = "/5";
                                }
                            }
                        } else {
                            level[0][row][col] = "/" + (r.nextInt(12) + 6);
                        }
                    }
                }
            }
        }
    }

    private static void generObjects() {
        for (int map = 0; map < 25; map++) {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if ("~".equals(maps[map][0][row][col]) && row != 0 && row != 9 && col != 0 && col != 9) {
                        if (r.nextInt(3) == 0) {
                            maps[map][1][row][col] = "0" + (r.nextInt(4) + 1);
                        }
                        if (r.nextInt(45) == 0) {
                            maps[map][1][row][col] = "05";
                        }
                        if (r.nextInt(20) == 0) {
                            maps[map][1][row][col] = "+";
                        }
                    }
                    if ("#".equals(maps[map][0][row][col]) && r.nextInt(10) == 0 && map != endMap) {
                        boolean up = false, down = false, left = false, right = false;
                        if (row != 0) {
                            if ("~".equals(maps[map][0][row - 1][col])) {
                                up = true;
                            }
                        }

                        if (row != 9) {
                            if ("~".equals(maps[map][0][row + 1][col])) {
                                down = true;
                            }
                        }
                        if (col != 0) {
                            if ("~".equals(maps[map][0][row][col - 1])) {
                                left = true;
                            }
                        }
                        if (col != 9) {
                            if ("~".equals(maps[map][0][row][col + 1])) {
                                right = true;
                            }
                        }
                        if (up | down | left | right) {
                            maps[map][1][row][col] = "^" + cells.second_layer.Torches.getArgument(up, down, left, right);
                        }
                    }

                    if ("#".equals(maps[map][0][row][col])) {
                        maps[map][0][row][col] = "#" + (r.nextInt(5) + 1);
                    }
                    if ("~".equals(maps[map][0][row][col])) {
                        if (r.nextInt(3) == 0) {
                            maps[map][0][row][col] = "~" + (r.nextInt(4) + 2);
                        }else{
                            maps[map][0][row][col] = "~1";
                        }
                    }
                }
            }
        }

    }

    private static void filer(int save) {
        for (int map = 0; map < 25; map++) {
            String futureFile = "";
            String[][][] futureFutureFile = maps[map];
            for (int lay = 0; lay < 3; lay++) {
                for (int row = 0; row < 10; row++) {
                    for (int col = 0; col < 10; col++) {
                        futureFile = futureFile + futureFutureFile[lay][row][col] + " ";
                    }
                    futureFile = futureFile + "\n";
                }
                futureFile = futureFile + "\n";
            }
            try {
                Formatter file = new Formatter(new File("saves/save" + save + "/maps/map" + (map + 1) + ".txt"));
                file.format("%s", futureFile);
                file.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Потерян файл saves/save" + save + "/maps/map" + (map + 1) + ".txt !");
            }
        }
        try {
            f = new Formatter("saves//save" + save + "/stats.txt");
            f.format("%d 0 0 0 6 3", startMap);
            f.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Потерян файл saves//save" + save + "/stats.txt !");
        }
    }

    private static void edgeTest() {
        for (int col = 9; col <= 40; col = col + 10) {
            for (int row = 0; row < 50; row++) {
                if ("~".equals(level[0][row][col])) {
                    if (!"~".equals(level[0][row][col + 1])) {
                        level[0][row][col] = "#";
                    }
                }
            }
        }
        for (int col = 10; col <= 40; col = col + 10) {
            for (int row = 0; row < 50; row++) {
                if ("~".equals(level[0][row][col])) {
                    if (!"~".equals(level[0][row][col - 1])) {
                        level[0][row][col] = "#";
                    }
                }
            }
        }
        for (int row = 9; row <= 40; row = row + 10) {
            for (int col = 0; col < 50; col++) {
                if ("~".equals(level[0][row][col])) {

                    if (!"~".equals(level[0][row + 1][col])) {
                        level[0][row][col] = "#";
                    }
                }
            }
        }
        for (int row = 10; row <= 40; row = row + 10) {
            for (int col = 0; col < 50; col++) {
                if ("~".equals(level[0][row][col])) {

                    if (!"~".equals(level[0][row - 1][col])) {
                        level[0][row][col] = "#";
                    }

                }
            }
        }
    }

    private static void slicer() {
        int startrow = 0, startcol = 0;
        for (int map = 0; map < 25; map++) {
            if (startcol < 40) {
                startcol = startcol + 10;
            } else {
                startcol = 0;
                startrow = startrow + 10;
            }
            if (map == 0) {
                startcol = 0;
            }
            for (int lay = 0; lay < 3; lay++) {
                for (int row = startrow; row < startrow + 10; row++) {
                    for (int col = startcol; col < startcol + 10; col++) {
                        maps[map][lay][row - startrow][col - startcol] = level[lay][row][col];
                    }
                }
            }
        }

    }

    private static void minerOperator() {
        while (testLevel() < 950 || mainWay == true) {
            direction = null;
            chooseDirection();
            miner();
            if (exitDirection == 1) {
                if (minerA == endA + 1 & minerB == endB & mainWay == true) {
                    mainWay = false;
                    newWay();
                }
            }
            if (exitDirection == 2) {
                if (minerA == endA - 1 & minerB == endB & mainWay == true) {
                    mainWay = false;
                    newWay();
                }
            }
            if (exitDirection == 3) {
                if (minerA == endA & minerB == endB + 1 & mainWay == true) {
                    mainWay = false;
                    newWay();
                }
            }
            if (exitDirection == 4) {
                if (minerA == endA & minerB == endB - 1 & mainWay == true) {
                    mainWay = false;
                    newWay();
                }
            }
        }
    }

    private static void terms() {
        for (int f = 0; f < 3; f++) {
            Aup = true;
            Adown = true;
            Aleft = true;
            Aright = true;
            //Ограничение передвижения по краям уровней
            if (minerA == 9 || minerA == 10 || minerA == 19 || minerA == 20 || minerA == 29 || minerA == 30 || minerA == 39 || minerA == 40) {
                Aleft = false;
                Aright = false;
            }
            if (minerB == 9 || minerB == 10 || minerB == 19 || minerB == 20 || minerB == 29 || minerB == 30 || minerB == 39 || minerB == 40) {
                Aup = false;
                Adown = false;
            }
            //Ограничение передвижения по краям всей карты
            if (minerA == 1) {
                Aup = false;
            }
            if (minerA == 48) {
                Adown = false;
            }
            if (minerB == 1) {
                Aleft = false;
            }
            if (minerB == 48) {
                Aright = false;
            }
            //Назад дороги нет!
            if ("up".equals(direction)) {
                Adown = false;
            }
            if ("down".equals(direction)) {
                Aup = false;
            }
            if ("left".equals(direction)) {
                Aright = false;
            }
            if ("right".equals(direction)) {
                Aleft = false;
            }
            //Ограничение рытья на проходы
            int random = 10;
            if (testLevel() > 400 & testLevel() <= 500) {
                random = 15;
            }
            if (testLevel() > 500 & testLevel() <= 600) {
                random = 25;
            }
            if (testLevel() > 700 & testLevel() <= 800) {
                random = 40;
            }
            if (testLevel() > 800 & testLevel() <= 950) {
                random = 60;
            }
            if (r.nextInt(random) > 0) {
                try {
                    if ("~".equals(level[0][minerA - 1][minerB]) || "~".equals(level[0][minerA - 2][minerB]) || "~".equals(level[0][minerA - 1][minerB + 1]) || "~".equals(level[0][minerA - 1][minerB - 1])) {
                        Aup = false;
                    }
                    if ("~".equals(level[0][minerA + 1][minerB]) || "~".equals(level[0][minerA + 2][minerB]) || "~".equals(level[0][minerA + 1][minerB + 1]) || "~".equals(level[0][minerA + 1][minerB - 1])) {
                        Adown = false;
                    }
                    if ("~".equals(level[0][minerA][minerB - 1]) || "~".equals(level[0][minerA][minerB - 2]) || "~".equals(level[0][minerA + 1][minerB - 1]) || "~".equals(level[0][minerA - 1][minerB - 1])) {
                        Aleft = false;
                    }
                    if ("~".equals(level[0][minerA][minerB + 1]) || "~".equals(level[0][minerA][minerB + 2]) || "~".equals(level[0][minerA + 1][minerB + 1]) || "~".equals(level[0][minerA - 1][minerB + 1])) {
                        Aright = false;
                    }
                } catch (Exception e) {
                }
            }
            //Условия, направляющие к выходу
            if (mainWay == true & r.nextInt(2) == 0) {
                if (minerA < endA & minerB > endB) {
                    Aup = false;
                    Aright = false;
                }
                if (minerA < endA & minerB == endB) {
                    Aleft = false;
                    Aright = false;
                    Aup = false;
                }
                if (minerA < endA & minerB < endB) {
                    Aup = false;
                    Aleft = false;

                }
                if (minerA == endA & minerB < endB) {
                    Aup = false;
                    Adown = false;
                    Aleft = false;
                }
                if (minerA == endA & minerB > endB) {
                    Aup = false;
                    Adown = false;
                    Aright = false;
                }
                if (minerA > endA & minerB > endB) {
                    Adown = false;
                    Aright = false;
                }
                if (minerA > endA & minerB == endB) {
                    Aleft = false;
                    Aright = false;
                    Adown = false;
                }
                if (minerA > endA & minerB < endB) {
                    Adown = false;
                    Aleft = false;

                }
            }
            //Сохранение линии
            if (r.nextInt(3) == 0) {
                if ("up".equals(direction)) {
                    Adown = false;
                    Aleft = false;
                    Aright = false;
                }
                if ("down".equals(direction)) {
                    Aup = false;
                    Aleft = false;
                    Aright = false;
                }
                if ("left".equals(direction)) {
                    Aup = false;
                    Adown = false;
                    Aright = false;
                }
                if ("right".equals(direction)) {
                    Aup = false;
                    Adown = false;
                    Aleft = false;
                }

            }
            if (Aup == true || Adown == true || Aleft == true || Aright == true) {
                f = 10000;
            }

        }
    }

    private static void newWay() {
        do {
            minerA = r.nextInt(48) + 1;
            minerB = r.nextInt(48) + 1;
        } while (!"~".equals(level[0][minerA][minerB]));
    }

    private static void miner() {
        if ("up".equals(direction)) {
            minerA--;
        }
        if ("down".equals(direction)) {
            minerA++;
        }
        if ("left".equals(direction)) {
            minerB--;
        }
        if ("right".equals(direction)) {
            minerB++;
        }
        level[0][minerA][minerB] = "~";
    }

    private static void chooseDirection() {
        terms();
        int AD = 0;
        if (Aup == true) {
            AD++;
        }
        if (Adown == true) {
            AD++;
        }
        if (Aleft == true) {
            AD++;
        }
        if (Aright == true) {
            AD++;
        }
        if (AD > 0) {
            String[] directions = new String[AD];
            int x = 0;
            if (Aup == true) {
                directions[x] = "up";
                x++;
            }
            if (Adown == true) {
                directions[x] = "down";
                x++;
            }
            if (Aleft == true) {
                directions[x] = "left";
                x++;
            }
            if (Aright == true) {
                directions[x] = "right";
                x++;
            }
            direction = directions[r.nextInt(AD)];
        } else {
            newWay();
        }
    }

    private static int testLevel() {
        int waysInLevel = 0;
        for (int row = 0; row < 50; row++) {
            for (int col = 0; col < 50; col++) {
                if ("~".equals(level[0][row][col])) {
                    waysInLevel++;
                }
            }
        }
        return waysInLevel;
    }

    private static void findEndMap() {
        if (endA > 0 && endA < 10 && endB > 0 && endB < 10) {
            endMap = 1;
        }
        if (endA > 0 && endA < 10 && endB > 10 && endB < 20) {
            endMap = 2;
        }
        if (endA > 0 && endA < 10 && endB > 20 && endB < 30) {
            endMap = 3;
        }
        if (endA > 0 && endA < 10 && endB > 30 && endB < 40) {
            endMap = 4;
        }
        if (endA > 0 && endA < 10 && endB > 40 && endB < 50) {
            endMap = 5;
        }
        if (endA > 10 && endA < 20 && endB > 0 && endB < 10) {
            endMap = 6;
        }
        if (endA > 10 && endA < 20 && endB > 40 & endB < 50) {
            endMap = 10;
        }
        if (endA > 20 && endA < 30 && endB > 0 && endB < 10) {
            endMap = 11;
        }
        if (endA > 20 && endA < 30 && endB > 40 & endB < 50) {
            endMap = 15;
        }
        if (endA > 30 && endA < 40 && endB > 0 && endB < 10) {
            endMap = 16;
        }
        if (endA > 30 && endA < 40 && endB > 40 & endB < 50) {
            endMap = 20;
        }
        if (endA > 40 && endA < 50 && endB > 0 && endB < 10) {
            endMap = 21;
        }
        if (endA > 40 && endA < 50 && endB > 10 & endB < 20) {
            endMap = 22;
        }
        if (endA > 40 && endA < 50 && endB > 20 & endB < 30) {
            endMap = 23;
        }
        if (endA > 40 && endA < 50 && endB > 30 & endB < 40) {
            endMap = 24;
        }
        if (endA > 40 && endA < 50 && endB > 40 & endB < 50) {
            endMap = 25;

        }
    }

    private static void findStartMap() {
        if (startA > 0 && startA < 10 && startB > 0 && startB < 10) {
            startMap = 1;
        }
        if (startA > 0 && startA < 10 && startB > 10 && startB < 20) {
            startMap = 2;
        }
        if (startA > 0 && startA < 10 && startB > 20 && startB < 30) {
            startMap = 3;
        }
        if (startA > 0 && startA < 10 && startB > 30 && startB < 40) {
            startMap = 4;
        }
        if (startA > 0 && startA < 10 && startB > 40 && startB < 50) {
            startMap = 5;
        }
        if (startA > 10 && startA < 20 && startB > 0 && startB < 10) {
            startMap = 6;
        }
        if (startA > 10 && startA < 20 && startB > 40 && startB < 50) {
            startMap = 10;
        }
        if (startA > 20 && startA < 30 && startB > 0 && startB < 10) {
            startMap = 11;
        }
        if (startA > 20 && startA < 30 && startB > 40 && startB < 50) {
            startMap = 15;
        }
        if (startA > 30 && startA < 40 && startB > 0 && startB < 10) {
            startMap = 16;
        }
        if (startA > 30 && startA < 40 && startB > 40 && startB < 50) {
            startMap = 20;
        }
        if (startA > 40 && startA < 50 && startB > 0 && startB < 10) {
            startMap = 21;
        }
        if (startA > 40 && startA < 50 && startB > 10 && startB < 20) {
            startMap = 22;
        }
        if (startA > 40 && startA < 50 && startB > 20 && startB < 30) {
            startMap = 23;
        }
        if (startA > 40 && startA < 50 && startB > 30 && startB < 40) {
            startMap = 24;
        }
        if (startA > 40 && startA < 50 && startB > 40 && startB < 50) {
            startMap = 25;
        }
    }

}
