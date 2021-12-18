package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.exception.SecretSantaRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class SecretSantaConfigLoaderImpl implements SecretSantaConfigLoader {

    private static final String SECRET_CONFIG_PATH_PROPERTY = "secretSantaConfig";
    private static final String DEFAULT_SECRET_SANTA_FILENAME = "secret-santa.yaml";

    private final ObjectMapper mapper;
    private SecretSantaConfig cashedConfig;

    public SecretSantaConfig load() {
        if (this.cashedConfig == null) {
            this.cashedConfig = this.loadFromFile();
        }
        return this.cashedConfig;
    }

    private SecretSantaConfig loadFromFile() throws SecretSantaRuntimeException {
        final String customConfigPath = System.getProperty(SECRET_CONFIG_PATH_PROPERTY);
        return customConfigPath == null || customConfigPath.isBlank()
                ? loadDefaultConfig()
                : loadCustomConfig(customConfigPath);
    }

    private SecretSantaConfig loadDefaultConfig() {
        try {
            final String defaultConfigPath = Objects.requireNonNull(Thread
                            .currentThread()
                            .getContextClassLoader()
                            .getResource(DEFAULT_SECRET_SANTA_FILENAME)
                    ).getPath();
            return loadFromFile(defaultConfigPath);
        } catch (IOException e) {
            throw new SecretSantaRuntimeException("Failed to load Secret Santa config!", e);
        }
    }

    private SecretSantaConfig loadCustomConfig(String configPath) {
        try {
            return loadFromFile(configPath);
        } catch (IOException e) {
            log.error("Failed to load custom Secret Santa config from path: {}. "
                    + "Trying load default config.", configPath);
            return loadDefaultConfig();
        }
    }

    private SecretSantaConfig loadFromFile(final String configPath) throws IOException {
        return this.mapper.readValue(new File(configPath), SecretSantaConfigPojo.class);
    }

}
