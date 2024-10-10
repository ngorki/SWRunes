import classes.runes.Rune;
import classes.runes.RuneArchetype;
import classes.runes.stats.StatType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import static classes.runes.stats.StatType.*;

public class Main {

    private static Connection db_conn;

    public void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "./json/Karueo.json";

        try {
            File jsonFile = new File(path);
            JsonNode jsonData = objectMapper.readTree(jsonFile);
            
            initDB();
            ArrayList<Rune> runes = parseRunes(jsonData.get("runes"));
            try {
                for(Rune rune : runes){
                    rune.insertIntoDB(getDBConnection());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Rune> parseRunes(JsonNode runeData){
        ArrayList<Rune> runeList = new ArrayList<Rune>();
        ArrayList<StatType> statTypes = new ArrayList<>(Arrays.asList(StatType.values()));
        RuneArchetype general = new RuneArchetype("General", statTypes);
        RuneArchetype dmg = new RuneArchetype("DMG", STAT_HP_PCT, STAT_ATK_PCT, STAT_CRIT_RATE_PCT, STAT_CRIT_DMG_PCT, STAT_SPD_PCT);

        for(JsonNode runeNode :  runeData){
            Rune runeObj = new Rune(runeNode);
            runeList.add(runeObj);
        }

        return runeList;
    }

    private static Connection getDBConnection() throws SQLException {
        if (db_conn == null || db_conn.isClosed()) {
            try {
                db_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/runes?user=" +
                        System.getenv("JDBC_USER") + "&password=" + System.getenv("JDBC_PASS"));
            } catch (SQLNonTransientConnectionException e) {
                System.err.println("Failed to connect to the database. Please check your connection settings.");
                throw e;
            } catch (SQLException e) {
                System.err.println("An error occurred while connecting to the database.");
                throw e;
            }
        }
        return db_conn;
    }

    public void initDB() {
        try {
            Connection conn = getDBConnection();
            createTable(conn);
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            // You might want to log the error or take appropriate action
        }
    }

    private void createTable(Connection conn) throws SQLException {
        String dropTableSQL = "DROP TABLE IF EXISTS Runes;";
        String createTableSQL = "CREATE TABLE Runes (\n" +
                "                id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                "                type VARCHAR(50),\n" +
                "                slot INT,\n" +
                "                grade INT,\n" +
                "                stars INT,\n" +
                "                level INT,\n" +
                "                main_stat VARCHAR(50),\n" +
                "                main_value INT,\n" +
                "                innate_stat VARCHAR(50),\n" +
                "                innate_value INT,\n" +
                "                sub1_stat VARCHAR(50),\n" +
                "                sub1_value INT,\n" +
                "                sub2_stat VARCHAR(50),\n" +
                "                sub2_value INT,\n" +
                "                sub3_stat VARCHAR(50),\n" +
                "                sub3_value INT,\n" +
                "                sub4_stat VARCHAR(50),\n" +
                "                sub4_value INT,\n" +
                "                ancient BOOLEAN,\n" +
                "                equipped BIGINT,\n" +
                "                value BIGINT,\n" +
                "                com2us_id BIGINT,\n" +
                "                efficiency DOUBLE\n" +
                "            );";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(dropTableSQL);
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the calling method
        }
    }

    public static void closeDBConnection() {
        if (db_conn != null) {
            try {
                db_conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
