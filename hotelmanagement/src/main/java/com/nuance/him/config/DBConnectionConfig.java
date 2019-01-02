/*
 * COPYRIGHT: Copyright (c) 2018 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *
 */

package com.nuance.him.config;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

@Configuration
@PropertySource(value = {"classpath:application.properties"})
public class DBConnectionConfig {
    private static final Logger log = LoggerFactory.getLogger(DBConnectionConfig.class);


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driverClassName}")
    private String driverClass;



    /**
     * Data source bean.
     *
     * @return the data source
     */
       @Bean
        public DataSource dataSource() {
           log.info("setting up database connection");
            SQLServerDataSource ds=null;
            try {
                // Establish the connection.
                 ds = new SQLServerDataSource();
                 ds.setURL(url);

                 ds.getConnection();
            }
            catch (SQLServerException e) {
                e.printStackTrace();
            }
            return ds;
        }

        @Bean
        @Qualifier("dataSource")
        public JdbcTemplate jdbcTemplate(DataSource dataSource) {
            log.info("setting up JdbcTemplate bean");
            return new JdbcTemplate(dataSource);
        }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    }

