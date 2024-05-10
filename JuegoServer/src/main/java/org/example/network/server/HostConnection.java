package org.example.network.server;

import org.example.network.ConnectionStatus;
import org.example.network.ConnectionType;

public interface HostConnection {
	String getHost();
    boolean close();
	ConnectionStatus exangeStatus();
	ConnectionType getType();
}
