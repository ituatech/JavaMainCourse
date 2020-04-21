package com.it_uatech.messageSystem;

public abstract class Message {

    private final Address fromAddress;
    private final Address toAddress;

    public Message(Address fromAddress, Address toAddress) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public Address getFrom() {
        return fromAddress;
    }

    public Address getTo() {
        return toAddress;
    }

    public abstract void exec(Addressee addressee);
}
