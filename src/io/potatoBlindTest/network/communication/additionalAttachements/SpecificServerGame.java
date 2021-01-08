package io.potatoBlindTest.network.communication.additionalAttachements;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SpecificServerGame implements Serializable {

    private int port;
    private String ipAddress;

    public SpecificServerGame(int port, String ipAddress) {
        this.port = port;
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String toString() {
        return "SpecificServerGame{" +
                "port=" + port +
                ", inetAdress=" + ipAddress +
                '}';
    }
}
