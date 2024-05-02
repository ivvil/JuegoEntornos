package org.example.Grid;

import org.example.Player;

public interface Tile {
	/**
	 * Symbol that reptesents the tile
	 *
	 */
	char getSymbol();

	boolean isInteractable();
	boolean isWalkable();

	void interact(Player player);
}
