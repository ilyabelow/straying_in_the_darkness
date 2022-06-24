package game.core;

import cells.first_layer.symbols.Letter;
import static game.Game.m;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Records {

    private static String[][] records = new String[2][5];

    public static void load() {
        download();
        remix();
        setOnMap();
        upload();
    }
    public static int getLastRecord(){
        download();
        remix();
        return Integer.parseInt(records[1][4]);
    }
    private static void download() {
        //Чикл по всем пяти рекордам
        for (int x = 0; x < 5; x++) {
            Scanner scn = null;
            //try-catch для открытия файла
            try {
                scn = new Scanner(new File("resources/records/record_" + (x + 1) + ".txt"));
            } catch (FileNotFoundException ex) {
                //Если файл не откроется
                records[0][x] = "ПУСТО";
                records[1][x] = "0";
            }
            //try-catch для изъятия из файла инфы
            try {
                records[0][x] = scn.next();
                //Преобразование имени
                //Перевод в верхний регистр
                records[0][x] = records[0][x].toUpperCase();
                //Обрезание
                if (records[0][x].length() > 5) {
                    records[0][x] = records[0][x].substring(0, 5);
                }
            } catch (Exception ex) {
                //Если файл пуст
                records[0][x] = "ПУСТО";
            }

            //try-catch для изъятия инфы из файла
            try {
                records[1][x] = scn.next();
            } catch (Exception ex) {
                //Если в файле было только имя
                records[1][x] = "0";
            }
            //try-catch для проверки числа на недопустимые символы
            try {
                //Ограничение числа
                if (Integer.parseInt(records[1][x]) > 9000) {
                    records[1][x] = "9000";
                }
                if (Integer.parseInt(records[1][x]) < 0) {
                    records[1][x] = "0";
                }
            } catch (Exception ex) {
                //Если таковые имеются
                records[1][x] = "0";
            }

        }
    }

    private static void upload() {

        //Переписывание файлов
        for (int x = 0; x < 5; x++) {

            try {
                Formatter f = new Formatter("resources/records/record_" + (x + 1) + ".txt");
                f.format("%s %s", records[0][x], records[1][x]);
                f.close();
            } catch (FileNotFoundException ex1) {
            }
        }
    }

    private static void remix() {
        //Перетасовывание рекордов
        String[][] newRecords = records;
        for (int start = 0; start < 4; start++) {
            int max = Integer.parseInt(newRecords[1][start]);
            int newMax = start;
            for (int place = start + 1; place < 5; place++) {
                if (Integer.parseInt(newRecords[1][place]) > max) {
                    newMax = place;
                }
            }
            String g;
            g = newRecords[0][newMax];
            newRecords[0][newMax] = newRecords[0][start];
            newRecords[0][start] = g;
            g = newRecords[1][newMax];
            newRecords[1][newMax] = newRecords[1][start];
            newRecords[1][start] = g;
        }
        records = newRecords;
    }

    public static void add(String name, int score) {
        download();
        if (score > 9000) {
            score = 9000;
        }
        if (score < 0) {
            score = 0;
        }
        name = name.toUpperCase();
        if (name.length() > 5) {
            name = name.substring(0, 5);
        }
        if (score >= getLastRecord()) {
            records[0][4] = name;
            records[1][4] = String.valueOf(score);
            remix();
            upload();
        }
    }

    private static void setOnMap() {
        //Доступные символы
        String[][] validSymbols = new String[][]{
            {"А", "Б", "В", "Г", "Д", "Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я",},
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0",},};

        for (int x = 0; x < 5; x++) {
            //Цикл установки имени на карту
            for (int y = 0; y < records[0][x].length(); y++) {
                //Цикл букв
                for (int z = 0; z < 33; z++) {
                    //Сравнение символа в имени и сомволом из массива
                    if (records[0][x].substring(y, y + 1).equals(validSymbols[0][z])) {
                        //Установка и присваевание соответсвуещего аргумента
                        m.setCell(new Letter(x * 2 + 1, y, "russian", true));
                        m.getCell(x * 2 + 1, y, 0).setArgument(z + 1);
                    }
                }
                //То же с цифрами
                for (int z = 0; z < 10; z++) {
                    if (records[0][x].substring(y, y + 1).equals(validSymbols[1][z])) {
                        m.setCell(new cells.first_layer.symbols.Number(x * 2 + 1, y, true));
                        m.getCell(x * 2 + 1, y, 0).setArgument(z + 1);
                    }
                }
            }
            for (int y = records[1][x].length(), pl = 9; y > 0 & pl > 5; y--, pl--) {
                //То же что и с иминем, только в другую сторону
                //и из-за этого "в другую сторону" всё усложнянтся.
                //pl - место установки знака
                for (int z = 0; z < 10; z++) {
                    if (records[1][x].substring(y - 1, y).equals(validSymbols[1][z])) {
                        m.setCell(new cells.first_layer.symbols.Number(x * 2 + 1, pl, true));
                        m.getCell(x * 2 + 1, pl, 0).setArgument(z + 1);
                    }
                }
            }
        }
    }
}
