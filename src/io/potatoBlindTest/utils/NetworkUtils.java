package io.potatoBlindTest.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class NetworkUtils {

    public static ServerSocket create() throws IOException {
        for (int port = 20_001; port < 20_006; ++port) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        // if the program gets here, no port in the range was found
        throw new IOException("No free port found ...");
    }
}
