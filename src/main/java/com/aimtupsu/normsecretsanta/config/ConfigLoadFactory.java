package com.aimtupsu.normsecretsanta.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigLoadFactory {

    public static final ConfigLoadFactory INSTANCE = new ConfigLoadFactory();

    private ConfigLoader configLoader;

    public SecretSantaConfig loadSecretSantaConfig() {
        return getConfigLoader().load(SecretSantaConfigPojo.class);
    }

    public MailClientConfig loadMailClientConfig() {
        return getConfigLoader().load(MailClientConfigPojo.class);
    }

    private ConfigLoader getConfigLoader() {
        if (this.configLoader == null) {
            this.configLoader = this.createSecretSantaConfigLoader();
        }
        return this.configLoader;
    }

    private ConfigLoader createSecretSantaConfigLoader() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        return new ConfigLoaderImpl(mapper);
    }

}
