package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;

import java.util.Set;

public record SecretSantaConfigPojo(Set<SecretSantaParticipant> participants) implements SecretSantaConfig {

    @Override
    public Set<SecretSantaParticipant> getParticipants() {
        return this.participants;
    }
}
