package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "medical_study")
public class MedicalStudyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "path")
    private String path;

    @JoinColumn(name = "doctor_id", referencedColumnName = "college_number")
    @ManyToOne(optional = false)
    private DoctorEntity doctorEntityId;

    @JoinColumn(name = "patient_id", referencedColumnName = "dni")
    @ManyToOne(optional = false)
    private PatientEntity patientEntityId;

    @JoinColumn(name = "study_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StudyTypeEntity studyTypeEntityId;

    public MedicalStudyEntity() {
    }

    public MedicalStudyEntity(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof MedicalStudyEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.MedicalStudyEntity[ id=" + id + " ]";
    }

}
