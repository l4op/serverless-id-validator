package it.loooop.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitializeRequest {

    @JsonProperty("identificationId")
    private String identificationId;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("callbackUrl")
    private String callbackUrl;

    public String getIdentificationId() {
        return identificationId;
    }

    public void setIdentificationId(String identificationId) {
        this.identificationId = identificationId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
