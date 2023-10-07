package it.loooop.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;

@DynamoDbBean
public class Client implements Serializable {

    private static final long serialVersionUID = -3657280991435198479L;

    private String clientId = null;
    private String name = null;
    private String description = null;

    public Client() {}

    public Client(String clientId, String name, String description) {
        this.clientId = clientId;
        this.name = name;
        this.description = description;
    }

    @DynamoDbPartitionKey
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String appId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
