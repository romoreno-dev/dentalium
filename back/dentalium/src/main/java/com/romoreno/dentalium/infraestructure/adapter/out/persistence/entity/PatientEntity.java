package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "patient")
public class PatientEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "dni")
    private String dni;

    @Column(name = "address")
    private String address;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "province")
    private String province;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientEntity")
    private List<AppointmentEntity> appointmentEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientEntity",  fetch=FetchType.EAGER)
    private List<DoctorAssigmentEntity> doctorAssigmentEntityList;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserEntity userEntityId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientEntityId")
    private List<MedicalStudyEntity> medicalStudyEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientEntityDni")
    private List<BudgetEntity> budgetEntityList;

    public PatientEntity() {
    }

    public PatientEntity(String dni) {
        this.dni = dni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof PatientEntity other)) {
            return false;
        }
        return (this.dni != null || other.dni == null) && (this.dni == null || this.dni.equals(other.dni));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity[ dni=" + dni + " ]";
    }

}
