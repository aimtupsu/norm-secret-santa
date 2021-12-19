package com.aimtupsu.normsecretsanta.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
enum ConfigPaths {

    SECRET_SANTA_CONFIG(SecretSantaConfigPojo.class, "secretSantaConfig","secret-santa.yaml"),
    MAIL_CLIENT_CONFIG(MailClientConfigPojo.class, "mailClientConfig", "mail-client.yaml")
    ;

    private final Class<?> classToken;
    private final String configPathProperty;
    private final String defaultConfigFilename;

    public static Optional<ConfigPaths> findByClassToken(final Class<?> classToken) {
        return Stream.of(ConfigPaths.values())
                .filter(it -> it.getClassToken().equals(classToken))
                .findFirst();
    }

}
