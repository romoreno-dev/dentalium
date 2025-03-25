package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "doctor_assigment")
public class DoctorAssigmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected DoctorAssigmentPKEntity doctorAssigmentPKEntity;

    @Column(name = "until")
    @Temporal(TemporalType.DATE)
    private Date to;

    @JoinColumn(name = "id_doctor", referencedColumnName = "college_number", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DoctorEntity doctorEntity;

    @JoinColumn(name = "id_patient", referencedColumnName = "dni", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PatientEntity patientEntity;

    public DoctorAssigmentEntity() {
    }

    public DoctorAssigmentEntity(DoctorAssigmentPKEntity doctorAssigmentPKEntity) {
        this.doctorAssigmentPKEntity = doctorAssigmentPKEntity;
    }

    public DoctorAssigmentEntity(String idPatient, String idDoctor, Date since) {
        this.doctorAssigmentPKEntity = new DoctorAssigmentPKEntity(idPatient, idDoctor, since);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (doctorAssigmentPKEntity != null ? doctorAssigmentPKEntity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof DoctorAssigmentEntity other)) {
            return false;
        }
        return (this.doctorAssigmentPKEntity != null || other.doctorAssigmentPKEntity == null) && (this.doctorAssigmentPKEntity == null || this.doctorAssigmentPKEntity.equals(other.doctorAssigmentPKEntity));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentEntity[ doctorAssigmentPK=" + doctorAssigmentPKEntity + " ]";
    }

}
