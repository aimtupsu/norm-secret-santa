package com.aimtupsu.normsecretsanta;

import com.aimtupsu.normsecretsanta.service.ServiceFactory;

public class Program {

    public static void main(String[] args) {
        ServiceFactory.INSTANCE.getSecretSantaService().sendOutInvitations();
    }

}
