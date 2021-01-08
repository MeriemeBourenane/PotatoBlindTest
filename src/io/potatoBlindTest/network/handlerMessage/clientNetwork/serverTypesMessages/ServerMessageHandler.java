package io.potatoBlindTest.network.handlerMessage.clientNetwork.serverTypesMessages;

import io.potatoBlindTest.network.communication.Message;

import java.io.Serializable;

/**
 * Handle messages coming from the client
 */
public interface ServerMessageHandler<T extends Serializable> {

    Message handle(T dataMessage);
}
