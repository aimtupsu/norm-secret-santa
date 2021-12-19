package com.aimtupsu.normsecretsanta.service;

import com.aimtupsu.normsecretsanta.config.SecretSantaConfig;
import com.aimtupsu.normsecretsanta.mail.MailClient;
import com.aimtupsu.normsecretsanta.model.SecretSantaParticipant;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
class SecretSantaServiceImpl implements SecretSantaService {

    private final Random random = new Random();
    private final SecretSantaConfig secretSantaConfig;
    private final MailClient mailClient;
    private final TemplateService templateService;

    @Override
    public void sendOutInvitations() {
        final var participantMap = mapParticipants(secretSantaConfig.getParticipants());
        participantMap.forEach(this::sendMailToParticipant);
    }

    /**
     * Shuffle set of participants and merged source set and shuffled lists to map when:
     *  - key is a participant from source set;
     *  - value is a person to whom participant will give a gift.
     *
     * @param participants set of participants.
     * @return set of pairs: participant and a person to whom he will give a gift.
     */
    private Map<SecretSantaParticipant, SecretSantaParticipant> mapParticipants(
            Set<SecretSantaParticipant> participants
    ) {
        Map<SecretSantaParticipant, SecretSantaParticipant> result = new LinkedHashMap<>();
        final List<SecretSantaParticipant> source = participants.stream().toList();
        final List<SecretSantaParticipant> shuffled = shuffleParticipants(source);
        for (int i = 0; i < participants.size(); i++) {
            result.put(source.get(i), shuffled.get(i));
        }
        return result;
    }

    /**
     * Shuffle the input list and return the new shuffled list.
     * <p>
     * The input list does not change.
     * <p>
     * During shuffling, it is considered that any participant
     * cannot take the position of a participant
     * who is prohibited from giving a gift.
     * Prohibitions are based on config.
     *
     * @param source list of participants
     * @return new shuffled list of participants
     */
    private List<SecretSantaParticipant> shuffleParticipants(List<SecretSantaParticipant> source) {
        final ArrayList<SecretSantaParticipant> result = new ArrayList<>(source);
        int i = 0;
        while (i < result.size() - 1) {
            final int ri = random.nextInt(result.size() - i - 1) + i + 1;
            final SecretSantaParticipant temp1 = result.get(ri);
            final SecretSantaParticipant temp2 = result.get(i);
            if (!(temp1.prohibition() != null && temp1.prohibition().equals(temp2.id()))
                    || !(temp2.prohibition() != null && temp2.prohibition().equals(temp1.id()))) {
                result.set(ri, temp2);
                result.set(i, temp1);
                i++;
            }
        }
        return result;
    }

    private void sendMailToParticipant(SecretSantaParticipant giving, SecretSantaParticipant receiving) {
        final String filledEmailMessage = templateService.getFilledEmailMessage(giving, receiving);
        mailClient.sendHtmlMail(giving.email(), filledEmailMessage);
    }

}
