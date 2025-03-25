package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "doctor")
public class DoctorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "college_number")
    private Integer collegeNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorEntity")
    private List<AppointmentEntity> appointmentEntityList;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserEntity userEntityId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorEntity")
    private List<DoctorAssigmentEntity> doctorAssigmentEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctorEntityId")
    private List<MedicalStudyEntity> medicalStudyEntityList;

    public DoctorEntity() {
    }

    public DoctorEntity(Integer collegeNumber) {
        this.collegeNumber = collegeNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collegeNumber != null ? collegeNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof DoctorEntity other)) {
            return false;
        }
        return (this.collegeNumber != null || other.collegeNumber == null) && (this.collegeNumber == null || this.collegeNumber.equals(other.collegeNumber));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity[ collegeNumber=" + collegeNumber + " ]";
    }

}
