/**
 * Created with IntelliJ IDEA.
 * User: ivar
 * Date: 3/14/13
 * Time: 3:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Player {

    private Game game;
    int x;
    int y;
    private boolean dead = false;


    public Player() {
    }

    public Player(Game game, int x, int y) {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void shoot(char direction) {
        game.shoot(direction);
    }

    public boolean isDead() {
        return dead;
    }

    public void die() {
        dead = true;
    }
}
