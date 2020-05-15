package com.it_uatech.messageSystem;

public abstract class AddressedMessage implements Message {

    private final Address fromAddress;
    private final Address toAddress;

    public AddressedMessage(Address fromAddress, Address toAddress) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public Address getFrom() {
        return fromAddress;
    }

    public Address getTo() {
        return toAddress;
    }

    public abstract AddressedMessage exec(Addressee addressee);

    @Override
    public String toString() {
        return "AddressedMessage{" +
                "fromAddress=" + fromAddress +
                ", toAddress=" + toAddress +
                '}';
    }
}
