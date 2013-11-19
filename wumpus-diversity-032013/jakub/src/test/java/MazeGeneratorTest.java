import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


public class MazeGeneratorTest {


    static class Maze {
        private final int width;
        private final int height;
        private Position player;
        private Position wumpus;

        Maze(int width, int height) {
            this.width = width;
            this.height = height;
            player = new Position(0,0);
            wumpus = new Position(1,1);

        }

        private void movePlayer(Direction direction) {
            moveWumpusRandomly();
            switch (direction) {
                case WEST:
                    player = new Position(player.x - 1, player.y);
                    break;
                case EAST:
                    player = new Position(player.x + 1, player.y);
                    break;
                case NORTH:
                    player = new Position(player.x, player.y - 1);
                    break;
                case SOUTH:
                    player = new Position(player.x, player.y + 1);
                    break;
            }

            if(wumpus.equals(player)) {
                System.out.println("Wumps has eaten you!!!");
                System.exit(0);
            }
            print();
        }

        private void moveWumpusRandomly() {
            Random random = new Random();
            int i = random.nextInt(4);


            if(i == 1) {
                wumpus = new Position(wumpus.x + 1, wumpus.y);
            }

            if(i == 2) {
                wumpus = new Position(wumpus.x + -1, wumpus.y);
            }

            if(i == 3) {
                wumpus = new Position(wumpus.x, wumpus.y + 1);
            }

            if(i == 4) {
                wumpus = new Position(wumpus.x , wumpus.y -1);
            }



        }

        public void print() {
            printRoom(0, 0);
        }

        public void printRoom(int x, int y) {
            System.out.print("|");
            if (player.x == x && player.y == y) {
                System.out.print("P");
            } else if (wumpus.x == x && wumpus.y == y) {
                System.out.print("W");
            } else {
                System.out.print(" ");
            }
            if (x < this.width) {
                printRoom(x + 1, y);
            } else {
                System.out.println("|");
                if (y < this.height) {
                    printRoom(0, y + 1);
                } else {
                    System.out.println("");
                    return;
                }
            }
        }

        public Position getPlayer() {
            return player;
        }

        public boolean playerWumpusNextToEachOther() {
            return false;  //To change body of created methods use File | Settings | File Templates.
        }
    }

    Maze maze;

    @Before
    public void setUp() {
        maze = new Maze(10, 10);
    }

    @Test
    public void drawMaze() {
    }


    @Test
    public void testMoveSouth() {
        maze.movePlayer(Direction.SOUTH);
        assertEquals(maze.getPlayer().y, 1);
    }

    @Test
    public void testDanceAroundTheWumpus() throws Exception {
        maze.movePlayer(Direction.SOUTH);
        maze.movePlayer(Direction.SOUTH);
        maze.movePlayer(Direction.EAST);
        maze.movePlayer(Direction.EAST);
        maze.movePlayer(Direction.NORTH);
        maze.movePlayer(Direction.NORTH);
        maze.movePlayer(Direction.WEST);
        maze.movePlayer(Direction.WEST);

        assertEquals(maze.getPlayer().y, 0);
        assertEquals(maze.getPlayer().x, 0);

    }

    @After
    public void tearDown() {
        maze.print();
    }

    @Test
    public void smellWumpus() throws Exception {
       maze.player = new Position(0,0);
        maze.wumpus = new Position(1,0);
       assertTrue(maze.playerWumpusNextToEachOther());
    }




}
