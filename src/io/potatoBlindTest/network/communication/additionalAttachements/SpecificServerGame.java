package io.potatoBlindTest.network.communication.additionalAttachements;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;

public class SpecificServerGame implements Serializable {

    private int port;
    private InetAddress inetAdress;

    public SpecificServerGame(int port, InetAddress inetAddress) {
        this.port = port;
        this.inetAdress = inetAddress;
    }

    public int getPort() {
        return port;
    }

    public InetAddress getInetAdress() {
        return inetAdress;
    }
}
