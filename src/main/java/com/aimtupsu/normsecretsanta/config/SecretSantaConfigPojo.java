package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;

import java.util.List;

public record SecretSantaConfigPojo(List<SecretSantaParticipant> participants) implements SecretSantaConfig {

    @Override
    public List<SecretSantaParticipant> getParticipants() {
        return this.participants;
    }
}
