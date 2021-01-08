package io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.turn;

import io.potatoBlindTest.gameEngine.Answer;
import io.potatoBlindTest.gameEngine.NamePlayer;
import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages.ServerMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageHandler;

public class SubmitAnswer implements ClientMessageHandler<Answer> {
    @Override
    public Message handle(Answer dataMessage, ClientHandler clientHandlers) {
        System.out.println("Handling submit answer ...");
        // Submit an answer :
        // - ask game engine if it's correct or not
        // - if it's not    -> send FORBIDDEN code
        // - if it is       -> send OK code
        //                  -> notify all ClientHandlers with TURN_RESULT code
        System.out.println("Server game has received the answer : " + dataMessage.getAnswer());
        Message messageToSend = new Message(ServerMessageType.OK.getValue());

        return messageToSend;
    }
}
