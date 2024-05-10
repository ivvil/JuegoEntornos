package org.example.network.packets;

public class Admin {
	public static enum Action {
		START_GAME,
		STOP_GAME,
		RESTART_GAME,
		SHUTDOWN_SERVER
	}

	private final Action action;

	public Admin(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}
}
