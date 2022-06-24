package cells.third_layer;

import java.awt.Graphics;
import java.util.Random;
import game.core.Actions;
import game.play.Bubble;
import game.play.Darkness;
import game.Game;
import static game.Game.m;
import animations.Animation;
import cells.Cell;
import static out.picture.Window.clickCell;
import static out.picture.Textures.dying;
import static out.picture.Textures.eyelinds;
import static out.picture.Textures.player_go;
import static out.picture.Textures.player_stay;

public class Player extends Cell {
    
    private boolean canAction = true, goFromBound = false;
    private int A, B;
    private int verticalOffset, horizontalOffset;
    private int shotsToTurn;
    
    public Player(int A, int B) {
        super(A, B, new String[][]{{"~", "r", "f", "n"}, {"all",}}, 6, 2, "A", "player", 5);
        setArgument(2);
        this.A = A;
        this.B = B;
        setStayAnimation();
    }
    
    @Override
    public void paint(Graphics g) {
        
        getAC().getAnimation().paint(g);
        if (getAC().getAnimation(1) != null) {
            getAC().getAnimation(1).paint(g);
        }
        drawLight();
    }
    
    @Override
    public int getA() {
        return A;
    }
    
    @Override
    public int getB() {
        return B;
    }
    
    @Override
    public boolean canAction(Actions.Action action) {
        if ("move".equals(action.name) & canAction) {
            if (!action.args[1].equals("at_all")) {
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 0).canAction(Actions.getAction("moveOn", new String[]{"player", action.args[1]}))) {
                    return false;
                }
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 1).canAction(Actions.getAction("moveOn", new String[]{"player", action.args[1]}))) {
                    return false;
                }
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 2).canAction(Actions.getAction("moveOn", new String[]{"player", action.args[1]}))) {
                    return false;
                }
                return true;
            } else {
                return canAction;
            }
        }
        if ("action".equals(action.name)) {
            return canAction;
        }
        if ("turn".equals(action.name)) {
            return canAction;
        }
        return false;
    }
    
    @Override
    public void doAction(Actions.Action action) {
        if ("moveOn".equals(action.name)) {
            if ("ghost".equals(action.args[0]) && !"dying".equals(getAC().getAnimation().name)) {
                canAction = false;
                getAC().setAnimation(new Animation("dying", 10, 1, 1, null) {
                    
                    @Override
                    public void shot() {
                    }
                    
                    @Override
                    public void end() {
                        m.removeCell(A, B, 2);
                        getAC().setAnimation(null);
                    }
                    
                    @Override
                    public void paint(Graphics g) {
                        drawTexture(dying, g, A, B, -15 + verticalOffset, 12 + horizontalOffset, 45, 36, (getAC().getAnimation().getShot() - 1) * 45, (getArgument() - 1) * 36);
                    }
                });
            }
        }
        if ("move".equals(action.name)) {
            if ("this".equals(action.args[0])) {
                if (canAction(action) == true) {
                    motion(action.args[1]);
                    canAction = false;
                }
            }
        }
        if ("turn".equals(action.name) && canAction) {
            if ("up".equals(action.args[0])) {
                setArgument(1);
            }
            if ("down".equals(action.args[0])) {
                setArgument(2);
            }
            if ("left".equals(action.args[0])) {
                setArgument(3);
            }
            if ("right".equals(action.args[0])) {
                setArgument(4);
            }
            shotsToTurn = 20;
        }
        if ("action".equals(action.name) && canAction) {
            action(action.args[0]);
            canAction = false;
        }
        
        if ("moveFromBound".equals(action.name)) {
            if ("up".equals(action.args[0])) {
                verticalOffset = 60;
            }
            if ("down".equals(action.args[0])) {
                verticalOffset = -60;
            }
            if ("left".equals(action.args[0])) {
                horizontalOffset = 60;
            }
            if ("right".equals(action.args[0])) {
                horizontalOffset = -60;
            }
            canAction = false;
            goFromBound = true;
            setGoAnimation();
        }
    }
    
    private void motion(String direction) {
        if (direction.equals("up")) {
            verticalOffset = 60;
        }
        if (direction.equals("down")) {
            verticalOffset = -60;
        }
        if (direction.equals("left")) {
            horizontalOffset = 60;
        }
        if (direction.equals("right")) {
            horizontalOffset = -60;
        }
        Game.m.removeCell(A, B, 2);
        A = Game.getNewA(direction, A);
        B = Game.getNewB(direction, B);
        m.setCell(this);
        m.getCell(A, B, 0).doAction(Actions.getAction("moveOn", new String[]{"player", direction}));
        m.getCell(A, B, 1).doAction(Actions.getAction("moveOn", new String[]{"player", direction}));
        m.getCell(A, B, 2).doAction(Actions.getAction("moveOn", new String[]{"player", direction}));
        setGoAnimation();
    }
    
    private void action(String direction) {
        if (m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 0).canAction(Actions.getAction("actionOn", new String[]{"player", direction}))) {
            m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 0).doAction(Actions.getAction("actionOn", new String[]{"player", direction}));
        } else {
            if (m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 1).canAction(Actions.getAction("actionOn", new String[]{"player", direction}))) {
                m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 1).doAction(Actions.getAction("actionOn", new String[]{"player", direction}));
            }
        }
        if (m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 2).canAction(Actions.getAction("actionOn", new String[]{"player", direction}))) {
            m.getCell(Game.getNewA(direction, A), Game.getNewB(direction, B), 2).doAction(Actions.getAction("actionOn", new String[]{"player", direction}));
        }
    }
    
    private void setGoAnimation() {
        getAC().setAnimation(0, new Animation("go", 12, 1, 1, new int[]{1}) {
            @Override
            public void shot() {
                if (getArgument() == 2) {
                    verticalOffset = verticalOffset + 5;
                }
                if (getArgument() == 1) {
                    verticalOffset = verticalOffset - 5;
                }
                if (getArgument() == 4) {
                    horizontalOffset = horizontalOffset + 5;
                }
                if (getArgument() == 3) {
                    horizontalOffset = horizontalOffset - 5;
                }
                Bubble.setCoordinates(A, B);
                if (getShot() >= 1 & getShot() <= 3) {
                    args[0]++;
                }
                if (getShot() >= 4 & getShot() <= 6) {
                    args[0]--;
                }
                if (getShot() >= 7 & getShot() <= 9) {
                    args[0]++;
                }
                if (getShot() >= 10 & getShot() <= 12) {
                    args[0]--;
                }
                setBlinkAnimation();
                Bubble.setOffsets(verticalOffset, horizontalOffset, args[0]);
            }
            
            @Override
            public void end() {
                setStayAnimation();
                horizontalOffset = 0;
                verticalOffset = 0;
                canAction = true;
                if (goFromBound) {
                    goFromBound = false;
                }
            }
            
            @Override
            public void paint(Graphics g) {
                
                if (!goFromBound) {
                    if (getArgument() == 2) {
                        clickCell(A - 1, B, g);
                    }
                    if (getArgument() == 1) {
                        clickCell(A + 1, B, g);
                    }
                    if (getArgument() == 4) {
                        clickCell(A, B - 1, g);
                    }
                    if (getArgument() == 3) {
                        clickCell(A, B + 1, g);
                    }
                }
                for (int x = 2; x < 5; x++) {
                    if (getAC().getAnimation(x) != null) {
                        getAC().getAnimation(x).paint(g);
                    }
                }
                drawTexture(player_go, g, A, B, 21 + verticalOffset, 12 + horizontalOffset, 12, 36, 39 + ((getAC().getAnimation().getShot() - 1) / 3 * 51), (getArgument() - 1) * 36);
                drawTexture(player_go, g, A, B, -15 - args[0] + verticalOffset, 12 + horizontalOffset, 39, 36, ((getAC().getAnimation().getShot() - 1) / 3) * 51, (getArgument() - 1) * 36);
            }
            
        });
    }
    
    private void setStayAnimation() {
        getAC().setAnimation(0, new Animation("stay", 16, 1, 4, new int[]{0}) {
            @Override
            public void shot() {
                setBlinkAnimation();
                
                if (shotsToTurn != 0) {
                    shotsToTurn--;
                } else {
                    setArgument(2);
                }
                if (!canAction) {
                    canAction = true;
                }
                if (getShot() >= 6 & getShot() <= 8) {
                    args[0]++;
                }
                if (getShot() >= 14 & getShot() <= 16) {
                    args[0]--;
                }
                Bubble.setOffsets(verticalOffset, horizontalOffset, args[0]);
            }
            
            @Override
            public void end() {
                setShot(1);
            }
            
            @Override
            public void paint(Graphics g) {
                drawTexture(player_stay, g, A, B, 21, 12, 12, 36, 39, (getArgument() - 1) * 36);
                drawTexture(player_stay, g, A, B, -15 - args[0], 12, 39, 36, 0, (getArgument() - 1) * 36);
            }
        });
    }
    
    private void setBlinkAnimation() {
        if (getAC().getAnimation(1) == null && new Random().nextInt(15) == 0) {
            
            getAC().setAnimation(1, new Animation("blink", 6, 1, 1, new int[]{3}) {
                
                @Override
                public void shot() {
                    if (getShot() == 1 | getShot() == 2 | getShot() == 3 | getShot() == 4) {
                        args[0]--;
                    }
                    if (getShot() == 5 | getShot() == 6) {
                        args[0]++;
                    }
                }
                
                @Override
                public void end() {
                    getAC().setAnimation(1, null);
                }
                
                @Override
                public void paint(Graphics g) {
                    drawTexture(eyelinds, g, A, B, - 3 + verticalOffset - getAC().getAnimation().args[0], 24 + horizontalOffset, 3, 12, args[0], 0);
                }
                
            });
        }
    }
    
    private void drawLight() {
        
        if (Game.stats[3].getCount() > 0) {
            int torches = Game.stats[3].getCount();
            if (torches > 25) {
                torches = 25;
            }
            
            float light = (float) 0.25;
            int radius = torches + 8;
            if (getAC().getAnimation(1) != null) {
                light = (float) (light - 0.05);
            }
            if ("dying".equals(getAC().getAnimation().name)) {
                radius = radius - getAC().getAnimation().getShot();
            }
            if (radius < 0) {
                radius = 0;
            }
            Darkness.setLight(A * 12 + (verticalOffset / 5) + 8, B * 12 + (horizontalOffset / 5) + 8, radius, light);
            
        }
        
    }
}
