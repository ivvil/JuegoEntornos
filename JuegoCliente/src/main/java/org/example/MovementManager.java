package org.example;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.example.datatypes.Vector2;

public class MovementManager {
	private Vector2 keys = new Vector2();
	private final Player player;
	private double speed = 3;
	
	public MovementManager(Player player, Game game) {
		System.out.println("Inst");

		this.player = player;
		game.addKeyListener(new KeyHandler());		
	}

	private class KeyHandler implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("test");

			int keyCode = e.getKeyCode();
			double acceleration = player.getAcceleration();
			switch (keyCode) {
			case KeyEvent.VK_W:
				keys.x += acceleration;
				break;
			case KeyEvent.VK_S:
				keys.x -= acceleration;
				break;
			case KeyEvent.VK_A:
				keys.y += acceleration;
				break;
			case KeyEvent.VK_D:
				keys.y -= acceleration;
				break;
			default:
				break;
			}

			Vector2.clamp(keys, player.getMaxSpeed());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			double firction = player.getFriction();
			switch (keyCode) {
			case KeyEvent.VK_W:
				keys.x += firction;
				break;
			case KeyEvent.VK_S:
				keys.x -= firction;
				break;
			case KeyEvent.VK_A:
				keys.y += firction;
				break;
			case KeyEvent.VK_D:
				keys.y -= firction;
				break;
			default:
				break;
			}

			Vector2.clamp(keys, player.getMaxSpeed());
		}
		
	}
	
}
