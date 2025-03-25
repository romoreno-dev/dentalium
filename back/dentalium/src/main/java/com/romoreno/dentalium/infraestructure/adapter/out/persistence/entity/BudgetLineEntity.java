package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Entity
@Getter
@Setter
@Table(name = "budget_line")
public class BudgetLineEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected BudgetLinePKEntity budgetLinePKEntity;

    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "teeth")
    private String teeth;

    @Basic(optional = false)
    @Column(name = "discount")
    private float discount;

    @JoinColumn(name = "budget_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BudgetEntity budgetEntity;

    @JoinColumn(name = "treatment_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TreatmentEntity treatmentEntity;

    public BudgetLineEntity() {
    }

    public BudgetLineEntity(BudgetLinePKEntity budgetLinePKEntity) {
        this.budgetLinePKEntity = budgetLinePKEntity;
    }

    public BudgetLineEntity(BudgetLinePKEntity budgetLinePKEntity, int quantity, float discount) {
        this.budgetLinePKEntity = budgetLinePKEntity;
        this.quantity = quantity;
        this.discount = discount;
    }

    public BudgetLineEntity(int treatmentId, int budgetId) {
        this.budgetLinePKEntity = new BudgetLinePKEntity(treatmentId, budgetId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (budgetLinePKEntity != null ? budgetLinePKEntity.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof BudgetLineEntity other)) {
            return false;
        }
        return (this.budgetLinePKEntity != null || other.budgetLinePKEntity == null) && (this.budgetLinePKEntity == null || this.budgetLinePKEntity.equals(other.budgetLinePKEntity));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetLineEntity[ budgetLinePK=" + budgetLinePKEntity + " ]";
    }

}
