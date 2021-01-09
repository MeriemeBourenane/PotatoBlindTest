package io.potatoBlindTest.network;

import io.potatoBlindTest.gameEngine.Answer;
import io.potatoBlindTest.gameEngine.ListGames;
import io.potatoBlindTest.gameEngine.NameCreator;
import io.potatoBlindTest.network.communication.Message;
import io.potatoBlindTest.network.communication.MessageAttachment;
import io.potatoBlindTest.network.communication.additionalAttachements.SpecificServerGame;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages.ClientMessageType;
import io.potatoBlindTest.network.handlerMessage.serverNetwork.clientMessageTypeHandlers.game.GetAllGames;

import java.io.IOException;

public class MainClientNetwork {

    public static void main(String[] args) throws IOException, InterruptedException {
        ClientNetwork client1 = new ClientNetwork();
        ClientNetwork client2 = new ClientNetwork();
        Message message = new Message(ClientMessageType.CREATE_GAME.getValue());
        MessageAttachment<SpecificServerGame> answer = (MessageAttachment) client1.sendMessage(message);
        System.out.println("Message received : " + answer.getAttachment());


        message = new Message(ClientMessageType.GET_ALL_GAMES.getValue());
        MessageAttachment<ListGames> messageReceived2 = (MessageAttachment) client2.sendMessage(message);
        System.out.println("Liste de game : " + messageReceived2.getAttachment());

        client1.changeConnnection(answer.getAttachment().getPort(), answer.getAttachment().getIpAddress());
        System.out.println("New socket : " + client1.getSocket());
        NameCreator nameCreator = new NameCreator("TOTO");
        message = new MessageAttachment<NameCreator>(ClientMessageType.JOIN_AS_CREATOR.getValue(), nameCreator);
        Message messageReceived = client1.sendMessage(message);
        System.out.println("code retour join as creator : " + messageReceived.getCode());

        message = new Message(ClientMessageType.GET_ALL_GAMES.getValue());
        MessageAttachment<ListGames> messageReceived3 = (MessageAttachment) client1.sendMessage(message);
        System.out.println("Liste de game : " + messageReceived3.getAttachment());
        /*
        Answer submitAnAnswer = new Answer("TOTOOOO c'est sur c'est la réponse");
        Message message2 = new MessageAttachment<Answer>(ClientMessageType.SUBMIT_ANSWER.getValue(), submitAnAnswer);
        Message answer2 = client1.sendMessage(message2);
        System.out.println("Message received : " + answer2.getCode());
        */

    }
}
