package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ListAssigmentResponse {

    private List<Person> assigmentList;

    public ListAssigmentResponse() {
        assigmentList = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Person {
        private String id;
        private String name;
        private Date initDate;
    }

}
