package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.domain.model.Treatment;

import java.util.List;

public interface TreatmentPort {

    List<Treatment> getTreatments(Treatment treatment);

    boolean saveTreatment(Treatment treatment);

    boolean deleteTreatment(Integer id);

}
