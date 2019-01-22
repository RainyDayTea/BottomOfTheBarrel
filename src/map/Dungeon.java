package map;

import entity.Player;
import framework.RenderedObject;
import framework.geom.Rectangle;
import game.GameAreaPanel;

import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon {

	private static final Rectangle ROOM_BOUNDS = new Rectangle(-448, -448, 448, 448);
	private GameAreaPanel parent;
	private HashMap<Integer, HashMap<Integer, Room>> rooms;
	private int row, col;

	public Dungeon(GameAreaPanel gamePanel) {
		this.parent = gamePanel;
		this.rooms = new HashMap<>();
		this.row = 0;
		this.col = 0;
	}

	public Room getCurrentRoom() {
		if (rooms.get(row) != null) {
			return rooms.get(row).get(col);
		}
		return null;
	}

	public Room getRoom(int row, int col) {
		if (rooms.get(row) != null) {
			return rooms.get(row).get(col);
		}
		return null;
	}

	public boolean addRoom(int row, int col, Room newRoom) {
		if (rooms.get(row) == null) {
			rooms.put(row, new HashMap<>());
		}

		if (rooms.get(row).get(col) != null) return false;
		rooms.get(row).put(col, newRoom);
		return true;
	}

	public Room shiftWest() {
		this.col--;
		if (getRoom(row, col) == null) {
			addRoom(row, col, new Room(this, ROOM_BOUNDS));
		}
		return getRoom(row, col);
	}

	public Room shiftEast() {
		this.col++;
		if (getRoom(row, col) == null) {
			addRoom(row, col, new Room(this, ROOM_BOUNDS));
		}
		return getRoom(row, col);
	}

	public Room shiftNorth() {
		this.row--;
		if (getRoom(row, col) == null) {
			addRoom(row, col, new Room(this, ROOM_BOUNDS));
		}
		return getRoom(row, col);
	}

	public Room shiftSouth() {
		this.row++;
		if (getRoom(row, col) == null) {
			addRoom(row, col, new Room(this, ROOM_BOUNDS));
		}
		return getRoom(row, col);
	}

	public void onExitRoom(String direction, ArrayList<RenderedObject> savedObjects) {
		Room newRoom = null;
		if (direction.equals("north")) {
			newRoom = shiftNorth();
		} else if (direction.equals("east")) {
			newRoom = shiftEast();
		} else if (direction.equals("south")) {
			newRoom = shiftSouth();
		} else if (direction.equals("west")) {
			newRoom = shiftWest();
		}

		if (newRoom == null) {
			throw new NullPointerException("onExitRoom(): newRoom not found");
		}

		// Find the player and place it in the new room
		for (RenderedObject obj : savedObjects) {
			if (obj instanceof Player) {
				newRoom.place(obj, true);
			}
		}
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
}
