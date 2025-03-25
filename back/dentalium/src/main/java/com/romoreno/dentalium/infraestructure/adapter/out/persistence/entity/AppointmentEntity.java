package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "appointment",
        uniqueConstraints = @UniqueConstraint(columnNames = {"patient_id", "doctor_id", "init_date", "end_date"}))
public class AppointmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    private AppointmentPKEntity appointmentPKEntity;

    @Column(name = "observations")
    private String observations;

    @JoinColumn(name = "appointment_status_id", referencedColumnName = "id")

    @ManyToOne(optional = false)
    private AppointmentStatusEntity appointmentStatusEntityId;
    @JoinColumn(name = "doctor_id", referencedColumnName = "college_number", insertable = false, updatable = false)

    @ManyToOne(optional = false)
    private DoctorEntity doctorEntity;

    @JoinColumn(name = "patient_id", referencedColumnName = "dni", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private PatientEntity patientEntity;

    public AppointmentEntity() {
    }

    public AppointmentEntity(AppointmentPKEntity appointmentPKEntity) {
        this.appointmentPKEntity = appointmentPKEntity;
    }

    public AppointmentEntity(String patientId, int doctorId, Date initDate, Date endDate) {
        this.appointmentPKEntity = new AppointmentPKEntity(patientId, doctorId, initDate, endDate);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appointmentPKEntity != null ? appointmentPKEntity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppointmentEntity other)) {
            return false;
        }
        return (this.appointmentPKEntity != null || other.appointmentPKEntity == null) && (this.appointmentPKEntity == null || this.appointmentPKEntity.equals(other.appointmentPKEntity));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.AppointmentEntity[ appointmentPK=" + appointmentPKEntity + " ]";
    }

}
