package PetStore;


import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetStorePage {

    private final String BASE_URL="https://petstore.swagger.io/v2";

    public Response addNewPet(int petId, int categoryId, String nameCategory, String name, int tagId, String nameTags,String status){
        String newPet = "{"+
                "\"id\": " + petId + ", " +
                "\"category\": { \"id\": " + categoryId + ", \"name\": \"" + nameCategory + "\" }," +
                "\"name\": \"" + name + "\"," +
                "\"photoUrls\": [ \"string\" ]," +
                "\"tags\": [ { \"id\": " + tagId + ", \"name\": \"" + nameTags + "\" } ]," +
                "\"status\": \"" + status + "\"" +
                " }";

        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .header("Content-Type","application/json")
                .body(newPet)
                .when()
                .post("/pet/");
        }

    public Response getPetById(int petId){
        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/"+petId);
    }

    public Response getPetByStatus(String status){
        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .queryParam("status")
                .when()
                .get("/pet/findByStatus/");
    }

    public Response postPetUpdate(int petId, String name, String status){
        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name")
                .formParam("status")
                .when()
                .post("pet/"+petId);
    }

    public Response putPetUpdate(int petId, int categoryId, String nameCategory, String name, int tagId, String nameTags,String status) {
        String updatePet = "{" +
                "\"id\": " + petId + ", " +
                "\"category\": { \"id\": " + categoryId + ", \"name\": \"" + nameCategory + "\" }," +
                "\"name\": \"" + name + "\"," +
                "\"photoUrls\": [ \"string\" ]," +
                "\"tags\": [ { \"id\": " + tagId + ", \"name\": \"" + nameTags + "\" } ]," +
                "\"status\": \"" + status + "\"" +
                " }";

        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .header("Content-Type","application/json")
                .body(updatePet)
                .when()
                .put("/pet/");
    }

    public Response postPetUploadImage(int petId, String additionalMetadata, String filePath){
        File file=new File(filePath);
        if(!file.exists()){
            throw new RuntimeException("файл не найден "+filePath);
        }
        return  given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .header("Content-Type","multipart/form-data")
                .multiPart("file",file)
                .formParam("additionalMetadata", additionalMetadata)
                .pathParam("petId",petId)
                .when()
                .post("/pet/{petId}/uploadImage");
    }

    public Response deletePet(int petId){
        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .when()
                .delete("/pet/"+petId);
    }

    public Response getPetByIdAfterDelete(int petId){
        return given()
                .relaxedHTTPSValidation()
                .baseUri(BASE_URL)
                .when()
                .get("/pet/"+petId);
    }
}
