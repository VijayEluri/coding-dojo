package wumpus;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoomTest {

        Room east = new Room();
        Room south = new Room();
        Room north = new Room();
        Room west = new Room();
        Room room = new Room(east, west, north, south);
        Room roomWithALLTHEWALLS = new Room(Room.WALL, Room.WALL, Room.WALL, Room.WALL);


    @Test
    public void navgiation(){

        assertSame(east, room.goEast());
        assertSame(west, room.goWest());
        assertSame(south, room.goSouth());
        assertSame(north, room.goNorth());
    }

    @Test
    public void wumpus_movement_east(){
        Room wumpusStartRoom = room;

        wumpusStartRoom.placeWumpus();
        Room wumpusEndRoom = wumpusStartRoom.goEast();

        assertTrue("we moved east so it shall be in the east room", wumpusEndRoom.hasWumpus());
        assertFalse("Wumpus hsall be moved, not cloned", wumpusStartRoom.hasWumpus());
    }


    @Test
    public void wumpus_movement_west(){
        Room wumpusStartRoom = room;

        wumpusStartRoom.placeWumpus();
        Room wumpusEndRoom = wumpusStartRoom.goWest();

        assertTrue("we moved W west so it shall be in the west room", wumpusEndRoom.hasWumpus());
        assertFalse("Wumpus hsall be moved, not cloned", wumpusStartRoom.hasWumpus());
    }

    @Test
    public void hero_movement_north(){
        Room heroStartRoom = room;

        heroStartRoom.placeHero();
        Room heroEndRoom = heroStartRoom.goNorth();

        assertTrue("we moved H north so it shall be in the north room", heroEndRoom.hasHero());
        assertFalse("Hero hsall be moved, not cloned", heroStartRoom.hasHero());
    }

    @Test
    public void cannot_move_into_wall_north(){
        Room heroStartRoom = roomWithALLTHEWALLS;

         heroStartRoom.placeHero();
         Room heroEndRoom = heroStartRoom.goNorth();

        assertTrue(heroStartRoom.north.isWall());
        assertNull("Cannot move into a wall!", heroEndRoom);
    }
    @Test
    public void cannot_move_into_wall_south(){
        Room heroStartRoom = roomWithALLTHEWALLS;

         heroStartRoom.placeHero();
         Room heroEndRoom = heroStartRoom.goSouth();

        assertTrue(heroStartRoom.south.isWall());
        assertNull("Cannot move into a wall!", heroEndRoom);
    }
    @Test
    public void cannot_move_into_wall_east(){
        Room heroStartRoom = roomWithALLTHEWALLS;

         heroStartRoom.placeHero();
         Room heroEndRoom = heroStartRoom.goEast();

        assertTrue(heroStartRoom.east.isWall());
        assertNull("Cannot move into a wall!", heroEndRoom);
    }
    @Test
    public void cannot_move_into_wall_west(){
        Room heroStartRoom = roomWithALLTHEWALLS;     //haha you are trapped

         heroStartRoom.placeHero();
         Room heroEndRoom = heroStartRoom.goWest();

        assertTrue(heroStartRoom.west.isWall());
        assertNull("Cannot move into a wall!", heroEndRoom);
    }

    @Test
    public void movePlayerIntoPitKillsHimDead() throws Exception {
        Room pitRoom = new Room();
        pitRoom.setPit(true);

        room.north = pitRoom;

        room.placeHero();

        Room destination = room.goNorth();

        assertEquals(Room.DEAD, destination);

    }

    @Test
    public void movePlayerNearPitWindIsBlowing() throws Exception {
        Room pitRoom = new Room();
        pitRoom.setPit(true);

        Room windRoom = new Room();
        windRoom.north = pitRoom;
        room.north = windRoom;

        Room destination = room.goNorth();
        assert(destination.isWindy());
    }
}
