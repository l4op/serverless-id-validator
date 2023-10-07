package it.loooop.service;

import it.loooop.client.DynamoDB;
import it.loooop.model.Client;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class ClientService {
    DynamoDB dynamoDb = new DynamoDB();
    String clientsTable = System.getenv("DYNAMODB_CLIENTS_TABLE");

    DynamoDbTable<Client> mappedTable = dynamoDb.client()
            .table(clientsTable, TableSchema.fromBean(Client.class));

    public Client getClient(String clientId){
        return mappedTable.getItem(Key.builder().partitionValue(clientId).build());
    }

}
