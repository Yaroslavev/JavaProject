package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbManager {
    private String database_name;
    private String host;
    private String username;
    private String password;
    private Connection connection;

    public DbManager() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("options.json");

            ObjectMapper objectMapper = new ObjectMapper();
            DatabaseConfig config = objectMapper.readValue(inputStream, ConfigWrapper.class).getDatabaseConfig();

            database_name = config.getName();
            host = config.getHost();
            username = config.getUsername();
            password = config.getPassword();

            String url = "jdbc:postgresql://" + host + ":5432/" + database_name;
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("------------ Successfully connected ------------");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void CreateDb() {
        try {
            URI fileName = getClass().getClassLoader().getResource("create_contacts.sql").toURI();
            Path filePath = Path.of(fileName);
            String query = Files.readString(filePath);
            Statement command = connection.createStatement();
            command.executeUpdate(query);
            command.close();
            System.out.println("------------ Table successfully created ------------");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void UpdateDb() {
        try {
            URI fileName = getClass().getClassLoader().getResource("update_contacts.sql").toURI();
            Path filePath = Path.of(fileName);
            String query = Files.readString(filePath);
            String checkDataQuery = "SELECT COUNT(*) FROM contacts";
            Statement command = connection.createStatement();
            ResultSet resCheckData = command.executeQuery(checkDataQuery);

            if (resCheckData.next() && resCheckData.getInt(1) == 0) {
                command.executeUpdate(query);
                System.out.println("------------ Table successfully updated ------------");
            }

            resCheckData.close();
            command.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void ShowDbData() {
        try {
            URI fileName = getClass().getClassLoader().getResource("get_contacts.sql").toURI();
            Path filePath = Path.of(fileName);
            String query = Files.readString(filePath);
            Statement command = connection.createStatement();
            ResultSet res = command.executeQuery(query);

            while (res.next()) {
                int id = res.getInt("id");
                String firstName = res.getString("first_name");
                String lastName = res.getString("last_name");
                String email = res.getString("email");
                String phone = res.getString("phone");
                String address = res.getString("address");
                System.out.println("Id: " + id + " Name: " + firstName + " " + lastName +
                        " Email: " + email + " Phone: " + phone + " Address: " + address);
            }

            res.close();
            command.close();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
