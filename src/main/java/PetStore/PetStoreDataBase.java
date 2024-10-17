package PetStore;

import java.sql.*;

public class PetStoreDataBase {
    public boolean addPetIntoDB(int id, int categoryId, String categoryName, String name, int tagId, String tagName, String status) {
        String insertQuery = "INSERT INTO pets (id, category_id, category_name, name, tag_id, tag_name, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.setString(3, categoryName);
            preparedStatement.setString(4, name);
            preparedStatement.setInt(5, tagId);
            preparedStatement.setString(6, tagName);
            preparedStatement.setString(7, status);

            int rowAffected=preparedStatement.executeUpdate();
            System.out.println("New Pet is found in the database.");
            return rowAffected>0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding a new pet to the database.");
            return false;
        }
    }

    public boolean PetInDB(int id){
        String selectQuery="SELECT * FROM pets WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("Pet with ID " + id + " is found in the database.");
                return true;
            }else {
                System.out.println("Pet with ID " + id + " is not found in the database.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean PetInDBWithStatus(String status){
        String selectQuery="SELECT * FROM pets WHERE status=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1,status);
            ResultSet resultSet=preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("Pet with status " + status + " is found in the database.");
                return true;
            }else {
                System.out.println("Pet with status " + status + " is not found in the database.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePostPetInDB(int id, String name, String status) {
        String selectQuery = "UPDATE pets SET name=?, status=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setString(    1, name);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pet with ID " + id + " successfully updated in the database!");
                return true;
            } else {
                System.out.println("No pet found with ID " + id + " in the database.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean updatePutPetInDB(int petId, int categoryId, String nameCategory, String name, int tagId, String nameTags,String status) {
        String selectQuery = "UPDATE pets SET category_id=?, category_name=?, name=?, tag_id=?, tag_name=?, status=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {

            preparedStatement.setInt(1, categoryId);
            preparedStatement.setString(2, nameCategory);
            preparedStatement.setString(3, name);
            preparedStatement.setInt(4, tagId);
            preparedStatement.setString(5, nameTags);
            preparedStatement.setString(6, status);
            preparedStatement.setInt(7, petId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pet with ID " + petId + " successfully updated in the database!");
                return true;
            } else {
                System.out.println("No pet found with ID " + petId + " in the database.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean updatePetPhotoInDB(int id, String photo_url){
        String updateQuery="UPDATE pets SET photo_url=? WHERE id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, photo_url);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Photo URL for pet with ID " + id + " successfully updated in the database!");
                return true;
            } else {
                System.out.println("No pet found with ID " + id + " in the database.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deletePetFromDB(int id){
        String deleteQuery="DELETE FROM pets WHERE id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pet with ID " + id + " successfully deleted from the database!");
            } else {
                System.out.println("No pet found with ID " + id + " in the database.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting pet from the database.");
        }
    }
}
