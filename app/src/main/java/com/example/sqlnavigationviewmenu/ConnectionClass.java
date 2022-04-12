package com.example.sqlnavigationviewmenu;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    private String login  = "sa";
    private String password = "12345";
    private String ip = "192.168.29.1";
    private String port = "1433";
    private String databaseName = "test";
    private String url = "";
    private Connection connection = null;

    public Connection connection()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            url = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+databaseName+";user="+login+";password="+password+";";
            connection = DriverManager.getConnection(url);
        }catch (Exception e){

        }
        return connection;
    }
}
