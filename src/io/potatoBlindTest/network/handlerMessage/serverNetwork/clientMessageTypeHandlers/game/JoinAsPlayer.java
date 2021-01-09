package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game;

import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.gameEngine.Player;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.notification.SubjectClientHandler;

import java.util.Random;

public class JoinAsPlayer extends SubjectClientHandler implements ClientMessageHandler<NamePlayer> {

    // The current clientHandler containes all clientHandlers of the ServerNetwork and all the ServerGames
    @Override
    public Message handle(NamePlayer dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling join as player ...");
        // Join as player :
        // - add it and his socket to the mapPlayerSocket if the state game is INIT && name of player not already given
        // - answer to the client with name unchanged or changed
        // - notify the creator with the NamePlayer
        Message messageToSend;
        Player player = new Player(dataMessage.getName());
        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getStatesGame() == StatesGame.INIT) {

            // while the name of the player is already given
            while (((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().containsKey(player)) {
                Random rand= new Random();
                player.setName(dataMessage.getName() + "-"+ rand.nextInt(10_000));

            }

            ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().put(player, clientHandlers);

            NamePlayer namePLayer = new NamePlayer(player.getName());
            messageToSend = new MessageAttachment<NamePlayer>(ServerMessageType.OK.getValue(), namePLayer);
            System.out.println("[JOinAsPlayer] code : " + messageToSend.getCode() + " nameplayer " + namePLayer.getName());

        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        if (messageToSend.getCode() == ServerMessageType.OK.getValue()) {
            Thread threadNotify = new Thread(() -> {

                NamePlayer namePlayer = new NamePlayer(player.getName());
                Message notifiation = new MessageAttachment<NamePlayer>(ServerMessageType.NEW_PLAYER_IN_GAME.getValue(), namePlayer);
                try {
                    this.notifyOne(((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                                    .get(((ServerGame)clientHandlers.getServerNetwork()).getCreator()),
                            notifiation);
                } catch (Exception e) {
                    System.out.println("[JoinAsPlayer] Can't notify the creator ...");
                }

            });
            threadNotify.start();
        }

        return messageToSend;
    }
}
