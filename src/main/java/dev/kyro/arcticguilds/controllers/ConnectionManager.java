package dev.kyro.arcticguilds.controllers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    public static Connection connection = null;

    public static void initConnection() {
        try {
            if(connection == null) connection = getConnection();
            createTable();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() {

        try {

            String url = "jdbc:mysql://sql.pitsim.net:3306/s1_GuildData";
            String username = "u1_C8NN8laTwo";
            String password = "VY.6a6s0!EUpX^x9jkezJgqP";

            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            dataSource = new HikariDataSource(config);

            return dataSource.getConnection();


        } catch(Exception e) { e.printStackTrace(); }
        return null;
    }

    private static void createTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS GUILD_DATA"
                + "  (UUID                 VARCHAR(36) PRIMARY KEY,"
                + "   created              BIGINT(255),"
                + "   name                 VARCHAR(255),"
                + "   owner                VARCHAR(36),"
                + "   balance              BIGINT(255),"
                + "   reputation           INTEGER,"
                + "   banner               INTEGER,"
                + "   buffs_damage         INTEGER,"
                + "   buffs_defence        INTEGER,"
                + "   buffs_xp             INTEGER,"
                + "   buffs_gold           INTEGER,"
                + "   buffs_renown         INTEGER,"
                + "   upgrades_size        INTEGER,"
                + "   upgrades_bank        INTEGER,"
                + "   upgrades_reputation  INTEGER,"
                + "   tag                  VARCHAR(255),"
                + "   members              MEDIUMTEXT)";



        Statement stmt = connection.createStatement();
        stmt.execute(sqlCreate);
    }
}
