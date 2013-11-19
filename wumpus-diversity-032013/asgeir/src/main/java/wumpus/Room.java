package wumpus;

public class Room {

    static int max;

    public static final Room WALL = new Room(true);
    public static final Room DEAD = new Room();

    Room east = WALL;
    Room west = WALL;
    Room north = WALL;
    Room south = WALL;


    private final int id;
    boolean wall;
    private boolean wumpus;
    private boolean hero;
    private boolean pit;
    private boolean wind;


    public Room(final Room east, final Room west, final Room north, final Room south) {
        this();
        this.east = east;
        this.west = west;
        this.north = north;
        this.south = south;
    }

    public Room(final boolean isWall) {
        this();
        this.wall = isWall;
    }

    public Room() {
        this.id = max++;
    }

    public Room goEast() {
        if (east == Room.WALL) {
            return null;
        }
        return moveInhabitantIfAny(east);
    }

    public Room goWest() {
        if (west == Room.WALL) {
            return null;
        }
        return moveInhabitantIfAny(west);
    }

    public Room goNorth() {
        if (north == Room.WALL) {
            return null;
        }

        return moveInhabitantIfAny(north);
    }

    public Room goSouth() {
        if (south == Room.WALL) {
            return null;
        }

        return moveInhabitantIfAny(south);
    }

    public Boolean isWall() {
        return wall;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }

    public void placeWumpus() {
        this.wumpus = true;
    }

    public boolean hasWumpus() {
        return wumpus;
    }

    private Room moveInhabitantIfAny(final Room destination) {
        if (wumpus) {
            destination.placeWumpus();
            wumpus = false;
        }
        if (hero) {
            if (destination.isPit()) {
                return DEAD;
            }
            destination.placeHero();
            hero = false;
        }
        return destination;
    }

    public void placeHero() {
        this.hero = true;
    }

    public boolean hasHero() {
        return hero;
    }

    public void setPit(final boolean pit) {
        this.pit = pit;
        this.north.wind = true;
        this.south.wind = true;
        this.east.wind = true;
        this.west.wind = true;
    }

    public boolean isPit() {
        return pit;
    }

    public boolean isWindy() {
        return wind;
    }
}
