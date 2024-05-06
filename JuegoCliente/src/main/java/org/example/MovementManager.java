package org.example;

import javax.swing.*;

import org.example.datatypes.Vector2;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class MovementManager {
    private Vector2 velocity = new Vector2();
    private final Player player;

    public MovementManager(Player player) {
        this.player = player;
    }

    public void update(float deltaTime) {
        Vector2 acceleration = new Vector2();
        if (Input.isKeyPressed(KeyEvent.VK_W)) {
            acceleration.x += 1;
        }
        if (Input.isKeyPressed(KeyEvent.VK_S)) {
            acceleration.x -= 1;
        }
        if (Input.isKeyPressed(KeyEvent.VK_A)) {
            acceleration.y += 1;
        }
        if (Input.isKeyPressed(KeyEvent.VK_D)) {
            acceleration.y -= 1;
        }
        acceleration.normalize().scale(player.getAcceleration());

        Vector2 friction = velocity.copy().scale(-player.getFriction());

        velocity.add(acceleration).add(friction).clamp(player.getMaxSpeed());

        player.setPosition(player.getPosition().add(velocity.x * deltaTime, velocity.y * deltaTime));
    }
}

class Input {
    private static final Map<Integer, Boolean> keyState = new HashMap<>();

	public static void initialize(JComponent component) {
		InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = component.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "press_w");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "release_w");
		actionMap.put("press_w", new KeyAction(KeyEvent.VK_W, true));
		actionMap.put("release_w", new KeyAction(KeyEvent.VK_W, false));

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "press_s");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "release_s");
		actionMap.put("press_s", new KeyAction(KeyEvent.VK_S, true));
		actionMap.put("release_s", new KeyAction(KeyEvent.VK_S, false));

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "press_a");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "release_a");
		actionMap.put("press_a", new KeyAction(KeyEvent.VK_A, true));
		actionMap.put("release_a", new KeyAction(KeyEvent.VK_A, false));

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "press_d");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "release_d");
		actionMap.put("press_d", new KeyAction(KeyEvent.VK_D, true));
		actionMap.put("release_d", new KeyAction(KeyEvent.VK_D, false));
	}


    public static boolean isKeyPressed(int keyCode) {
        Boolean pressed = keyState.getOrDefault(keyCode, false);
        return pressed;
    }

    private static class KeyAction extends AbstractAction {
        private final int keyCode;
        private final boolean pressed;

        public KeyAction(int keyCode, boolean pressed) {
            this.keyCode = keyCode;
            this.pressed = pressed;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            keyState.put(keyCode, pressed);

			System.out.println(keyState);

        }
    }
}
