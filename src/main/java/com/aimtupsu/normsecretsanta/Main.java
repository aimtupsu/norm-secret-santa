package com.aimtupsu.normsecretsanta;

import com.aimtupsu.normsecretsanta.config.ConfigFactory;
import com.aimtupsu.normsecretsanta.config.SecretSantaConfig;
import com.aimtupsu.normsecretsanta.config.SecretSantaConfigLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        // Load config
        SecretSantaConfigLoader secretSantaConfigLoader = ConfigFactory.INSTANCE.getSecretSantaConfigLoader();
        SecretSantaConfig secretSantaConfig = secretSantaConfigLoader.load();
        log.info("Loaded config: {}", secretSantaConfig);
    }
}
