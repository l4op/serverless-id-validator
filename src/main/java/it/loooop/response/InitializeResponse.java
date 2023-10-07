package it.loooop.response;

public class InitializeResponse extends GenericResponse {

    public InitializeResponse(String status, String message, String selfiePresignedUrl, String documentPresignedUrl) {
        super(status, message);
        this.selfiePresignedUrl = selfiePresignedUrl;
        this.documentPresignedUrl = documentPresignedUrl;
    }

    private String selfiePresignedUrl;
    private String documentPresignedUrl;

    public String getSelfiePresignedUrl() {
        return selfiePresignedUrl;
    }

    public void setSelfiePresignedUrl(String selfiePresignedUrl) {
        this.selfiePresignedUrl = selfiePresignedUrl;
    }

    public String getDocumentPresignedUrl() {
        return documentPresignedUrl;
    }

    public void setDocumentPresignedUrl(String documentPresignedUrl) {
        this.documentPresignedUrl = documentPresignedUrl;
    }
}
