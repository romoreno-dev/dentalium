package com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "surname_1")
    private String surname1;

    @Column(name = "surname_2")
    private String surname2;

    @Basic(optional = false)
    @Column(name = "user")
    private String user;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @Column(name = "active")
    private String active;

    @Basic(optional = false)
    @Column(name = "valid")
    private String valid;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntityId")
    private List<DoctorEntity> doctorEntityList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntityId")
    private List<PatientEntity> patientEntityList;

    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private RoleEntity roleEntityId;

    public UserEntity() {
    }

    public UserEntity(Integer id) {
        this.id = id;
    }

    public UserEntity(Integer id, String name, String surname1, String user, String password, String phone, String active, String valid) {
        this.id = id;
        this.name = name;
        this.surname1 = surname1;
        this.user = user;
        this.password = password;
        this.phone = phone;
        this.active = active;
        this.valid = valid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof UserEntity other)) {
            return false;
        }
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity[ id=" + id + " ]";
    }

}
