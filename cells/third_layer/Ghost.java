package cells.third_layer;

import game.core.Actions;
import game.Game;
import static game.Game.m;
import java.awt.Graphics;
import java.util.Random;
import animations.Animation;
import cells.Cell;
import static out.picture.Window.clickCell;
import static out.picture.Textures.ghost;
import static out.picture.Textures.ghost_shadow;

public class Ghost extends Cell {

    private int A, B;

    private int verticalOffset = 0, horizontalOffset = 0;

    private boolean go = false;

    public Ghost(int A, int B) {
        super(A, B, new String[][]{{"~", "r", "n", "p"}, {"all",}}, 4, 2, "$", "ghost", 1);
        this.A = A;
        this.B = B;
        getAC().setAnimation(new Animation("go", 2, 1, 1, new int[]{new Random().nextInt(11) - 5, new Random().nextInt(2)}) {
            @Override
            public void shot() {
                if (go) {
                    if (getArgument() == 2) {
                        verticalOffset = verticalOffset + 3;
                    }
                    if (getArgument() == 1) {
                        verticalOffset = verticalOffset - 3;
                    }
                    if (getArgument() == 4) {
                        horizontalOffset = horizontalOffset + 3;
                    }
                    if (getArgument() == 3) {
                        horizontalOffset = horizontalOffset - 3;
                    }
                }
                if (verticalOffset == 0 && horizontalOffset == 0) {
                    go();
                }
                if (args[1] == 1) {
                    if (args[0] < 5) {
                        args[0]++;
                    } else {
                        args[0]--;
                        args[1] = 0;
                    }
                } else {
                    if (args[0] > -5) {
                        args[0]--;
                    } else {
                        args[0]++;
                        args[1] = 1;
                    }
                }
            }

            @Override
            public void end() {
                setShot(1);
                shot();
            }

            @Override
            public void paint(Graphics g) {
                drawTexture(ghost_shadow, g, getA(), getB(), 21 + verticalOffset, 12 + horizontalOffset, 15, 39, (args[0] + 4) * 15, 0);
                drawTexture(ghost, g, getA(), getB(), -35 + verticalOffset + args[0], 12 + horizontalOffset, 54, 39, 0, (getArgument() - 1) * 39);
            }

        });
    }

    private void go() {
        if (canAction(Actions.getAction("move", new String[]{"this", Game.getDirection(getArgument())}))) {
            motion(Game.getDirection(getArgument()));
            go = true;
        } else {
            if ("player".equals(m.getCell(Game.getNewA(Game.getDirection(getArgument()), A), Game.getNewB(Game.getDirection(getArgument()), B), 2).getName())) {
                if (getArgument() == 1 & A == 1 || getArgument() == 2 & A == 8 || getArgument() == 3 & B == 1 || getArgument() == 4 & B == 8) {
                } else {
                    m.getCell(Game.getNewA(Game.getDirection(getArgument()), A), Game.getNewB(Game.getDirection(getArgument()), B), 2).doAction(Actions.getAction("moveOn", new String[]{"ghost", Game.getDirection(getArgument())}));
                }
            }
            go = false;
            chooseDirection();
        }
    }

    @Override
    public void paint(Graphics g) {
        if (go) {
            if (getArgument() == 1) {
                clickCell(A + 1, B, g);
            }
            if (getArgument() == 2) {
                clickCell(A - 1, B, g);
            }
            if (getArgument() == 3) {
                clickCell(A, B + 1, g);
            }
            if (getArgument() == 4) {
                clickCell(A, B - 1, g);
            }
        }
        getAC().getAnimation().paint(g);

    }

    @Override
    public boolean canAction(Actions.Action action) {
        if ("move".equals(action.name)) {
            if ("this".equals(action.args[0])) {
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 0).canAction(Actions.getAction("moveOn", new String[]{"ghost", action.args[1]}))) {
                    return false;
                }
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 1).canAction(Actions.getAction("moveOn", new String[]{"ghost", action.args[1]}))) {
                    return false;
                }
                if (!m.getCell(Game.getNewA(action.args[1], A), Game.getNewB(action.args[1], B), 2).canAction(Actions.getAction("moveOn", new String[]{"ghost", action.args[1]}))) {
                    return false;
                }
                if ("up".equals(action.args[1]) & A == 1 || "down".equals(action.args[1]) & A == 8 || "left".equals(action.args[1]) & B == 1 || "right".equals(action.args[1]) & B == 8) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void doAction(Actions.Action action) {
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
        m.getCell(A, B, 0).doAction(Actions.getAction("moveOn", new String[]{"ghost", direction}));
        m.getCell(A, B, 1).doAction(Actions.getAction("moveOn", new String[]{"ghost", direction}));
        m.getCell(A, B, 2).doAction(Actions.getAction("moveOn", new String[]{"ghost", direction}));
    }

    private void chooseDirection() {
        boolean[] approved = new boolean[]{false, false, false, false};
        int howManyDirectionsAreApprowed = 0;
        if (canAction(Actions.getAction("move", new String[]{"this", "up"})) || "player".equals(m.getCell(A - 1, B, 2).getName()) & A != 1) {
            approved[0] = true;
            howManyDirectionsAreApprowed++;
        }
        if (canAction(Actions.getAction("move", new String[]{"this", "down"})) || "player".equals(m.getCell(A + 1, B, 2).getName()) & A != 8) {
            approved[1] = true;
            howManyDirectionsAreApprowed++;
        }
        if (canAction(Actions.getAction("move", new String[]{"this", "left"})) || "player".equals(m.getCell(A, B - 1, 2).getName()) & B != 1) {
            approved[2] = true;
            howManyDirectionsAreApprowed++;
        }
        if (canAction(Actions.getAction("move", new String[]{"this", "right"})) || "player".equals(m.getCell(A, B + 1, 2).getName()) & B != 8) {
            approved[3] = true;
            howManyDirectionsAreApprowed++;
        }
        if (howManyDirectionsAreApprowed > 0) {
            int[] directions = new int[howManyDirectionsAreApprowed];
            int x = 0;
            if (approved[0] == true) {
                directions[x] = 1;
                x++;
            }
            if (approved[1] == true) {
                directions[x] = 2;
                x++;
            }
            if (approved[2] == true) {
                directions[x] = 3;
                x++;
            }
            if (approved[3] == true) {
                directions[x] = 4;
                x++;
            }
            setArgument(directions[new Random().nextInt(x)]);
        }

    }

    @Override
    public int getA() {
        return A;
    }

    @Override
    public int getB() {
        return B;
    }
}
