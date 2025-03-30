package com.romoreno.dentalium.application.mappers;

import com.romoreno.dentalium.application.utils.Utils;
import com.romoreno.dentalium.domain.model.User;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.MapperConversionTemp;

@MapperConversionTemp
public class UserMapperTemp {

    public User patientEntityToUser(PatientEntity patient) {
        final var user = setWithCommonData(patient.getUserEntityId());

        user.setIdentification(patient.getDni());
        user.setAddress(patient.getAddress());
        user.setPostalCode(patient.getPostalCode());
        user.setMunicipality(patient.getMunicipality());
        user.setProvince(patient.getProvince());


        final var assigment = patient.getDoctorAssigmentEntityList().stream()
                .filter(v -> v.getTo() == null).findFirst();
        if (assigment.isPresent()) {
            final var userReference = new User.UserReference();
            userReference.setId(assigment.get().getDoctorEntity().getCollegeNumber().toString());
            userReference.setName(Utils.extractPersonalData(assigment.get().getDoctorEntity().getUserEntityId()));
            user.setUserReference(userReference);
        }

        return user;
    }

    public User doctorEntityToUser(DoctorEntity doctor) {
        final var user = setWithCommonData(doctor.getUserEntityId());
        user.setIdentification(doctor.getCollegeNumber().toString());
        return user;
    }

    public User setWithCommonData(UserEntity userEntity) {
        final var user = new User();
        user.setId(userEntity.getId());
        user.setName(userEntity.getName());
        user.setFirstSurname(userEntity.getSurname1());
        user.setSecondSurname(userEntity.getSurname2());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setUser(userEntity.getUser());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRoleEntityId().getName());
        user.setActive(userEntity.getActive() != null && userEntity.getActive().equals("1"));
        return user;
    }

    public UserEntity toUserEntity(User user) {
        final var userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setSurname1(user.getFirstSurname());
        userEntity.setSurname2(user.getSecondSurname());
        userEntity.setUser(user.getUser());
        userEntity.setPhone(user.getPhone());
        userEntity.setEmail(user.getEmail());
        return userEntity;
    }


}
