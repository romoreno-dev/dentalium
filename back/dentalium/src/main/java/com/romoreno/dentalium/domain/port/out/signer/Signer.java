package com.romoreno.dentalium.domain.port.out.signer;

public interface Signer {

    byte[] signDocument(byte[] document, String reason, Position position);

    enum Position{
        CENTRAL, FINAL
    }
}
