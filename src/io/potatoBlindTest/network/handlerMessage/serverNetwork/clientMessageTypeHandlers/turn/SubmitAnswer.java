package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.turn;

import io.potatoBlindTest.gameEngine.*;
import io.potatoBlindTest.gameEngine.statsGame.StatesGame;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.notification.SubjectClientHandler;

import java.util.Map;

public class SubmitAnswer extends SubjectClientHandler implements ClientMessageHandler<Answer> {
    @Override
    public Message handle(Answer dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling submit answer ...");
        // Submit an answer :
        // - ask game engine if it's correct or not
        // - if it's not    -> send FORBIDDEN code
        // - if it is       -> send OK code
        //                  -> notify all ClientHandlers with TURN_RESULT code
        Message messageToSend = null;
        IsCorrectAnswer isCorrectAnswer = null;
        GameEngine serverGameEngine = ((ServerGame)clientHandlers.getServerNetwork()).getGameEngine();
        System.out.println("Server game has received the answer : " + dataMessage.getAnswer());
        if (clientHandlers.getServerNetwork().isServerGame() && ((ServerGame)clientHandlers.getServerNetwork()).getStatesGame() == StatesGame.STARTED) {

            isCorrectAnswer = new IsCorrectAnswer(serverGameEngine.isCorrectAnswer(dataMessage.getAnswer()));

            /**
             * Set the winner
             */
            if (isCorrectAnswer.isCorrect()) {
                for (Map.Entry<Player,ClientHandler> element : ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().entrySet()) {
                    if (element.getValue().equals(clientHandlers)) {
                        serverGameEngine.getCurrentTurn().setPlayerWinner(element.getKey());
                        break;
                    }
                }
            }

            messageToSend = new MessageAttachment<IsCorrectAnswer>(ServerMessageType.OK.getValue(), isCorrectAnswer);

        } else {
            messageToSend = new Message(ServerMessageType.FORBIDDEN.getValue());
        }

        if (messageToSend.getCode() == ServerMessageType.OK.getValue() && isCorrectAnswer.isCorrect()) {
            Thread threadNotify = new Thread(() -> {
                // TODO: Temporary solution
                //  - need to wait until the answer is written and thus sent
                //  - maybe be use
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message notification;
                // Call game engine to choose a file
                if (serverGameEngine.isLastTurn()) {
                    TableScore tableScore = serverGameEngine.makeTableScore();
                    notification = new MessageAttachment<TableScore>(ServerMessageType.END_GAME_RESULTS.getValue(), tableScore);
                } else {
                    TurnResult turnResult = new TurnResult(serverGameEngine.getCurrentTurn().getAnswer(),
                            serverGameEngine.getCurrentTurn().getPlayerWinner().getName());
                    notification = new MessageAttachment<TurnResult>(ServerMessageType.TURN_RESULT.getValue(), turnResult);
                }

                for (Player player: ((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler().keySet()) {
                    try {
                        this.notifyOne(((ServerGame)clientHandlers.getServerNetwork()).getMapPlayerClientHandler()
                                        .get(player),
                                notification);
                    } catch (Exception e) {
                        System.out.println("[ReadyPlayer]->[thread notify] Can't notify the players ...");
                    }
                }

            });
            threadNotify.start();
        }


        return messageToSend;
    }
}
