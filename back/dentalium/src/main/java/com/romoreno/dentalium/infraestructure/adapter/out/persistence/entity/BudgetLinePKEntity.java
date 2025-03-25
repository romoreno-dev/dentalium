package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
public class BudgetLinePKEntity implements Serializable {

    @Basic(optional = false)
    @Column(name = "treatment_id")
    private Integer treatmentId;

    @Basic(optional = false)
    @Column(name = "budget_id")
    private Integer budgetId;

    public BudgetLinePKEntity() {
    }

    public BudgetLinePKEntity(Integer treatmentId, Integer budgetId) {
        this.treatmentId = treatmentId;
        this.budgetId = budgetId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += treatmentId;
        hash += budgetId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof BudgetLinePKEntity other)) {
            return false;
        }
        if (this.treatmentId != other.treatmentId) {
            return false;
        }
        return this.budgetId == other.budgetId;
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetLinePKEntity[ treatmentId=" + treatmentId + ", budgetId=" + budgetId + " ]";
    }

}
