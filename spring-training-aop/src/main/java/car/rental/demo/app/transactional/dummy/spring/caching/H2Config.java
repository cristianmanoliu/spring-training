package car.rental.demo.app.transactional.dummy.spring.caching;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import javax.sql.DataSource;

import org.h2.tools.DeleteDbFiles;


public class H2Config {
	private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test6";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
    
	public static Connection createConnectionToH2() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
		return dbConnection;
	}
	
	
	public static void dbSetup() {
		 DeleteDbFiles.execute("~", "test6", true);
		Connection connection = createConnectionToH2();
		Statement stmt = null;
        try {
            connection.setAutoCommit(false);
            stmt = connection.createStatement();
            stmt.execute("CREATE TABLE CUSTOMER(customerId varchar(30) primary key, name varchar(64), driversLicense varchar(64), dateOfBirth TIMESTAMP)");
            stmt.execute("CREATE TABLE VEHICLE(vin varchar(30) primary key, make varchar(32), model varchar(32), size varchar(32))");
            stmt.execute("CREATE TABLE POINTS(customerId varchar(30) primary key, numberofpoints INTEGER)");
            
           
            stmt.execute("INSERT INTO CUSTOMER(customerId, name, driversLicense, dateOfBirth) VALUES('189031518211', 'Trump', 'B2353636', '1989-03-15 13:25:00')");
            stmt.execute("INSERT INTO VEHICLE(vin, make, model, size) VALUES('TS1233B2553HXXXVYAA', 'Tesla', 'M2', 'Luxury')");
            
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
        }
		
	}
}
