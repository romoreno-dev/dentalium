package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.domain.model.Treatment;
import com.romoreno.dentalium.domain.port.in.TreatmentPort;
import com.romoreno.dentalium.domain.port.out.persistence.TreatmentRepository;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;

import java.util.List;

@PortImplementation
public class TreatmentService implements TreatmentPort {

    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    @Override
    public boolean saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment) != null;
    }

    @Override
    public List<Treatment> getTreatments(Treatment treatment) {
        return treatmentRepository.findAll(treatment);
    }

    @Override
    public boolean deleteTreatment(Integer id) {
        treatmentRepository.delete(id);
        return true;
    }
}
