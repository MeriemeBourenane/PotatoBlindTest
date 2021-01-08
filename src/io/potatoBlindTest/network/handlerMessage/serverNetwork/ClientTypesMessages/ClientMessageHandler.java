package io.potatoBlindTest.network.handlerMessage.serverNetwork.ClientTypesMessages;

import io.potatoBlindTest.network.ClientHandler;
import io.potatoBlindTest.network.ServerGame;
import io.potatoBlindTest.network.communication.Message;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handle messages coming from the server when it notifies the client
 */
public interface ClientMessageHandler<T extends Serializable> {

    Message handle(T dataMessage,
                   ClientHandler clientHandlers);
}
