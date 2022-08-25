package test;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utils.ConfigReader;
import utils.PatientsUtils;
import utils.PayloadUtils;
import java.util.List;
import java.util.Map;

public class GetRequest {


    @Test
    public void verifyAppointmentDate() {

        /*
        In a test, using the response data from the previous step, verify there are 1 or more
        patients with an appointment date in the month of June 2022.
         */
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";
        Response response = RestAssured
                .given().accept("application/json")
                .when().get().then()
                .statusCode(200)
                .extract().response();
        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });

        boolean isPatientExist = false;
        for (Map<String, Object> patientInfo : parsedResponse) {
            if (patientInfo.get("appointment_date").toString().startsWith("2022-06")) {
                isPatientExist = true;
            }
        }
        Assert.assertTrue(isPatientExist);
    }


    @Test
    public void failIfPatientDoesNotExist() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";
        Response response = RestAssured
                .given().accept("application/json")
                .when().get().then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });

        boolean failIfPatientDoesNotExist = false;
        for (Map<String, Object> patient : parsedResponse) {
            if (patient.get("id").equals("SR19760827202206208364")
                    && patient.get("birthdate").equals("1976-08-27")
                    && patient.get("phone").equals("347-555-9876")
                    && patient.get("appointment_date").equals("2022-06-20")) {
                failIfPatientDoesNotExist = true;
            }
        }
        Assert.assertTrue(failIfPatientDoesNotExist);
    }

    @Test
    public void verifyPatientID() {
        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/patient";
        Response response = RestAssured
                .given().accept("application/json")
                .when().get().then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> parsedResponse = response.as(new TypeRef<List<Map<String, Object>>>() {
        });

        for (Map<String, Object> patient : parsedResponse) {
            Assert.assertTrue(PatientsUtils.verifyID(patient));
        }
    }

    @Test
    public void patchRequest() {
        final String patientID = "SR19760827202206208364";

        RestAssured.baseURI = "https://hs4hqu0udj.execute-api.us-east-1.amazonaws.com/test/update";

        Response response = RestAssured.given().queryParam(ConfigReader.readProperty("key"),
                        ConfigReader.readProperty("value"))
                .contentType("application/json")
                .body(PayloadUtils.getPayload(patientID,"Tom","Hanks",
                        "1234 W Madison","Chicago","IL","60459"))
                .when().patch()
                .then().statusCode(200).extract().response();

        Map<String,Object> patient = response.as(new TypeRef<Map<String,Object>>(){});

        Map<String,String> patientFullName = (Map<String, String>) patient.get("name");
        Map<String,String> patientAddress = (Map<String, String>) patient.get("address");

        Assert.assertEquals(patientID,patient.get("id"));
        Assert.assertEquals("Tom",patientFullName.get("firstName"));
        Assert.assertEquals("Hanks",patientFullName.get("lastName"));
        Assert.assertEquals("1234 W Madison",patientAddress.get("street"));
        Assert.assertEquals("Chicago",patientAddress.get("city"));
        Assert.assertEquals("IL",patientAddress.get("state"));
        Assert.assertEquals("60459",patientAddress.get("zip"));

    }





    }

