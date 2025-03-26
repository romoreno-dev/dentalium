package com.romoreno.dentalium.infraestructure.adapter.out.persistence.provider;

import com.romoreno.dentalium.domain.port.out.persistence.DataConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataConnectionProviderImpl implements DataConnectionProvider {

    private final DataSource dataSource;

    public DataConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Object getConnection() throws Exception {
        return dataSource.getConnection();
    }
}
