package it.loooop.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class Identification implements Serializable {
    private static final long serialVersionUID = 1857157464650282940L;
    private String identificationId = null;
    private String clientId = null;
    private String callbackUrl = null;
    private String insertTimestamp = null;
    private String updateTimestamp = null;
    private String status = null;
    private String idData = null;

    public Identification() {}

    public Identification(String identificationId, String clientId, String callbackUrl, String status, String idData, String insertTimestamp) {
        this.identificationId = identificationId;
        this.clientId = clientId;
        this.callbackUrl = callbackUrl;
        this.status = status;
        this.idData = idData;
        this.insertTimestamp = insertTimestamp;
    }

    @DynamoDbPartitionKey
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

    public String getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(String insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public String getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(String updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdData() {
        return idData;
    }

    public void setIdData(String idData) {
        this.idData = idData;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
