package it.loooop;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.loooop.exception.ValidationException;
import it.loooop.model.Client;
import it.loooop.model.Identification;
import it.loooop.request.InitializeRequest;
import it.loooop.response.GenericResponse;
import it.loooop.response.InitializeResponse;
import it.loooop.service.ClientService;
import it.loooop.service.IdentificationService;
import it.loooop.utils.Mapper;
import it.loooop.utils.Utils;
import it.loooop.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Initialize implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Logger logger = LoggerFactory.getLogger(Initialize.class);
    ObjectMapper mapper = new ObjectMapper();
    ClientService clientService = new ClientService();
    IdentificationService identificationService = new IdentificationService();
    S3Presigner presigner = S3Presigner.create();


    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        try {
            InitializeRequest request = mapper.readValue(event.getBody(), InitializeRequest.class);

            logger.info(request.toString());

            Validation.validateInitializeRequest(request);

            Identification identification = Mapper.initializeRequestToIdentification(request);

            //search for a client
            Client client = clientService.getClient(identification.getClientId());

            if(client == null) {
                throw new ValidationException("[client] No active client","CONTACT_SALES");
            }

            //Search for existing identification with the same identificationId
            Identification identificationFound = identificationService.getIdentification(identification.getIdentificationId());

            //identification found
            if (identificationFound != null) {
                throw new ValidationException("[identification] This identification is already present in our systems","ALREADY_PRESENT");
            }

            identificationService.persistIdentification(identification);

            URL selfieUrl = getPresignedUrl(identification.getIdentificationId()+"_selfie");
            URL documentUrl = getPresignedUrl(identification.getIdentificationId()+"_document");

            return Utils.buildResponse("OK", mapper.writeValueAsString(
                    new InitializeResponse("OK",
                            "Identification session started",
                            selfieUrl.toString(),
                            documentUrl.toString()
                            )));

        } catch (ValidationException e1) {
            logger.atError().setCause(e1).log("Exception = ValidationException Code = {} Message = {}", e1.getCode(), e1.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e1.getMessage()).toString());
        } catch (JsonProcessingException e2) {
            logger.atError().setCause(e2).log("Exception = JsonProcessingException Message = {}", e2.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e2.getMessage()).toString());
        } catch (Exception e) {
            logger.atError().setCause(e).log("Exception = Exception Message = {}", e.getMessage());
            return Utils.buildResponse("KO", new GenericResponse("KO", e.getMessage()).toString());
        }

    }

    public URL getPresignedUrl(String keyName) {

            Map<String, String> metadata = new HashMap<>();
            metadata.put("author","serverless-id-validator");
            metadata.put("version","1.0.0.0");

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(System.getenv("S3_PRESIGNED"))
                    .key(keyName)
                    .contentType("image/jpeg")
                    .metadata(metadata)
                    .build();

            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .putObjectRequest(objectRequest)
                    .build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            logger.info("Presigned URL to upload a file to: {}", presignedRequest.url());
            logger.info("Which HTTP method needs to be used when uploading a file: {}", presignedRequest.httpRequest().method());

            URL url = presignedRequest.url();
            return url;
    }
}