package PetStore;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

        boolean addPetDb= petStorageDatabase.addPetIntoDB(
                1,
                2,
                "dog",
                "TestPet",
                3,
                "test",
                "available");
        assertTrue(addPetDb,"Failed to add pet to the database");
    }

    @Test(dependsOnMethods = "testAddNewPet")
    public void testGetPetById(){
        Response response= petStoragePage.getPetById(1);

        response.then()
                .statusCode(200);

        boolean petInDB= petStorageDatabase.PetInDB(1);
        assertTrue(petInDB, "Pet with ID 1 was not found in the database!");
    }

    @Test(dependsOnMethods = "testGetPetById")
    public void testGetPetByStatus(){
        Response response= petStoragePage.getPetByStatus("available");
        response.then()
                .statusCode(200);

        boolean isPetInDBWithStatus = petStorageDatabase.PetInDBWithStatus("available");
        assertTrue(isPetInDBWithStatus, "No pets with status 'available' found in the database!");
    }


    @Test(dependsOnMethods = "testGetPetByStatus")
    public void testPostPetUpdate(){
        Response response= petStoragePage.postPetUpdate(1,"NewTestPet","sold");
        response.then()
                .statusCode(200);

        boolean updatePostPetDB= petStorageDatabase.updatePostPetInDB(1,"NewTestPet","sold");
        assertTrue(updatePostPetDB,"Pet with ID 1 was not correctly updated in the database!");
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

        boolean updatePutPetDB = petStorageDatabase.updatePutPetInDB(
                1,
                0,
                "dog",
                "TestPetUpdate",
                3,
                "test",
                "available");
        assertTrue(updatePutPetDB, "Pet with ID 1 was not correctly updated in the database!");
    }

    @Test(dependsOnMethods = "testPutPetUpdate")
    public void testPostPetUploadImage(){
        Response response= petStoragePage.postPetUploadImage(1,"it`s a dog","src//main//java//PetStore//img//dog1.jpg");

        response.then()
                .statusCode(200);

        boolean updatePetPhotoDB= petStorageDatabase.updatePetPhotoInDB(1,"src//main//java//PetStore//img//dog1.jpg");
        assertTrue(updatePetPhotoDB,"Photo URL for pet with ID 1 was not correctly updated in the database!");
    }

    @Test(dependsOnMethods = "testPostPetUploadImage")
    public void testDeletePet(){
        Response response= petStoragePage.deletePet(1);
        response.then()
                .statusCode(200);

        petStorageDatabase.deletePetFromDB(1);
    }

    @Test(dependsOnMethods = "testDeletePet")
    public void testGetPetByIdAfterDelete(){
        Response response= petStoragePage.getPetByIdAfterDelete(1);

        response.then()
                .statusCode(404);

        boolean petInDB= petStorageDatabase.PetInDB(1);
        assertFalse(petInDB, "Pet with ID 1 is still found in the database after deletion!");
    }
}
