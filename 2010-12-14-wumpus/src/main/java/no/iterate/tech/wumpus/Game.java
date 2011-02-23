package no.iterate.tech.wumpus;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
	PrintWriter recorder;

	Point wumpusPosition = null;
	Point arrowPosition = null;

	List<String> messages = new ArrayList<String>();

	Random random = new Random();

	public static void main(String[] args) {
		Game game = createNewGameWithA10x10Maze();
		game.run();
	}

	private static Game createNewGameWithA10x10Maze() {
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

	public Game() {
		this(1, 1);
	}

	public Game(int mazeWidth, int mazeHeight) {
		this(mazeWidth, mazeHeight, new Point(0, 0));
	}

	public Game(int mazeWidth, int mazeHeight, Point playerPosition) {
		maze = new SquareType[mazeWidth][mazeHeight];
		for (int x = 0; x < mazeWidth; x++) {
			for (int y = 0; y < mazeHeight; y++) {
				maze[x][y] = SquareType.PATH;
			}
		}
		this.playerPosition = playerPosition;
	}

	public void createWall(int x, int y) {
		maze[x][y] = SquareType.WALL;
	}

	public void createPit(int x, int y) {
		maze[x][y] = SquareType.PIT;
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

	public void run() {
		System.out.println("Welcome to Hunt the Wumpus! Play!");
		startRecording();
		while (running && alive) {
			System.out.print("> ");
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			System.out.println(process(command).trim());
			while (!messages.isEmpty()) {
				System.out.println(messages.remove(0));
			}
		}
		stopRecording();
	}

	private void startRecording() {
		try {
			recorder = new PrintWriter(new File("lastGame.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		recorder.close();

	}

	protected boolean over() {
		return !alive;
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
			return displayTurnInventoryAndMaze();
		}
		if (command.equals("Q")) {
			running = false;
			return "Bye!";
		}

		return "Can't understand you. Try ([S]{E,W,N,S}|R|P|Q) ;-)";
	}

	private String displayTurnInventoryAndMaze() {
		return getTurnDisplay() + ",  " + getInventoryDisplay() + "\n"
				+ getMazeDisplay();
	}

	protected void tick() {
		turn++;
		moveWumpus();
		if (wumpusPosition != null && wumpusPosition.equals(playerPosition)) {
			messages.add("The wumpus found you and killed you!");
			alive = false;
		}
		if (recorder != null) {
			recorder.print(displayTurnInventoryAndMaze());
		}
	}

	protected void rest() {
		tick();
	}

	protected boolean goEast() {
		return go(1, 0);
	}

	protected boolean goWest() {
		return go(-1, 0);
	}

	protected boolean goNorth() {
		return go(0, -1);
	}

	protected boolean goSouth() {
		return go(0, 1);
	}

	private boolean go(int dx, int dy) {
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

	protected boolean playerHasArrow() {
		return arrowPosition == null;
	}

	protected boolean shootEast() {
		return shoot(1, 0);
	}

	protected boolean shootWest() {
		return shoot(-1, 0);
	}

	protected boolean shootNorth() {
		return shoot(0, -1);
	}

	protected boolean shootSouth() {
		return shoot(0, 1);
	}

	private boolean shoot(int dx, int dy) {
		if (!playerHasArrow()) {
			return false;
		}
		arrowPosition = (Point) playerPosition.clone();
		flyingArrow(dx, dy);
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

	private String getTurnDisplay() {
		return "Turn: " + turn;
	}

	private String getInventoryDisplay() {
		if (playerHasArrow()) {
			return "Inventory: +";
		} else {
			return "Inventory: empty";
		}
	}

	protected String getMazeDisplay() {
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

	private void addBorderRow(StringBuilder sb) {
		for (int i = 0; i < maze.length + 2; i++) {
			sb.append("#");
		}
		sb.append("\n");
	}

	private boolean arrowIsAtPoint(Point point) {
		return arrowPosition != null && arrowPosition.equals(point);
	}

	private boolean wumpusIsAtPoint(Point point) {
		return wumpusPosition != null && wumpusPosition.equals(point);
	}

}
