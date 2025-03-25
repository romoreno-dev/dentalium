package com.romoreno.dentalium.domain.port.out.report;

import java.util.Map;

public interface ReportGenerator {

    byte[] generatePdfReport(String reportReference, Map<String, Object> parameter, Object connection) throws Exception;

}
