package com.romoreno.dentalium.infraestructure.adapter.in.controller.rest;

public class Constants {

    public static class Controller {
        public static final String APPOINTMENT = "/appointment";
        public static final String ASSIGNMENT = "/assignment";
        public static final String BUDGET = "/budget";
        public static final String LOGIN = "/login";
        public static final String CERTIFICATE = "/certificate";
        public static final String MEDICAL_STUDY = "/study";
        public static final String TREATMENT = "/treatment";
        public static final String USER = "/user";

        private Controller() {
        }
    }

    public static class Path {
        public static final String GET = "/get";
        public static final String SAVE = "/save";
        public static final String CONTENT = "/content";
        public static final String STATUS = "/status/modify";
        public static final String MODIFY = "/modify";
        public static final String DELETE = "/delete";
        public static final String DOWNLOAD = "/download";
        public static final String DICOM = "/dicom";
        public static final String DATA = "/data";
        public static final String UPLOAD = "/upload";
        public static final String CHANGE = "/change";
        public static final String ACTIVATE = "/activate";
        public static final String PATIENT = "/patient";
        public static final String DOCTOR = "/doctor";

        private Path() {
        }
    }


//    public static class Path {
//        public static final String ID = "/id";
//
//        private Path() {
//        }
//    }

    public static class PathVariable {
        public static final String ID = "/{id}";
        public static final String USER_ID = "/{userId}";
        public static final String TREATMENT_ID = "/{treatmentId}";

        private PathVariable() {
        }
    }

    private Constants() {
    }
}
