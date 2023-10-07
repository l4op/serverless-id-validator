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
import it.loooop.response.GetIdentificationResponse;
import it.loooop.service.ClientService;
import it.loooop.service.IdentificationService;
import it.loooop.utils.Mapper;
import it.loooop.utils.Utils;
import it.loooop.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetIdentification implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    Logger logger = LoggerFactory.getLogger(Initialize.class);
    ObjectMapper mapper = new ObjectMapper();
    ClientService clientService = new ClientService();
    IdentificationService identificationService = new IdentificationService();

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
                String messsage = null;
                if("COMPLETED".equals(identificationFound.getStatus()))
                    messsage = "Identification completed";
                else
                    messsage = "Identification in progress";

                return Utils.buildResponse("OK",
                        mapper.writeValueAsString(
                                new GetIdentificationResponse("OK",
                                        messsage,
                                        identificationFound.getStatus(),
                                        identificationFound.getIdData(),
                                        identificationFound.getUpdateTimestamp())));

            }

            return Utils.buildResponse("KO", mapper.writeValueAsString(new GenericResponse("KO", "Identification not found")));

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
}
