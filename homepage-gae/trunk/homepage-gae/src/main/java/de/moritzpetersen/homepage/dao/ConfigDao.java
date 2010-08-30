package de.moritzpetersen.homepage.dao;

import de.moritzpetersen.homepage.domain.Config;

public class ConfigDao extends BaseDao<Config> {
    public ConfigDao() {
        super(Config.class);
    }
}
