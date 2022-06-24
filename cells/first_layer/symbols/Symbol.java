
package cells.first_layer.symbols;

import cells.Cell;
import game.core.Actions;

public abstract class Symbol extends Cell{
    
    private final boolean onGround;
    
    public Symbol(int A, int B, int maxArgument, String mark, String name, boolean onGround) {
        super(A, B, null, maxArgument, 0, groundTest(mark,onGround), groundTest(name,onGround),0);
        this.onGround = onGround;
    }

    @Override
    public final boolean canAction(Actions.Action action) {
        return "moveOn".equals(action.name) & onGround;
    }

    @Override
    public final void doAction(Actions.Action action) {}

    public boolean getGrounded(){
        return onGround;
    }
    
    
    private static String groundTest(String in, boolean onGround){
        if(onGround){
            in = in.toLowerCase();
        }else{
            in = in.toUpperCase();
        }
        return in;
    }
}
