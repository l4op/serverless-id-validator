package it.loooop.utils;

import it.loooop.exception.ValidationException;
import it.loooop.request.InitializeRequest;
public class Validation {

    public static void validateInitializeRequest(InitializeRequest ir) {
        if(ir == null)
            throw new ValidationException("Request malformed","REQUEST_MALFORMED");

        if(ir.getIdentificationId() == null || ir.getIdentificationId().isEmpty())
            throw new ValidationException("[identificationId] cannot be null or empty","REQUIRED_FIELD");

        if (ir.getClientId() == null || ir.getClientId().isEmpty())
            throw new ValidationException("[clientId] cannot be null or empty","REQUIRED_FIELD");

    }

}
