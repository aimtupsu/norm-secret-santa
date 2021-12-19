package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;

import java.time.LocalDate;
import java.util.Set;

public record SecretSantaConfigPojo(String place,
                                    LocalDate date,
                                    Set<SecretSantaParticipant> participants) implements SecretSantaConfig {

    @Override
    public String getPlace() {
        return this.place;
    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public Set<SecretSantaParticipant> getParticipants() {
        return this.participants;
    }
}
