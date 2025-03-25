package com.romoreno.dentalium.domain.port.out.persistence;

import com.romoreno.dentalium.domain.model.Treatment;

import java.util.List;

public interface TreatmentRepository {

    // Este modelo sigue verderamente la arquitectura hexagonal (Los otros no)

    Treatment save(Treatment treatment);

    void delete(Integer id);

    List<Treatment> findAll(Treatment entity);

}
