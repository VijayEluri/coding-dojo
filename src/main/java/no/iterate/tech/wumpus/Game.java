package no.iterate.tech.wumpus;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

	enum SquareType {
		PATH, WALL, PIT, BAT
	};

	boolean running = true;
	int turn = 0;
	SquareType[][] maze;

	Point playerPosition;
	boolean alive = true;

	Point wumpusPosition = null;
	Point arrowPosition = null;

	List<String> messages = new ArrayList<String>();

	Random random = new Random();

	public Game() {
		this(1, 1);
	}

	public Game(int x, int y) {
		this(x, y, new Point(0, 0));
	}

	public Game(int x, int y, Point playerPosition) {
		maze = new SquareType[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				maze[i][j] = SquareType.PATH;
			}
		}
		this.playerPosition = playerPosition;
	}

	public boolean go(int dx, int dy) {
		SquareType squareType;
		try {
			squareType = maze[playerPosition.x + dx][playerPosition.y + dy];
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		switch (squareType) {
		case PIT:
			messages.add("You fell in a pit, game over ^^");
			alive = false;
		case PATH:
			playerPosition.x += dx;
			playerPosition.y += dy;
			pickUpArrowIfInSameSquareAsPlayer();
			listenForWind();
			smellForWumpus();
			beKilledByWumpusIfInSameSquare();
			tick();
			return true;
		default:
			return false;
		}
	}

	private void beKilledByWumpusIfInSameSquare() {
		if (playerPosition.equals(wumpusPosition)) {
			messages.add("You walked into the Wumpus. It kills you!");
			alive = false;
		}
	}

	private void pickUpArrowIfInSameSquareAsPlayer() {
		if (playerPosition.equals(arrowPosition)) {
			arrowPosition = null;
			messages.add("You picked up the arrow");
		}
	}

	private void smellForWumpus() {
		if (wumpusInEast() || wumpusInWest() || wumpusInNorth()
				|| wumpusInSouth()) {
			messages.add("You smell the Wumpus");
		}
	}

	private boolean wumpusInSouth() {
		Point checkForWumpus = new Point(playerPosition.x, playerPosition.y + 1);
		return checkForWumpus.equals(wumpusPosition);
	}

	private boolean wumpusInNorth() {
		Point checkForWumpus = new Point(playerPosition.x, playerPosition.y - 1);
		return checkForWumpus.equals(wumpusPosition);
	}

	private boolean wumpusInWest() {
		Point checkForWumpus = new Point(playerPosition.x - 1, playerPosition.y);
		return checkForWumpus.equals(wumpusPosition);
	}

	private boolean wumpusInEast() {
		Point checkForWumpus = new Point(playerPosition.x + 1, playerPosition.y);
		return checkForWumpus.equals(wumpusPosition);
	}

	private void listenForWind() {
		if (pitInEast() || pitInWest() || pitInNorth() || pitInSouth()) {
			messages.add("You hear wind");
		}
	}

	private boolean pitInEast() {
		try {
			return maze[playerPosition.x + 1][playerPosition.y] == SquareType.PIT;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean pitInWest() {
		try {
			return maze[playerPosition.x - 1][playerPosition.y] == SquareType.PIT;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean pitInNorth() {
		try {
			return maze[playerPosition.x][playerPosition.y - 1] == SquareType.PIT;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean pitInSouth() {
		try {
			return maze[playerPosition.x][playerPosition.y + 1] == SquareType.PIT;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	public boolean goNorth() {
		return go(0, -1);
	}

	public boolean goEast() {
		return go(1, 0);
	}

	public boolean goWest() {
		return go(-1, 0);
	}

	public boolean goSouth() {
		return go(0, 1);
	}

	public boolean over() {
		return !alive;
	}

	public boolean shootNorth() {
		return shoot(0, -1);
	}

	public boolean shootSouth() {
		return shoot(0, 1);
	}

	public boolean shootWest() {
		return shoot(-1, 0);
	}

	public boolean shootEast() {
		return shoot(1, 0);
	}

	private boolean shoot(int x, int y) {
		if (!playerHasArrow()) {
			return false;
		}
		arrowPosition = (Point) playerPosition.clone();
		flyingArrow(x, y);
		tick();
		return true;
	}

	private boolean flyingArrow(int dx, int dy) {
		SquareType squareType;
		try {
			squareType = maze[arrowPosition.x + dx][arrowPosition.y + dy];
		} catch (ArrayIndexOutOfBoundsException e) {
			dieIfArrowBouncedBackAtPlayer();
			return false;
		}

		switch (squareType) {
		case PIT:
		case PATH:
			arrowPosition.x += dx;
			arrowPosition.y += dy;
			return !arrowHitWumpus() && flyingArrow(dx, dy);
		default:
			dieIfArrowBouncedBackAtPlayer();
			return false;
		}

	}

	private boolean arrowHitWumpus() {
		if (arrowPosition.equals(wumpusPosition)) {
			messages.add("You shot the Wumpus! You WIN!");
			running = false;
			return true;
		} else {
			return false;
		}
	}

	private void dieIfArrowBouncedBackAtPlayer() {
		if (arrowPosition.equals(playerPosition)) {
			alive = false;
			messages.add("Arrow bounced on wall. You die!");
		}
	}

	public void createWall(int x, int y) {
		maze[x][y] = SquareType.WALL;
	}

	public void createPit(int x, int y) {
		maze[x][y] = SquareType.PIT;
	}

	public void rest() {
		tick();
	}

	public boolean playerHasArrow() {
		return arrowPosition == null;
	}

	public String printMaze() {
		StringBuilder sb = new StringBuilder();
		addBorderRow(sb);
		for (int j = 0; j < maze[0].length; j++) {
			sb.append("#");
			for (int i = 0; i < maze.length; i++) {
				switch (maze[i][j]) {
				case WALL:
					sb.append("x");
					break;
				case PIT:
					sb.append("o");
					break;
				case PATH:
					if (playerPosition.x == i && playerPosition.y == j) {
						sb.append("@");
					} else if (wumpusIsAtPoint(new Point(i, j))) {
						sb.append("W");
					} else if (arrowIsAtPoint(new Point(i, j))) {
						sb.append("+");
					} else {
						sb.append(" ");
					}
					break;
				default:
					break;
				}
			}
			sb.append("#\n");
		}
		addBorderRow(sb);
		return sb.toString();
	}

	private boolean arrowIsAtPoint(Point point) {
		return arrowPosition != null && arrowPosition.equals(point);
	}

	private boolean wumpusIsAtPoint(Point point) {
		return wumpusPosition != null && wumpusPosition.equals(point);
	}

	private void addBorderRow(StringBuilder sb) {
		for (int i = 0; i < maze.length + 2; i++) {
			sb.append("#");
		}
		sb.append("\n");
	}

	public static void main(String[] args) {
		Game game = createNewGameWithAMaze();
		game.run();
	}

	private void run() {
		System.out.println("Welcome to Hunting the Wumpus! Play!");
		while (running && alive) {
			System.out.print("> ");
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			System.out.println(process(command).trim());
			while (!messages.isEmpty()) {
				System.out.println(messages.remove(0));
			}
		}
	}

	private static Game createNewGameWithAMaze() {
		Game game = new Game(10, 10);
		game.createWall(0, 1);
		game.createWall(0, 2);
		game.createWall(0, 3);
		game.createWall(0, 4);
		game.createWall(0, 5);
		game.createWall(2, 3);
		game.createWall(2, 4);
		game.createWall(2, 5);
		game.createWall(2, 6);
		game.createWall(3, 3);
		game.createWall(3, 4);
		game.createWall(3, 5);
		game.createWall(3, 6);
		game.createWall(4, 3);
		game.createWall(4, 4);
		game.createWall(4, 5);
		game.createWall(4, 6);
		game.createPit(1, 4);
		game.createPit(7, 5);
		game.addWumpus(new Point(6, 3));
		return game;
	}

	public String process(String command) {
		if (command.equals("E")) {
			return goEast() ? "Moving east" : "You can't go east from here";
		}
		if (command.equals("W")) {
			return goWest() ? "Moving west" : "You can't go west from here";
		}
		if (command.equals("N")) {
			return goNorth() ? "Moving north" : "You can't go north from here";
		}
		if (command.equals("S")) {
			return goSouth() ? "Moving south" : "You can't go south from here";
		}

		if (command.equals("SE")) {
			return shootEast() ? "Shooting east!" : "You don't have the arrow!";
		}
		if (command.equals("SW")) {
			return shootWest() ? "Shooting west!" : "You don't have the arrow!";
		}
		if (command.equals("SN")) {
			return shootNorth() ? "Shooting north!"
					: "You don't have the arrow!";
		}
		if (command.equals("SS")) {
			return shootSouth() ? "Shooting south!"
					: "You don't have the arrow!";
		}

		if (command.equals("R")) {
			rest();
			return "Zzz ...";
		}
		if (command.equals("P")) {
			return getTurnDisplay() + ",  " + getInventoryDisplay() + "\n"
					+ printMaze();
		}
		if (command.equals("Q")) {
			running = false;
			return "Bye!";
		}

		return "Can't understand you. Try ([S]{E,W,N,S}|R|P|Q) ;-)";
	}

	private String getInventoryDisplay() {
		if (playerHasArrow()) {
			return "Inventory: +";
		} else {
			return "Inventory: empty";
		}
	}

	private String getTurnDisplay() {
		return "Turn: " + turn;
	}

	public void addWumpus(Point wumpusPosition) {
		try {
			if (maze[wumpusPosition.x][wumpusPosition.y] != SquareType.PATH) {
				throw new IllegalStateException("Square is not a path");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalStateException("Wumpus is outside the maze", e);
		}
		this.wumpusPosition = wumpusPosition;
	}

	public void tick() {
		turn++;
		moveWumpus();
	}

	private void moveWumpus() {
		if (wumpusPosition == null) {
			return;
		}
		int direction = random.nextInt(4);
		switch (direction) {
		case 0:
			moveWumpusEast();
			break;
		case 1:
			moveWumpusWest();
			break;
		case 2:
			moveWumpusNorth();
			break;
		case 3:
			moveWumpusSouth();
			break;
		default:
			throw new RuntimeException("This is not possible!");
		}

	}

	private boolean moveWumpusSouth() {
		try {
			if (maze[wumpusPosition.x][wumpusPosition.y + 1] == SquareType.PATH) {
				wumpusPosition.y++;
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	private boolean moveWumpusNorth() {
		try {
			if (maze[wumpusPosition.x][wumpusPosition.y - 1] == SquareType.PATH) {
				wumpusPosition.y--;
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	private boolean moveWumpusWest() {
		try {
			if (maze[wumpusPosition.x - 1][wumpusPosition.y] == SquareType.PATH) {
				wumpusPosition.x--;
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

	private boolean moveWumpusEast() {
		try {
			if (maze[wumpusPosition.x + 1][wumpusPosition.y] == SquareType.PATH) {
				wumpusPosition.x++;
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return false;
	}

}
