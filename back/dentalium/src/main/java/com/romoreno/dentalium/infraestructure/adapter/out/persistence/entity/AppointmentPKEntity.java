package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Embeddable
@Getter
@Setter
public class AppointmentPKEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "patient_id")
    private String patientId;

    @Basic(optional = false)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @Basic(optional = false)
    @Column(name = "init_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initDate;

    @Basic(optional = false)
    @Column(name = "end_date")
    private Date endDate;

    public AppointmentPKEntity() {
    }

    public AppointmentPKEntity(String patientId, Integer doctorId, Date initDate, Date endDate) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientId != null ? patientId.hashCode() : 0);
        hash += doctorId;
        hash += (initDate != null ? initDate.hashCode() : 0);
        hash += (endDate != null ? endDate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof AppointmentPKEntity other)) {
            return false;
        }
        if ((this.patientId == null && other.patientId != null) || (this.patientId != null && !this.patientId.equals(other.patientId))) {
            return false;
        }
        if (this.doctorId != other.doctorId) {
            return false;
        }
        if ((this.initDate == null && other.initDate != null) || (this.initDate != null && !this.initDate.equals(other.initDate))) {
            return false;
        }
        return (this.endDate != null || other.endDate == null) && (this.endDate == null || this.endDate.equals(other.endDate));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentPKEntity[ patientId=" + patientId + ", doctorId=" + doctorId + ", initDate=" + initDate + ", endDate=" + endDate + " ]";
    }

}
