package org.example.database;

import org.example.gardenplace.GardenPlace;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.config.GardenConfig.*;

public class DBinteractions {

        static Connection connection;

        static {
                try {
                        connection = DriverManager.getConnection(DB_URL, USER, PASS);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        public static List<GardenPlace> getAllPlaces() throws SQLException {
                String sql = "SELECT * FROM garden.maingarden";
                List<GardenPlace> placesArray = new ArrayList<>();

                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        ResultSet selectAll = statement.executeQuery(sql);
                        System.out.println("------------AllPlaces:-----------");

                        while (selectAll.next()) {
                                GardenPlace currentPlace = DBinteractions.createGardenPlace(selectAll);
                                placesArray.add(currentPlace);
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }
                System.out.println("---------not JSON---------------");
                System.out.println(placesArray);//это строка с кучей \"

                return placesArray;
        }

        public static GardenPlace getPlaceByID(int chosenID) {
                String sql = "SELECT * FROM garden.maingarden WHERE id=?";
                GardenPlace chosenPlace = null;

                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, chosenID);
                        ResultSet chosenLocation = statement.executeQuery();

                        while (chosenLocation.next()) {
                                chosenPlace = DBinteractions.createGardenPlace(chosenLocation);
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }
                return chosenPlace;
        }


        public static void addNewPlace(GardenPlace place) throws SQLException {
                String sql = "INSERT INTO garden.maingarden (location, carrot, potato, cabbage) VALUES (?, ?, ?, ?)";

                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, place.getLocation());
                        statement.setInt(2, place.getCarrot());
                        statement.setInt(3, place.getPotato());
                        statement.setInt(4, place.getCabbage());
                        statement.executeUpdate();

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public static void updatePlace(int id, GardenPlace place) throws SQLException {
                String sql = "UPDATE garden.maingarden SET location=?, carrot=?, potato=?, cabbage=? WHERE id=?";

                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(5, id);
                        statement.setString(1, place.getLocation());
                        statement.setInt(2, place.getCarrot());
                        statement.setInt(3, place.getPotato());
                        statement.setInt(4, place.getCabbage());
                        statement.executeUpdate();

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        public static void deletePlace(int id) throws SQLException {
                String sql = "DELETE FROM garden.maingarden WHERE id=?";

                try (final PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, id);
                        statement.executeUpdate();

                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

        private static GardenPlace createGardenPlace(ResultSet resultStatement) throws SQLException {
                int id = resultStatement.getInt("id");
                String location = resultStatement.getString("location");
                int carrot = resultStatement.getInt("carrot");
                int potato = resultStatement.getInt("potato");
                int cabbage = resultStatement.getInt("cabbage");

                GardenPlace newPlace = new GardenPlace();
                newPlace.setId(id);
                newPlace.setLocation(location);
                newPlace.setCarrot(carrot);
                newPlace.setPotato(potato);
                newPlace.setCabbage(cabbage);

                System.out.println(newPlace.getLocation());
                System.out.println(newPlace.getCarrot());
                System.out.println(newPlace.getPotato());
                System.out.println(newPlace.getCabbage());

                return newPlace;
        }

        public DBinteractions() throws SQLException {
                System.out.println("DBinteractions - something wrong with that");
        }
}
