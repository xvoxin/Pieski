package com.example.xvoxin.pieski.Connection;

/**
 * Created by xvoxin on 07.10.2017.
 */
import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionClass {
    String ip = "eos.inf.ug.edu.pl";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "mniskowski";
    String un = "mniskowski";
    String password = "238204";


    //@SuppressLint("NewApi")
    public Connection CONN() {
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
