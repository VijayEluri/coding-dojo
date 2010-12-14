package no.iterate.tech.wumpus;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	enum SquareType {
		PATH, WALL, PIT, BAT
	};

	boolean alive = true;
	SquareType[][] maze;
	Point playerPosition;
	Point arrowPosition = null;
	int turn;
	boolean running = true;
	List<String> messages = new ArrayList<String>();

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
			turn++;
			return true;
		default:
			return false;
		}
	}

	private void pickUpArrowIfInSameSquareAsPlayer() {
		if (playerPosition.equals(arrowPosition)) {
			arrowPosition = null;
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
		turn++;
		flyingArrow(x, y);
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
			return flyingArrow(dx, dy);
		default:
			dieIfArrowBouncedBackAtPlayer();
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
		turn++;
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
					} else if (!playerHasArrow() && arrowPosition.x == i && arrowPosition.y == j) {
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
			return shootNorth() ? "Shooting north!" : "You don't have the arrow!";
		}
		if (command.equals("SS")) {
			return shootSouth() ? "Shooting south!" : "You don't have the arrow!";
		}

		if (command.equals("R")) {
			rest();
			return "Zzz ...";
		}
		if (command.equals("P")) {
			return getTurnDisplay() + ",  " + getInventoryDisplay() + "\n" + printMaze();
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

}
