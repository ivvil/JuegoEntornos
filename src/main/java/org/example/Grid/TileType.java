package org.example.Grid;

import org.example.Player;

public enum TileType implements Tile {
	FLOOR(' ', false, true) {
        @Override
			public void interact(Player player) {}
    },
    WALL('#', false, false) {
        @Override
			public void interact(Player player) {}
    },
    DOOR('+', true, false) {
        @Override
			public void interact(Player player) {
        }
    },
    KEY('k', true, false) {
        @Override
			public void interact(Player player) {
			player.inventory.add("key");
        }
	};

    TileType(char symbol, boolean interactable, boolean walkable) {
        this.symbol = symbol;
        this.interactable = interactable;
        this.walkable = walkable;
    }

    private final char symbol;
    private final boolean interactable;
    private final boolean walkable;

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public boolean isInteractable() {
        return interactable;
    }

    @Override
    public boolean isWalkable() {
        return walkable;
    }
	
    @Override
    public abstract void interact(Player player);

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}
