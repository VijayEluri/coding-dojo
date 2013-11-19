package wumpus;

import wumpus.user.Player;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class Game
{

    private static int[][] map = new int[4][4];
    Player player = new Player(0, 0);
    private Wumpus wumpus;

    public static void main( String[] args ) {

    }

    public Game() {
    }

    private boolean isPlayerMoveValid(int x, int y) {

        if (x < 0 || y < 0 || y >= map.length || x >= map[0].length)
            return false;

        return playerCanOnlyMoveOneStep(x, y);

    }

    private boolean playerCanOnlyMoveOneStep(int x, int y) {
        int xdiff = Math.abs(x - player.GetX());
        int ydiff = Math.abs(y - player.GetY());

        return ( (xdiff == 1 && ydiff == 0) || (xdiff == 0 && ydiff == 1) );
    }

    public Status movePlayer(int x, int y) {
        if (wumpus != null && (wumpus.x == x && wumpus.y == y)) {
            player.movePlayer(x, y);
            return new Status(StatusCode.DEAD);
        }

        if (isPlayerMoveValid(x, y)) {
            player.movePlayer(x, y);
            return new Status(StatusCode.OK);
        } else {
            return new Status(StatusCode.WALL);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isPlayerMoveValid(Direction direction) {
        switch (direction) {
            case E:
                return isPlayerMoveValid(player.GetX()+1, player.GetY());
            case N:
                return isPlayerMoveValid(player.GetX(), player.GetY()-1);
            case S:
                return isPlayerMoveValid(player.GetX(), player.GetY()+1);
            case W:
                return isPlayerMoveValid(player.GetX()-1, player.GetY());
        }
        return false;
    }
    public Status movePlayer(Direction direction) {
        switch (direction) {
            case E:
                return movePlayer(player.GetX() + 1, player.GetY());
            case N:
                return movePlayer(player.GetX(), player.GetY() - 1);
            case S:
                return movePlayer(player.GetX(), player.GetY() + 1);
            case W:
                return movePlayer(player.GetX() - 1, player.GetY());
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

    }

    public void placeWumpus(int x, int y) {
        this.wumpus = new Wumpus(x, y);
    }
}
