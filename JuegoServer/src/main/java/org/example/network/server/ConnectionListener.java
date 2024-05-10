package org.example.network.server;

import java.util.List;

public interface ConnectionListener {
	void init();
	void stop();
	List<String> listConnections();
}
