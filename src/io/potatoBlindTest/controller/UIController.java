package io.potatoBlindTest.controller;

import io.potatoBlindTest.network.communication.Message;

public interface UIController {

    void handleMessage(Message incomingMessage);
}
