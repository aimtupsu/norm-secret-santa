package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;

import java.time.LocalDate;
import java.util.Set;

public interface SecretSantaConfig {

    String getPlace();

    LocalDate getDate();

    Set<SecretSantaParticipant> getParticipants();

}
