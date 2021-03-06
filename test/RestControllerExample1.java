import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.F;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import Model.*;
import Service.IdentificationsService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.libs.F.Promise;


public class RestControllerExample1 extends MainController{
    
    @Before
    public void cleanUp() {
        super.initiateController();
    }

    @Test
    public void test1() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), new Runnable() {
            @Override
            public void run() {
                JsonNode company = Json.parse("{\"id\": 1, \"name\": \"Test Bank\", \"sla_time\": 60, \"sla_percentage\": 0.9, \"current_sla_percentage\": 0.90}");
                assertEquals(WS.url("http://localhost:3333/api/v1/addCompany").post(company).get(10000).getStatus(), OK);

                JsonNode identification = Json.parse("{\"id\": 1, \"name\": \"Shawn\", \"time\": 1435667215, \"waiting_time\": 30, \"companyId\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

                identification = Json.parse("{\"id\": 2, \"name\": \"Shawn\", \"time\": 1435667215, \"waiting_time\": 45, \"companyId\": 1}");
                assertEquals(WS.url("http://localhost:3333/api/v1/startIdentification").post(identification).get(10000).getStatus(), OK);

                
                Promise<Pair<JsonNode,Integer>> pairPromise = WS.url("http://localhost:3333/api/v1/identifications").get().map(
                        new F.Function<WSResponse, Pair<JsonNode,Integer>>() {
                            public Pair<JsonNode,Integer> apply(WSResponse response) {
                                Integer status=response.getStatus();
                                JsonNode json = response.asJson();
                                return new Pair(json,status);
                            }
                        }
                );

                Pair<JsonNode,Integer> pair = pairPromise.get(1000);
                JsonNode jsonNode = pair.getKey();
                int status = pair.getValue();

                assertEquals(status, OK);

                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<Identifier> identificationsWithOrder =  mapper.readValue(jsonNode.toString(),
                            TypeFactory.defaultInstance().constructCollectionType(List.class, Identifier.class));
                    Identifier first = identificationsWithOrder.get(0);
                    Identifier second = identificationsWithOrder.get(1);
                    
                    assertEquals("checking if its equal to 2 :: ",first.getId(), 2);
                    assertEquals("checking if its equal to 1 :: ",second.getId(), 1);
                } catch (JsonParseException e) {
                    Logger.error("problem occured during json object parsing", e);
                } catch (IOException e) {
                    Logger.error("problem occured during json object parsing", e);
                }
            }
        });
    }
}