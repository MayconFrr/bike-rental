package io.github.mayconfrr.bikerental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@Profile({"prod"})
public class DatasourceConfig {

    public static final Pattern URL_PATTERN = Pattern.compile("^postgres://(?<username>\\w+):(?<password>\\w+)@(?<url>.+)$");

    @Bean
    public DataSource datasource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        if (databaseUrl == null) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set");
        }

        Matcher matcher = URL_PATTERN.matcher(databaseUrl);

        if (!matcher.matches()) {
            throw new IllegalStateException("DATABASE_URL environment variable is not set correctly");
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + matcher.group("url"));
        dataSource.setUsername(matcher.group("username"));
        dataSource.setPassword(matcher.group("password"));

        return dataSource;
    }
}