package de.moritzpetersen.homepage.service;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.ConfigDao;
import de.moritzpetersen.homepage.domain.Config;

public class ConfigService {
    @Inject
    private ConfigDao configDao;

    public Config get() {
        for (Config config : configDao.findAll()) {
            return config;
        }
        return new Config();
    }

    public void put(Config config) {
        configDao.save(config);
    }
}
