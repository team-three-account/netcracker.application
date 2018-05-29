package com.gmail.netcracker.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

@Configuration
public class TransactionConfig implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    @Autowired
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return platformTransactionManager(this.dataSource);
    }


}
