package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.ServerNetwork;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.communication.additionalAttachements.SpecificServerGame;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;
import io.potatoBlindTest.service.Network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class CreateGame implements ClientMessageHandler<Player> {

    // The current clientHandler containes all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandler) {
        System.out.println("Handling create game ...");
        // Create a ServerGame :
        // - add it to the CopyOnWriteArrayList<ServerGame>
        // - send the new ServerSocket 's ServerGame to the client
        Message messageToSend;
        ServerGame newServerGame = Network.createServerGame(clientHandler.getServerNetwork().serverGames);
        newServerGame.setStatesGame(StatesGame.CREATED);

        if (newServerGame == null) {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        } else {
            clientHandler.getServerNetwork().addServerGameToList(newServerGame);

            try{
                SpecificServerGame specificServerGame = new SpecificServerGame(newServerGame.getServerSocket().getLocalPort(),
                        InetAddress.getLocalHost().getHostAddress());
                messageToSend = new MessageAttachment<SpecificServerGame>(ServerMessageType.OK.getValue(), specificServerGame);
            } catch (UnknownHostException e) {
                System.out.println("Unknown host name ... ");
                e.printStackTrace();
                messageToSend = new Message(ServerMessageType.ERROR_SERVER.getValue());
            }
        }

        System.out.println("Message to send : " + messageToSend.getCode() + ((MessageAttachment)messageToSend).getAttachment());

        return messageToSend;
    }
}
