package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;

public interface UIController {

    public void handleMessage(Message incomingMessage);
}
