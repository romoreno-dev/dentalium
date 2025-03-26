package com.romoreno.dentalium.infraestructure.adapter.out.persistence.jpa;

import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.RoleEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.StudyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<StudyTypeEntity, Integer> {

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    Optional<RoleEntity> getRole(@Param("name") String name);

}
