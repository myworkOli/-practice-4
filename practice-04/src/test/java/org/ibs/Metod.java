package org.ibs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Metod {
    public record Food(Integer foodId, String foodName, String foodType, Integer foodExotic) {    }

    public static List<Food> getFoodMaxId(){
        List<Food>  foodMaxId =new ArrayList<>();
        String sql= "SELECT * FROM food ORDER BY food_Id DESC LIMIT ?";
        try (Connection connection= BDConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1,1 );
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        foodMaxId.add(new Food(rs.getInt("food_Id"),
                                rs.getString("food_name"),
                                rs.getString("food_type"),
                                rs.getInt("FOOD_EXOTIC")));
                    }
                }

            } catch (SQLException e) {
            throw new RuntimeException("нет соединения с базой");
        }
        return foodMaxId;

    }

    public static List<String> getColomsName() {
        List<String> colomsName = new ArrayList<String>();
        try (Connection connection = BDConnection.getConnection()) {
            try (Statement ps = connection.createStatement()) {
                DatabaseMetaData md = connection.getMetaData();
                ResultSet rs = md.getColumns(null, null, "FOOD", null);
                while (rs.next()) {
                    colomsName.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return colomsName;

    }

    public static List<Food> getFoodTest() throws SQLException{
        List<Food>  foodTest =new ArrayList<>();
        String sql= "SELECT * FROM FOOD WHERE FOOD_NAME =? AND FOOD_TYPE = ? AND FOOD_EXOTIC =? ";
        try (Connection connection= BDConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setString(1, "Citrus limon(☼) _Azerbaijan 5HPH+PPC, Unnamed Road, Pərvanlı");
                ps.setString(2, "FRUIT");
                ps.setInt(3,0 );
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    foodTest.add(new Food(rs.getInt("food_Id"),
                            rs.getString("food_name"),
                            rs.getString("food_type"),
                            rs.getInt("FOOD_EXOTIC")));
                }
            }
        }



        return foodTest;

    }

}
