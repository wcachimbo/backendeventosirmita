package com.backend.eventos.irmita.commons;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConexionBD {

    private DataSourceConfig dataSourceConfig;

    public ConexionBD(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    public Connection getConnection() throws SQLException {
        return dataSourceConfig.getDataSource().getConnection();
    }



}
