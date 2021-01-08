package io.potatoBlindTest.network.communication;

import java.io.Serializable;

public class MessageAttachment<T extends Serializable> extends Message {

    private final T attachment;

    public MessageAttachment(MessageAttachment<T> messageAttachment) {
        super(messageAttachment);
        this.attachment = messageAttachment.getAttachment();
    }

    public MessageAttachment(int code, T attachment) {
        super(code);
        this.attachment = attachment;
    }

    @Override
    public boolean hasAttachment() {
        return true;
    }

    public T getAttachment() {
        return attachment;
    }
}
