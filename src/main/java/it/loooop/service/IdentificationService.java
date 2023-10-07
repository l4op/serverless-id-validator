package it.loooop.service;

import it.loooop.client.DynamoDB;
import it.loooop.model.Identification;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class IdentificationService {

    DynamoDB dynamoDb = new DynamoDB();
    String identificationsTable = System.getenv("DYNAMODB_IDENTIFICATIONS_TABLE");

    DynamoDbTable<Identification> mappedTable = dynamoDb.client()
            .table(identificationsTable, TableSchema.fromBean(Identification.class));

    public String persistIdentification(Identification identification){
        mappedTable.putItem(identification);
        return identification.getIdentificationId();
    }

    public Identification getIdentification(String identificationId){
        return mappedTable.getItem(Key.builder().partitionValue(identificationId).build());
    }

    public String updateIdentification(Identification identification) {
        mappedTable.updateItem(identification);
        return identification.getIdentificationId();
    }

}
