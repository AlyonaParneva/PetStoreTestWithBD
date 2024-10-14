package PetStore;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class PetStoreTest extends BaseTest{

    @Test
    public  void testAddNewPet(){
        Response response=petStoragePage.addNewPet(1,2,"dog","TestPet",3,"test","available");
        response.then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("category.id",equalTo(2))
                .body("category.name",equalTo("dog"))
                .body("name", equalTo("TestPet"))
                .body("tags.id",hasItem(3))
                .body("tags.name",hasItem("test"))
                .body("status",equalTo("available"));

    }

    @Test(dependsOnMethods = "testAddNewPet")
    public void testGetPetById(){
        Response response= petStoragePage.getPetById(1);

        response.then()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "testGetPetById")
    public void testGetPetByStatus(){
        Response response= petStoragePage.getPetByStatus("available");
        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testGetPetByStatus")
    public void testPostPetUpdate(){
        Response response= petStoragePage.postPetUpdate(1,"NewTestPet","sold");
        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testPostPetUpdate")
    public void testPutPetUpdate(){
        Response response= petStoragePage.putPetUpdate(1,0,"dog","TestPetUpdate",3,"test","available");
        response.then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("category.id",equalTo(0))
                .body("category.name",equalTo("dog"))
                .body("name", equalTo("TestPetUpdate"))
                .body("tags.id",hasItem(3))
                .body("tags.name",hasItem("test"))
                .body("status",equalTo("available"));
    }

    @Test(dependsOnMethods = "testPutPetUpdate")
    public void testPostPetUploadImage(){
        Response response= petStoragePage.postPetUploadImage(1,"it`s a dog","src//main//java//PetStore//img//dog1.jpg");

        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testPostPetUploadImage")
    public void testDeletePet(){
        Response response= petStoragePage.deletePet(1);
        response.then()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "testDeletePet")
    public void testGetPetByIdAfterDelete(){
        Response response= petStoragePage.getPetByIdAfterDelete(1);

        response.then()
                .statusCode(404);

    }


}
