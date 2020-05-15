package com.it_uatech.administrationMessages;

import com.it_uatech.messageSystem.Address;
import com.it_uatech.messageSystem.Message;

public class OwnCompanionAddressResponseMessage implements Message {

    private final Address address;

    public OwnCompanionAddressResponseMessage(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "RegisterCompanionResponseMessage{" +
                "address=" + address +
                '}';
    }
}
