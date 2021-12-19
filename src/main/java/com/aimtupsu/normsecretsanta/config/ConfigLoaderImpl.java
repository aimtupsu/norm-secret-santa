package com.aimtupsu.normsecretsanta.config;

import com.aimtupsu.normsecretsanta.exception.SecretSantaRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class ConfigLoaderImpl implements ConfigLoader {

    private final ObjectMapper mapper;
    private final Map<Class<?>, Object> cashedConfigs = new HashMap<>();

    public <T> T load(final Class<T> classToken) {
        return classToken.cast(cashedConfigs.computeIfAbsent(classToken, this::loadFromFile));
    }

    private <T> T loadFromFile(final Class<T> classToken) throws SecretSantaRuntimeException {
        final ConfigPaths configPath = ConfigPaths.findByClassToken(classToken)
                .orElseThrow(() -> new SecretSantaRuntimeException(
                        "Config with ClassToken " + classToken + " not found!"
                ));
        final String customConfigPath = System.getProperty(configPath.getConfigPathProperty());
        final String defaultConfigFilename = configPath.getDefaultConfigFilename();
        return customConfigPath == null || customConfigPath.isBlank()
                ? loadDefaultConfig(defaultConfigFilename, classToken)
                : loadCustomConfig(customConfigPath, defaultConfigFilename, classToken);
    }

    private <T> T loadDefaultConfig(final String defaultConfigFname, final Class<T> classToken) {
        try {
            final String defaultConfigPath = Objects.requireNonNull(Thread
                            .currentThread()
                            .getContextClassLoader()
                            .getResource(defaultConfigFname)
                    ).getPath();
            return loadFromFile(defaultConfigPath, classToken);
        } catch (IOException e) {
            throw new SecretSantaRuntimeException("Failed to load Secret Santa config!", e);
        }
    }

    private <T> T loadCustomConfig(final String configPath,
                                   final String defaultConfigFname,
                                   final Class<T> classToken) {
        try {
            return loadFromFile(configPath, classToken);
        } catch (IOException e) {
            log.error("Failed to load custom Secret Santa config from path: {}. "
                    + "Trying load default config.", configPath);
            return loadDefaultConfig(defaultConfigFname, classToken);
        }
    }

    private <T> T loadFromFile(final String configPath, Class<T> classToken) throws IOException {
        return this.mapper.readValue(new File(configPath), classToken);
    }

}
