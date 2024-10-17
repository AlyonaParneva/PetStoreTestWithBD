package PetStore;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static PetStorePage petStoragePage;
    protected static PetStoreDataBase petStorageDatabase;

    @BeforeClass
    public void setup(){
        petStorageDatabase=new PetStoreDataBase();
        petStoragePage=new PetStorePage();
    }
}
