/**
 * Created with IntelliJ IDEA.
 * User: iterate
 * Date: 2/14/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {

    private int wumpusX;
    private int wumpusY;
    private boolean wumpusIsDead;
    private int heroX;
    private int heroY;
    private int boardSize;

    public Game(int boardSize) {
        this.boardSize = boardSize;
    }

    public Game() {

    }

    public void setWumpus(int x, int y) {
        wumpusX = x;
        wumpusY = y;
    }

    public void setHero(int x, int y) {
        heroX = x;
        heroY = y;
    }

    public String getStatus() {
        if (wumpusIsDead) {
            return "You won, you evil kitten killer!";
        } else if (wumpusX == heroX && wumpusY == heroY) {
            return "You lost, sucker!";
        } else {
            return "Game on!";
        }
    }

    public void setWumpusDead() {
        wumpusIsDead = true;
    }

    public int getHeroY() {
        return heroY;
    }

    public int getHeroX() {
        return heroX;
    }

    public int[] getHero() {
        return new int[] {heroX, heroY};
    }


    public String moveHero(Direction direction) {
        if(Direction.NORTH.equals(direction)){
           if (heroY < boardSize - 1) {
               heroY++;
           } else {
               return invalidMove(direction);
           }
        }else if (Direction.SOUTH.equals(direction)){
           if (heroY > 0) {
               heroY--;
           } else {
               return invalidMove(direction);
           }
        } else if (Direction.WEST.equals(direction)) {
            if (heroX > 0) {
                heroX--;
            } else {
                return invalidMove(direction);
            }
        } else if (Direction.EAST.equals(direction)){
            if (heroX < boardSize - 1) {
                heroX++;
            } else {
                return invalidMove(direction);
            }
        }

        return null;

    }

    private String invalidMove(Direction direction) {
        return "Can't move " + direction.name().toLowerCase() + " from here";
    }

}
