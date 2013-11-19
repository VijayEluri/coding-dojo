/**
 * Created with IntelliJ IDEA.
 * User: ivar
 * Date: 3/14/13
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class Wumpus {

    int x, y;
    private boolean dead;

    public Wumpus(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDead() {
        return dead;
    }

    public void die() {
        dead = true;
    }
}
