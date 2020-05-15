package com.it_uatech.administrationMessages;

import com.it_uatech.messageSystem.Message;
import com.it_uatech.messageSystem.ServiceName;

public class OwnCompanionAddressRequestMessage implements Message {

    private final ServiceName prefixName;
    private final boolean requestCompanion;

    public OwnCompanionAddressRequestMessage(ServiceName prefixName, boolean requestCompanion) {
        this.prefixName = prefixName;
        this.requestCompanion = requestCompanion;
    }

    public ServiceName getPrefixName() {
        return prefixName;
    }

    public boolean isRequestCompanion() {
        return requestCompanion;
    }

    @Override
    public String toString() {
        return "OwnCompanionAddressRequestMessage{" +
                "prefixName=" + prefixName +
                ", requestCompanion=" + requestCompanion +
                '}';
    }
}
