package com.example.carrentaldesktop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDB {

    Connection conn;
    private static final String DB_HOST = "localhost";
    private static final String DB_NAME = "carrental";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public CarDB() throws SQLException {
        String connectionString = String.format("jdbc:mysql://%s/%s", DB_HOST, DB_NAME);
        conn = DriverManager.getConnection(connectionString, DB_USERNAME, DB_PASSWORD);
    }

    public List<Car> getCar() throws SQLException {
        List<Car> carList = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String license_plate_number = resultSet.getString("license_plate_number");
            String brand = resultSet.getString("brand");
            String model = resultSet.getString("model");
            int daily_cost = resultSet.getInt("daily_cost");
            Car c = new Car(id,license_plate_number,brand,model,daily_cost);
            carList.add(c);
        }
        return carList;
    }

    public boolean deleteCar(Car deleted) throws SQLException {
        String sql = "DELETE FROM cars WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, deleted.getId());
        return statement.executeUpdate() > 0;
    }
}
