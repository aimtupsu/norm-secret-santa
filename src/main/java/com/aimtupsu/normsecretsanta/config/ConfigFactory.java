package com.aimtupsu.normsecretsanta.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigFactory {

    public static final ConfigFactory INSTANCE = new ConfigFactory();

    private SecretSantaConfigLoader secretSantaConfigLoader;

    public SecretSantaConfigLoader getSecretSantaConfigLoader() {
        if (this.secretSantaConfigLoader == null) {
            this.secretSantaConfigLoader = this.createSecretSantaConfigLoader();
        }
        return this.secretSantaConfigLoader;
    }

    private SecretSantaConfigLoader createSecretSantaConfigLoader() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return new SecretSantaConfigLoaderImpl(mapper);
    }

}
