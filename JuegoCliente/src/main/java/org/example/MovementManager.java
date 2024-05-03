package org.example;

import org.example.Datatypes.Vector2;

public class MovementManager {
	private Vector2 keys = new Vector2();
	private final Player player;
	private double speed = 3;
	
	public MovementManager(Player player) {
		this.player = player;
	}

	private class KeyHandler extends Thread {
		
		@Override
		public void run() {
			while (true) {
				
			}
		}
	}
	
}
