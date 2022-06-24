package out.picture;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Textures {

    public static Image coin_shadow, ghost_shadow, bubble, menu_pause_message, logo,
            game_pause_message, win_message, ghost, dying, reset_message, handle, flame,
            russian_letters, pointers, player_stay, player_go, input_message, smoke,
            numbers, coins, eyelinds, chest, frame, generating_done_message,
            stats_icons, OVER9000, door, sign, score_message, records_font;

    public static Image[] darkness = new Image[20];
    public static Image[] dust = new Image[10];
    public static Image[] ways = new Image[10];
    public static Image[] walls = new Image[10];

    public static void initialization() {
        boolean lost = false;
        for (int x = 0; x < 20; x++) {
            try {
                darkness[x] = ImageIO.read(new File("textures/darkness/" + (x + 1) + ".png"));
                darkness[x] = darkness[x].getScaledInstance(5, 5, 0);
            } catch (IOException ex) {
                System.out.println("Darkness " + (x + 1) + "  texture lost!");
                lost = true;
            }
        }
        try {
            frame = ImageIO.read(new File("textures/frame/frame.png"));
        } catch (IOException ex) {
            System.out.println("Frame texture lost!");
            lost = true;
        }

        for (int x = 0; x < 10; x++) {
            try {
                dust[x] = ImageIO.read(new File("textures/dust/" + (x + 1) + ".png"));
                dust[x] = dust[x].getScaledInstance(10 + (2 * x), 10 + (2 * x), 0);
            } catch (IOException ex) {
                System.out.println("Dust " + (x + 1) + "  texture lost!");
                lost = true;
            }
        }
        try {
            smoke = ImageIO.read(new File("textures/torch/smoke.png"));
            smoke = smoke.getScaledInstance(192, 24, 0);
        } catch (IOException ex) {
            System.out.println("Smoke texture lost!");
            lost = true;
        }
        try {
            score_message = ImageIO.read(new File("textures/messages/score_message.png"));
            score_message = score_message.getScaledInstance(480, 540, 0);
        } catch (IOException ex) {
            System.out.println("Score message texture lost!");
            lost = true;
        }
        try {
            input_message = ImageIO.read(new File("textures/messages/input_message.png"));
            input_message = input_message.getScaledInstance(480, 300, 0);
        } catch (IOException ex) {
            System.out.println("Input message texture lost!");
            lost = true;
        }
        try {
            records_font = ImageIO.read(new File("textures/messages/records_font.png"));
            records_font = records_font.getScaledInstance(1815, 85, 0);
        } catch (IOException ex) {
            System.out.println("Records font texture lost!");
            lost = true;
        }

        try {
            reset_message = ImageIO.read(new File("textures/messages/reset_message.png"));
            reset_message = reset_message.getScaledInstance(480, 240, 0);
        } catch (IOException ex) {
            System.out.println("Reset message texture lost!");
            lost = true;
        }
        try {
            generating_done_message = ImageIO.read(new File("textures/messages/generating_done_message.png"));
            generating_done_message = generating_done_message.getScaledInstance(480, 240, 0);
        } catch (IOException ex) {
            System.out.println("Generating done message texture lost!");
            lost = true;
        }
        try {
            menu_pause_message = ImageIO.read(new File("textures/messages/menu_pause_message.png"));
            menu_pause_message = menu_pause_message.getScaledInstance(480, 240, 0);
        } catch (IOException ex) {
            System.out.println("Menu pause message texture lost!");
            lost = true;
        }
        try {
            ghost = ImageIO.read(new File("textures/ghosts/ghost.png"));
            ghost = ghost.getScaledInstance(156, 48, 0);
        } catch (IOException ex) {
            System.out.println("Ghost texture lost!");
            lost = true;
        }
        try {
            win_message = ImageIO.read(new File("textures/messages/win_message.png"));
            win_message = win_message.getScaledInstance(480, 240, 0);
        } catch (IOException ex) {
            System.out.println("Win message texture lost!");
            lost = true;
        }
        try {
            game_pause_message = ImageIO.read(new File("textures/messages/game_pause_message.png"));
            game_pause_message = game_pause_message.getScaledInstance(480, 240, 0);
        } catch (IOException ex) {
            System.out.println("Game pause message texture lost!");
            lost = true;
        }
        try {
            sign = ImageIO.read(new File("textures/game/sign.png"));
            sign = sign.getScaledInstance(60, 90, 0);
        } catch (IOException ex) {
            System.out.println("Sign texture lost!");
            lost = true;
        }
        try {
            bubble = ImageIO.read(new File("textures/game/bubble.png"));
            bubble = bubble.getScaledInstance(240, 180, 0);
        } catch (IOException ex) {
            System.out.println("Bubble texture lost!");
            lost = true;
        }
        try {
            door = ImageIO.read(new File("textures/game/door.png"));
            door = door.getScaledInstance(60, 90, 0);
        } catch (IOException ex) {
            System.out.println("Door texture lost!");
            lost = true;
        }
        try {
            handle = ImageIO.read(new File("textures/torch/handle.png"));
            handle = handle.getScaledInstance(60, 21, 0);
        } catch (IOException ex) {
            System.out.println("Handle texture lost!");
            lost = true;
        }
        try {
            flame = ImageIO.read(new File("textures/torch/flame.png"));
            flame = flame.getScaledInstance(126, 18, 0);
        } catch (IOException ex) {
            System.out.println("Flame texture lost!");
            lost = true;
        }
        try {
            OVER9000 = ImageIO.read(new File("textures/stats/OVER9000.png"));
            OVER9000 = OVER9000.getScaledInstance(100, 36, 0);
        } catch (IOException ex) {
            System.out.println("OVER9000 texture lost!");
            lost = true;
        }
        try {
            stats_icons = ImageIO.read(new File("textures/stats/stats_icons.png"));
            stats_icons = stats_icons.getScaledInstance(120, 24, 0);
        } catch (IOException ex) {
            System.out.println("Stats icons texture lost!");
            lost = true;
        }
        for (int x = 0; x < 5; x++) {
            try {
                walls[x] = ImageIO.read(new File("textures/walls/" + (x + 1) + ".png"));
                walls[x] = walls[x].getScaledInstance(60, 90, 0);
            } catch (IOException ex) {
                System.out.println("Wall " + (x + 1) + "  texture lost!");
                lost = true;
            }
        }
        for (int x = 0; x < 5; x++) {
            try {
                ways[x] = ImageIO.read(new File("textures/ways/" + (x + 1) + ".png"));
                ways[x] = ways[x].getScaledInstance(60, 60, 0);
            } catch (IOException ex) {
                System.out.println("Way " + (x + 1) + "  texture lost!");
                lost = true;
            }
        }
        try {
            ghost_shadow = ImageIO.read(new File("textures/ghosts/ghost_shadow.png"));
            ghost_shadow = ghost_shadow.getScaledInstance(39, 150, 0);
        } catch (IOException ex) {
            System.out.println("Ghost shadow texture lost!");
            lost = true;
        }
        try {
            coin_shadow = ImageIO.read(new File("textures/coins/coin_shadow.png"));
            coin_shadow = coin_shadow.getScaledInstance(24, 90, 0);
        } catch (IOException ex) {
            System.out.println("Coin shadow texture lost!");
            lost = true;
        }
        try {
            russian_letters = ImageIO.read(new File("textures/symbols/russian_letters.png"));
            russian_letters = russian_letters.getScaledInstance(720, 540, 0);
        } catch (IOException ex) {
            System.out.println("Russian letters texture lost!");
            lost = true;
        }
        try {
            pointers = ImageIO.read(new File("textures/symbols/pointers.png"));
            pointers = pointers.getScaledInstance(240, 345, 0);
        } catch (IOException ex) {
            System.out.println("Pointers texture lost!");
            lost = true;
        }
        try {
            player_stay = ImageIO.read(new File("textures/player/player_stay.png"));
            player_stay = player_stay.getScaledInstance(144, 51, 0);
        } catch (IOException ex) {
            System.out.println("Player stay texture lost!");
            lost = true;
        }
        try {
            player_go = ImageIO.read(new File("textures/player/player_go.png"));
            player_go = player_go.getScaledInstance(144, 204, 0);
        } catch (IOException ex) {
            System.out.println("Player go texture lost!");
            lost = true;
        }
        try {
            dying = ImageIO.read(new File("textures/player/dying.png"));
            dying = dying.getScaledInstance(144, 450, 0);
        } catch (IOException ex) {
            System.out.println("Dying texture lost!");
            lost = true;
        }
        try {
            eyelinds = ImageIO.read(new File("textures/player/eyelids.png"));
            eyelinds = eyelinds.getScaledInstance(12, 6, 0);
        } catch (IOException ex) {
            System.out.println("Eyelins texture lost!");
            lost = true;
        }
        try {
            numbers = ImageIO.read(new File("textures/symbols/numbers.png"));
            numbers = numbers.getScaledInstance(300, 171, 0);
        } catch (IOException ex) {
            System.out.println("Numbers texture lost!");
            lost = true;
        }
        try {
            coins = ImageIO.read(new File("textures/coins/coins.png"));
            coins = coins.getScaledInstance(180, 36, 0);
        } catch (IOException ex) {
            System.out.println("Coins texture lost!");
            lost = true;
        }
        try {
            logo = ImageIO.read(new File("textures/frame/logo.png"));
            logo = logo.getScaledInstance(50, 50, 0);
        } catch (IOException ex) {
            System.out.println("Logo texture lost!");
            lost = true;
        }
        try {
            chest = ImageIO.read(new File("textures/game/chest.png"));
            chest = chest.getScaledInstance(78, 156, 0);
        } catch (IOException ex) {
            System.out.println("Chest texture lost!");
            lost = true;
        }
        if (lost) {
            System.exit(42);
        }

    }
}
