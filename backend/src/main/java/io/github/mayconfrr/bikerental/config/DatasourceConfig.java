package io.github.mayconfrr.bikerental.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Profile({"prod"})
public class DatasourceConfig {

    public static final Pattern URL_PATTERN = Pattern.compile("^postgres://(?<username>[^:]):(?<passowrd>[^@])@(?<url>.+)$");

    public DatasourceConfig() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set");
        }

        Matcher matcher = URL_PATTERN.matcher(databaseUrl);

        if (!matcher.matches()) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set correctly");
        }

        System.setProperty("spring.datasource.url", "jbdc:postgresql://" + matcher.group("url"));
        System.setProperty("spring.datasource.username", matcher.group("username"));
        System.setProperty("spring.datasource.password", matcher.group("password"));
    }
}