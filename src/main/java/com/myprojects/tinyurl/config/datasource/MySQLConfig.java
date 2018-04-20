package com.myprojects.tinyurl.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages="com.myprojects.tinyurl", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "myTransactionManager")
public class MySQLConfig {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "my.datasource")
    public DataSource smDataSource()
    {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean smEntityManagerFactory(EntityManagerFactoryBuilder builder)
    {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        properties.put("hibernate.ddl-auto", "validate");
        properties.put("generate-ddl", "false");
        properties.put("show-sql", "true");

        return builder
                .dataSource(smDataSource())
                .packages("com.myprojects.tinyurl.domain")
                .persistenceUnit("my")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "myTransactionManager")
    public PlatformTransactionManager smTransactionManager()
    {
        return new DataSourceTransactionManager(smDataSource());
    }
}
