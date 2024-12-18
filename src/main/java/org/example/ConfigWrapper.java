package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigWrapper {
    @JsonProperty("DatabaseConfig")
    private DatabaseConfig databaseConfig;

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public String toString() {
        return "ConfigWrapper{" +
                "DatabaseConfig=" + databaseConfig +
                '}';
    }
}
