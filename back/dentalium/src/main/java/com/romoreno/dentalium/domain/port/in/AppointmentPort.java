package com.romoreno.dentalium.domain.port.in;

import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.ModifyStatusAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SaveAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentRequest;
import com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.appointment.dto.SearchAppointmentResponse;

public interface AppointmentPort {

    SearchAppointmentResponse getAppointments(SearchAppointmentRequest searchAppointmentRequest);

    boolean saveAppointment(SaveAppointmentRequest appointment) throws Exception;

    boolean modifyStatus(ModifyStatusAppointmentRequest request) throws Exception;

    boolean deleteAppointment(Integer id);

    byte[] generatePdfCertificate(int id, boolean signed) throws Exception;

}
