package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "budget")
public class BudgetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetEntity", orphanRemoval = true)
    private List<BudgetLineEntity> budgetLineEntityList;

    @JoinColumn(name = "budget_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private BudgetStatusEntity budgetStatusEntityId;

    @JoinColumn(name = "patient_dni", referencedColumnName = "dni")
    @ManyToOne(optional = false)
    private PatientEntity patientEntityDni;

    public BudgetEntity() {
    }

    public BudgetEntity(Integer id) {
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

        if (!(object instanceof BudgetEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetEntity[ id=" + id + " ]";
    }

}
