package game.core;

import java.util.Arrays;

public class Actions {
    /*
     Действие move
     Описание:
     Передвижение кем-либо
     какого-либо объекта
     Параметры:
     0 - кто двигает
     1 - направление
     */

    public static final Action THIS_MOVE_UP = new Action("move", new String[]{"this", "up"});
    public static final Action THIS_MOVE_DOWN = new Action("move", new String[]{"this", "down"});
    public static final Action THIS_MOVE_LEFT = new Action("move", new String[]{"this", "left"});
    public static final Action THIS_MOVE_RIGHT = new Action("move", new String[]{"this", "right"});
    public static final Action THIS_MOVE_AT_ALL = new Action("move", new String[]{"this", "at_all"});
    /*
     Действие moveFromBound
     Описание:
     Выход игрока из-за границы
     поля при переходе с локации
     на локацию
     Параметры:
     0 - направление
     */
    public static final Action MOVE_UP_FROM_BOUND = new Action("moveFromBound", new String[]{"up"});
    public static final Action MOVE_DOWN_FROM_BOUND = new Action("moveFromBound", new String[]{"down"});
    public static final Action MOVE_LEFT_FROM_BOUND = new Action("moveFromBound", new String[]{"left"});
    public static final Action MOVE_RIGHT_FROM_BOUND = new Action("moveFromBound", new String[]{"right"});

    /*
     Действие moveOn
     Описание:
     Передвижение какого-либо
     объекта на какой-то другой
     объект
     Параметры:
     0 - кто передавигается
     1 - направление
     */
    public static final Action PLAYER_MOVE_ON_UP = new Action("moveOn", new String[]{"player", "up"});
    public static final Action PLAYER_MOVE_ON_DOWN = new Action("moveOn", new String[]{"player", "down"});
    public static final Action PLAYER_MOVE_ON_LEFT = new Action("moveOn", new String[]{"player", "left"});
    public static final Action PLAYER_MOVE_ON_RIGHT = new Action("moveOn", new String[]{"player", "right"});

    public static final Action GHOST_MOVE_ON_UP = new Action("moveOn", new String[]{"ghost", "up"});
    public static final Action GHOST_MOVE_ON_DOWN = new Action("moveOn", new String[]{"ghost", "down"});
    public static final Action GHOST_MOVE_ON_LEFT = new Action("moveOn", new String[]{"ghost", "left"});
    public static final Action GHOST_MOVE_ON_RIGHT = new Action("moveOn", new String[]{"ghost", "right"});

    /*
     Действие action
     Описание:
     Тот, на кого направленно
     это действие, совершает
     действие в каком-либо
     направлнии
     Параметры:
     0 - кто совершает действие
     1 - направление
     */
    public static final Action ACTION_UP = new Action("action", new String[]{"up"});
    public static final Action ACTION_DOWN = new Action("action", new String[]{"down"});
    public static final Action ACTION_LEFT = new Action("action", new String[]{"left"});
    public static final Action ACTION_RIGHT = new Action("action", new String[]{"right"});
    /*
     Действие actionOn
     Описание:
     Действие. Просто действие.
     Совершает кем-то над кем-то
     в каком-то направлении действие
     Параметры:
     0 - кто совершает действие
     1 - направление
     */

    public static final Action PLAYER_ACTION_ON_UP = new Action("actionOn", new String[]{"player", "up"});
    public static final Action PLAYER_ACTION_ON_DOWN = new Action("actionOn", new String[]{"player", "down"});
    public static final Action PLAYER_ACTION_ON_LEFT = new Action("actionOn", new String[]{"player", "left"});
    public static final Action PLAYER_ACTION_ON_RIGHT = new Action("actionOn", new String[]{"player", "right"});
    /*
     Действие turn
     Описание:
     Поворачивает игрока
     в определянное направление
     Параметры:
     0 - направление поворота
     */

    public static final Action TURN_UP = new Action("turn", new String[]{"up"});
    public static final Action TURN_DOWN = new Action("turn", new String[]{"down"});
    public static final Action TURN_LEFT = new Action("turn", new String[]{"left"});
    public static final Action TURN_RIGHT = new Action("turn", new String[]{"right"});

    /*
     Действие return
     Описание:
     Просто возвращает соответствующую пременную
     Параметры:
     0 - название
     */
    public static final Action RETURN_CAN_GO = new Action("return", new String[]{"canGo",});
    public static final Action RETURN_CAN_ACTION = new Action("return", new String[]{"canAction",});

    private static final Action[] allActions = new Action[]{
        THIS_MOVE_UP, THIS_MOVE_DOWN, THIS_MOVE_LEFT, THIS_MOVE_RIGHT,
        PLAYER_MOVE_ON_UP, PLAYER_MOVE_ON_DOWN, PLAYER_MOVE_ON_LEFT, PLAYER_MOVE_ON_RIGHT,
        ACTION_UP, ACTION_DOWN, ACTION_LEFT, ACTION_RIGHT,THIS_MOVE_AT_ALL,
        PLAYER_ACTION_ON_UP, PLAYER_ACTION_ON_DOWN, PLAYER_ACTION_ON_LEFT, PLAYER_ACTION_ON_RIGHT,
        TURN_UP, TURN_DOWN, TURN_LEFT, TURN_RIGHT, RETURN_CAN_GO, RETURN_CAN_ACTION,
        MOVE_UP_FROM_BOUND, MOVE_DOWN_FROM_BOUND, MOVE_LEFT_FROM_BOUND, MOVE_RIGHT_FROM_BOUND,
        GHOST_MOVE_ON_UP, GHOST_MOVE_ON_DOWN, GHOST_MOVE_ON_LEFT, GHOST_MOVE_ON_RIGHT,};

    public static Action getAction(String name, String[] args) {
        Action ret = new Action("name", new String[]{"argument"});
        for (int x = 0; x < allActions.length; x++) {
            if (name.equals(allActions[x].name) & Arrays.equals(allActions[x].args, args)) {
                ret = allActions[x];
            }
        }
        return ret;
    }

    public static class Action {

        public final String name;
        public final String[] args;

        public Action(String name, String[] args) {
            this.name = name;
            this.args = args;
        }
    }
}
