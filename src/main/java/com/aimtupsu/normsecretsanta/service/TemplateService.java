package com.aimtupsu.normsecretsanta.service;

import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;

public interface TemplateService {

    String getFilledEmailMessage(SecretSantaParticipant giving, SecretSantaParticipant receiving);

}
