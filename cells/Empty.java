package cells;


import game.core.Actions;
import java.awt.Graphics;

public class Empty extends Cell{
    
    public Empty(int A, int B) {
        super(A,B,new String[][]{{"all"},{"all"}},1, -1,"_","empty",0);
    }

    public void paint(Graphics g){
    }

    @Override
    public boolean canAction(Actions.Action action) {
        return "moveOn".equals(action.name);
    }

    @Override
    public void doAction(Actions.Action action) {
    }

    
}
