package dev.shft.packets;

public class Admin {
	public static enum Action {
		START_GAME,
		STOP_GAME,
		RESTART_GAME,
		SHUTDOWN_SERVER
	}

	private Action action;

	public Admin(Action action) {
		this.action = action;
	}
	
}
