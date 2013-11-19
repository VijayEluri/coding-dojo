package wumpus.user;

/**
 * @author <a href="mailto:vegaasen@gmail.com">Vegard Aasen</a>
 */
public class Player {

    private int x;
    private int y;
    private boolean alive;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int GetX() {
        return x;
    }

    public int GetY() {
        return y;
    }

    public void movePlayer(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
