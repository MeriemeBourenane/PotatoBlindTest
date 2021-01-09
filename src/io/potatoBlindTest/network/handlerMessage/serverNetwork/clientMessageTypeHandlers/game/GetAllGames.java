package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.Game;
import io.potatoBlindTest.gameEngine.ListGames;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.ServerNetwork;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

public class GetAllGames implements ClientMessageHandler<Player> {

    // The current clientHandler contains all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(Player dataMessage, ClientHandler clientHandler) {
        // Return all server games formatted in a Message List
        //  - iterate on ServerGames
        //  - get hashMap of mapPlayerSocket
        //  - extract these information and put it in a Game object
        //  - add this Game object to a ListGame
        Message messageToSend;
        ListGames listGames = new ListGames();

        for(ServerGame serverGame : clientHandler.getServerNetwork().getServerGames()) {
            if (serverGame.getCreator() != null && serverGame.getStatesGame() == StatesGame.INIT) {
                try {
                    Game game = new Game(serverGame.getCreator().getName(),
                            serverGame.getMapPlayerClientHandler().size(),
                            InetAddress.getLocalHost().getHostAddress(),
                            serverGame.getServerSocket().getLocalPort());
                    listGames.addGameToList(game);
                    System.out.println("A game : " + game);
                } catch (UnknownHostException e) {
                    System.out.println("[GetAllGames] uncknown host ...");
                    e.printStackTrace();
                }
            }
        }
        messageToSend = new MessageAttachment<ListGames>(ServerMessageType.OK.getValue(), listGames);

        return messageToSend;
    }
}
