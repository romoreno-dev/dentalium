package com.romoreno.dentalium.application.services;

import com.romoreno.dentalium.application.mappers.UserMapperTemp;
import com.romoreno.dentalium.application.utils.SecureUtils;
import com.romoreno.dentalium.domain.model.User;
import com.romoreno.dentalium.domain.port.in.UserPort;
import com.romoreno.dentalium.domain.port.out.mail.MailSender;
import com.romoreno.dentalium.domain.port.out.persistence.UserRepository;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.user.dto.UserSearchResponse;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.DoctorEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.PatientEntity;
import com.romoreno.dentalium.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.romoreno.dentalium.infraestructure.config.annotations.GetTransaction;
import com.romoreno.dentalium.infraestructure.config.annotations.PortImplementation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@PortImplementation
public class UserService implements UserPort {

    private final MailSender mailSender;
    private final UserRepository userRepository;
    private final UserMapperTemp userMapperTemp;

    public UserService(MailSender mailSender, UserRepository userRepository, UserMapperTemp userMapperTemp) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.userMapperTemp = userMapperTemp;
    }

    @Override
    public List<User> getUsers(User user) {
        return List.of();
    }

    @Override
    @GetTransaction
    public boolean createUser(User user, String requestAuthor) {

        final var userEntity = this.userMapperTemp.toUserEntity(user);
        userEntity.setPassword("INICIAL");
        userEntity.setActive("1");
        userEntity.setValid("1");

        if ("ROLE_DENTIST".equals(user.getRole())) {
            userEntity.setRoleEntityId(userRepository.getRole(user.getRole()).get());
            final var doctorEntity = new DoctorEntity();
            doctorEntity.setCollegeNumber(Integer.parseInt(user.getIdentification()));
            userRepository.save(userEntity, doctorEntity);
        } else if ("ROLE_PATIENT".equals(user.getRole())) {
            userEntity.setRoleEntityId(userRepository.getRole(user.getRole()).get());
            final var patientEntity = new PatientEntity();
            patientEntity.setDni(user.getIdentification());
            patientEntity.setAddress(user.getAddress());
            patientEntity.setProvince(user.getProvince());
            patientEntity.setMunicipality(user.getMunicipality());
            patientEntity.setPostalCode(user.getPostalCode());
            userRepository.save(userEntity, patientEntity);
        }

        return true;
    }

    @Override
    public boolean deleteUser(Long id, String requestAuthor) {
        final var user =  userRepository.findById(id);
        if (user.isPresent()) {
            try {
                userRepository.delete(id.longValue());
            } catch (Exception e) {
                user.get().setValid("0");
                userRepository.save(user.get());
            }
        }
        return true;
    }

    @Override
    @GetTransaction
    public boolean changePassword(String email) {
        final var userEntity = new UserEntity();
        userEntity.setEmail(email);
        final var userList = userRepository.findAll(userEntity);
        if (!userList.isEmpty()) {
            final var user = userList.get(0);
            final var newUser = user.getPassword().equals("INICIAL");
            final var password = SecureUtils.generatePassword(10);
            final var encryptedPassword = new BCryptPasswordEncoder().encode(password);
            user.setPassword(encryptedPassword);

            userRepository.save(user);

            var msg = "";

            if (newUser) {
                msg = """
                Hola %s,
                
                Bienvenido a Dentalium, la plataforma de gestión de tus consultas dentales.
                Desde Dentalium podrás consultar tus citas, presupuestos y estudios dentales.
                Tus datos son los siguientes:
                 - Usuario: %s
                 - Contraseña: %s
                
               
                ¡Un saludo!
                
                El equipo de Dentalium
                """.formatted(user.getName(),
                        user.getUser(),
                        password);
            } else {
                msg = """
                Hola %s,
                
                Has solicitado cambiar tu contraseña de tu usuario %s de Dentalium.
                La nueva contraseña es: %s
                
                
                ¡Un saludo!
                
                El equipo de Dentalium
                """.formatted(user.getName(),
                        user.getUser(),
                        password);

            }

            mailSender.sendEmail("dentalium@gmail.com", user.getEmail(), newUser ? "Bienvenido a Dentalium" : "Solicitud de cambio de contraseña", msg);
        }
        return true;
    }

    @Override
    @GetTransaction
    public boolean activateUser(Long id, boolean active) {
        final var user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setActive(active ? "1":"0");
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    @Override
    public UserSearchResponse getPatients(String identification) {
        final var userOpt = userRepository.findPatient(identification);

        if (userOpt.isPresent()) {
            final var patient = userOpt.get();
            final var user = userMapperTemp.patientEntityToUser(patient);
            return new UserSearchResponse(Collections.singletonList(user));
        }
        return null;
    }

    @Override
    public User getUser(String username) {
        if ("admin".equals(username)) {
            final var userEntity = new UserEntity();
            userEntity.setUser("admin");
            final var userOpt = userRepository.findAll(userEntity).stream().findFirst();
            if (userOpt.isPresent()) {
                return userMapperTemp.setWithCommonData(userOpt.get());
            }
        } else {
            final var patientOpt = userRepository.findPatientByUsername(username);
            final var doctorOpt = userRepository.findDoctorByUsername(username);

            if (patientOpt.isPresent()) {
                return userMapperTemp.patientEntityToUser(patientOpt.get());
            } else if (doctorOpt.isPresent()) {
                return userMapperTemp.doctorEntityToUser(doctorOpt.get());
            }
        }

        return null;
    }

    @Override
    public UserSearchResponse getAllPatients() {
        final var patientsList = userRepository.findAllPatients();
        return new UserSearchResponse(patientsList.stream().map(userMapperTemp::patientEntityToUser).toList());
    }

    @Override
    public UserSearchResponse getAllDoctors() {
        final var doctorsList = userRepository.findAllDoctors();
        return new UserSearchResponse(doctorsList.stream().map(userMapperTemp::doctorEntityToUser).toList());    }
}
