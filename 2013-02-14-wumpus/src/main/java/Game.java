/**
 * Created with IntelliJ IDEA.
 * User: iterate
 * Date: 2/14/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {

    private int wumpusX;
    private boolean wumpusIsDead;

    public void setWumpus(int x, int y) {
        wumpusX = x;
    }

    public void setHero(int x, int y) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public String getStatus() {
        if (wumpusIsDead) {
            return "You won, you evil kitten killer!";
        } else if (wumpusX == 0) {
            return "You lost, sucker!";
        } else {
            return "Game on!";
        }
    }

    public void setWumpusDead() {
        wumpusIsDead = true;
    }
}
