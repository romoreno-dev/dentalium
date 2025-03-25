package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "budget_status")
public class BudgetStatusEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetStatusEntityId")
    private List<BudgetEntity> budgetEntityList;

    public BudgetStatusEntity() {
    }

    public BudgetStatusEntity(Integer id) {
        this.id = id;
    }

    public BudgetStatusEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof BudgetStatusEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.BudgetStatusEntity[ id=" + id + " ]";
    }

}
