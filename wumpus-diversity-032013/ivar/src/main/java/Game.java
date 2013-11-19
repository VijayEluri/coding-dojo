/**
 * Created with IntelliJ IDEA.
 * User: ivar
 * Date: 3/14/13
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Game {

    Room[][] gameBoard;
    Player player;
    Wumpus wumpus;

    public Game() {
        this.gameBoard = createGameBoard();
        player = new Player(this, 0, 0);

        int sizex = 5;
        int sizey = 5;

        int x, y;
        do {
            x = (int) (Math.random() * sizex);
            y = (int) (Math.random() * sizey);
        } while (x != player.getX() || y != player.getY());

        wumpus = new Wumpus(x, y);


    }

    private Room[][] createGameBoard() {
        Room[][] board = new Room[5][5];
        int x = 0;
        int y = 0;
        for (Room[] rooms : board) {

            for (Room room : rooms) {
                board[x][y] = new Room(x, y);
                x++;
            }
            x = 0;
            y++;
        }

        return board;

    }

    public Room[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Room[][] gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setPlayerPosition(int x, int y) {
        player.setPosition(x, y);

    }

    public Player getPlayer() {
        return player;
    }

    public Wumpus setWumpusPosition(int x, int y) {
        wumpus = new Wumpus(x, y);
        return wumpus;
    }

    public boolean isWall(char direction) {
        switch (direction) {
            case 'W':
                return player.x == 0;
            case 'E':
                return player.x == 4;
            case 'N':
                return player.x == 0;
            case 'S':
                return player.x == 4;
            default:
                throw new IllegalArgumentException("Invalid direction " + direction);
        }
    }

    public void shoot(char direction) {
        if (isWall(direction)) {
            player.die();
        } else {

            switch (direction) {
                case 'W':
                    if (player.y == wumpus.y && wumpus.x < player.x) wumpus.die();
                    break;
                case 'E':
                    if (player.y == wumpus.y && wumpus.x > player.x) wumpus.die();
                    break;
                case 'N':
                    if (player.y < wumpus.y && wumpus.x == player.x) wumpus.die();
                    break;
                case 'S':
                    if (player.y > wumpus.y && wumpus.x == player.x) wumpus.die();
                    break;
            }
        }
    }
}
