package com.romoreno.dentalium.infraestructure.adapter.out.report;

import com.romoreno.dentalium.domain.port.out.report.ReportGenerator;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;

@Component
public class JasperReportGeneratorImpl implements ReportGenerator {

    @Override
    public byte[] generatePdfReport(String reportReference, Map<String, Object> params, Object connectionObject) throws Exception {

        final var resource = String.format("jasper/%s.jasper", reportReference);

        try (var jasperReport = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)) {

            final var jasperPrint = this.fillReport(jasperReport, params, connectionObject != null ? connectionObject :
                    new JREmptyDataSource());

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }
    }

    private JasperPrint fillReport(InputStream jasperReport, Map<String, Object> params, Object connectionObject) throws JRException {
        if (connectionObject instanceof Connection connection) {
            return JasperFillManager.fillReport(jasperReport, params, connection);
        } else if (connectionObject instanceof JRDataSource jrDataSource) {
            return JasperFillManager.fillReport(jasperReport, params, jrDataSource);
        } else {
            throw new JRException("JasperFillManager.fillReport: No es posible encontrar un datasource valido");
        }
    }
}
