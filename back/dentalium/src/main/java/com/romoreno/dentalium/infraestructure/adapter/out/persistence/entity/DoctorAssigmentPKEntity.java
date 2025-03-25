package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Embeddable
@Getter
@Setter
public class DoctorAssigmentPKEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_patient")
    private String idPatient;

    @Basic(optional = false)
    @Column(name = "id_doctor")
    private String idDoctor;

    @Basic(optional = false)
    @Column(name = "since")
    @Temporal(TemporalType.DATE)
    private Date since;

    public DoctorAssigmentPKEntity() {
    }

    public DoctorAssigmentPKEntity(String idPatient, String idDoctor, Date since) {
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
        this.since = since;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDoctor != null ? idDoctor.hashCode() : 0);
        hash += (since != null ? since.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof DoctorAssigmentPKEntity other)) {
            return false;
        }
        if (this.idPatient != other.idPatient) {
            return false;
        }
        if ((this.idDoctor == null && other.idDoctor != null) || (this.idDoctor != null && !this.idDoctor.equals(other.idDoctor))) {
            return false;
        }
        return (this.since != null || other.since == null) && (this.since == null || this.since.equals(other.since));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorAssigmentPKEntity[ idPatient=" + idPatient + ", idDoctor=" + idDoctor + ", since=" + since + " ]";
    }

}
