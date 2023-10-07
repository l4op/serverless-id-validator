package it.loooop.utils;


import it.loooop.model.Identification;
import it.loooop.request.InitializeRequest;

public class Mapper {

    public static Identification initializeRequestToIdentification(InitializeRequest request) {
        return new Identification(
                request.getIdentificationId(),
                request.getClientId(),
                request.getCallbackUrl(),
                "STARTED",
                null,
                Utils.currentStringDate()
        );
    }

}
