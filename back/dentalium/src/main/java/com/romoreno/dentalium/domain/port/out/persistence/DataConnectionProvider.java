package com.romoreno.dentalium.domain.port.out.persistence;

public interface DataConnectionProvider {

    Object getConnection() throws Exception;

}
