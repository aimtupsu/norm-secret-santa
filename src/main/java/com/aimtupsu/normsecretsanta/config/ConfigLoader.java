package com.aimtupsu.normsecretsanta.config;

public interface ConfigLoader {

    <T> T load(Class<T> classToken);

}
