package it.loooop.response;

public class GetIdentificationResponse extends GenericResponse {

    public GetIdentificationResponse(String status, String message, String identificationStatus, String idData, String identificationTimestamp) {
        super(status, message);
        this.identificationStatus = identificationStatus;
        this.idData = idData;
        this.identificationTimestamp = identificationTimestamp;
    }

    private String idData;
    private String identificationStatus;
    private String identificationTimestamp;

    public String getIdData() {
        return idData;
    }

    public void setIdData(String idData) {
        this.idData = idData;
    }

    public String getIdentificationTimestamp() {
        return identificationTimestamp;
    }

    public void setIdentificationTimestamp(String identificationTimestamp) {
        this.identificationTimestamp = identificationTimestamp;
    }

    public String getIdentificationStatus() {
        return identificationStatus;
    }

    public void setIdentificationStatus(String identificationStatus) {
        this.identificationStatus = identificationStatus;
    }
}
