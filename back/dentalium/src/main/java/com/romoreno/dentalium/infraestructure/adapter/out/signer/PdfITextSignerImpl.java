package com.romoreno.dentalium.infraestructure.adapter.out.signer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.BouncyCastleDigest;
import com.itextpdf.signatures.DigestAlgorithms;
import com.itextpdf.signatures.PdfSigner;
import com.itextpdf.signatures.PrivateKeySignature;
import com.romoreno.dentalium.domain.port.out.signer.Signer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;

@Component
public class PdfITextSignerImpl implements Signer {

    @Value("file:/app/documents/certificate.p12")
    private Resource certificate;

    @Value("${certificate.password}")
    private String password;

    @Override
    public byte[] signDocument(byte[] document, String reason, Position position) {

        final var PASSWORD = password.toCharArray();
        final var CERTIFICATE_TYPE = "PKCS12";

        try (final var certificateInputStream = certificate.getInputStream();
             final var documentInputStream = new ByteArrayInputStream(document);
             final var documentOutputStream = new ByteArrayOutputStream();) {

            final var keyStore = KeyStore.getInstance(CERTIFICATE_TYPE);
            keyStore.load(certificateInputStream, PASSWORD);

            final var alias = keyStore.aliases().nextElement();
            final var privateKey = (PrivateKey) keyStore.getKey(alias, PASSWORD);
            final var chain = keyStore.getCertificateChain(alias);

            final var pdfReader = new PdfReader(documentInputStream);
            final var pdfWriter = new PdfWriter(documentOutputStream);
            final var pdfSigner = new PdfSigner(pdfReader, pdfWriter, new StampingProperties());

            final var rectangle = switch(position) {
                case CENTRAL -> new Rectangle(300, 470, 250, 60);
                default -> new Rectangle(5, 10, 200, 60);
            };
            final var pdfSignatureAppearance = pdfSigner.getSignatureAppearance();
            pdfSignatureAppearance.setReason(reason)
                    .setLocation("Málaga (Málaga)")
                    // .setContact("romorenodev@gmail.com") (No parece funcionar bien)
                    .setPageRect(rectangle);
            pdfSigner.setFieldName("Firma");

            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final var bouncyCastleDigest = new BouncyCastleDigest();

            final var privateKeySignature = new PrivateKeySignature(privateKey, DigestAlgorithms.SHA256, "BC");

            pdfSigner.signDetached(bouncyCastleDigest, privateKeySignature, chain, null, null, null,
                    0, PdfSigner.CryptoStandard.CMS);

            return documentOutputStream.toByteArray();

        } catch (Exception e) {
            return document;
        }
    }
}
