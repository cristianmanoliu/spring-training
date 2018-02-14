import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcApiSample {

  public static final String URL = "jdbc:h2:tcp://localhost/~/test";
  public static final String USER = "sa";
  public static final String PASSWORD = "";

  public static void main(String[] args) {
    Connection dbConnection = null;
    try {
      // init dbConnection
      dbConnection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Got a connection: " + dbConnection);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // set auto commit to false
    try {
      assert dbConnection != null;
      dbConnection.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Use the for general-purpose access to your database. Useful when you are using static SQL statements at runtime.
      Statement statement = dbConnection.createStatement();
      statement.execute("INSERT INTO TEST VALUES (1, 'CMA')");
      statement.execute("INSERT INTO TEST VALUES (2, 'CMA')");

      ResultSet rs = statement.executeQuery("SELECT * FROM TEST WHERE ID = 2");
      while (rs.next()) {
        String name = rs.getString("NAME");
        System.out.println("Name: " + name);

        int id = rs.getInt("ID");
        System.out.println("Id: " + id);
      }

      // A SQL statement is precompiled and stored in a PreparedStatement object.
      // 	Use the when you plan to use the SQL statements many times.
      // What "pre-compiled" means is that the first step has been completed, so it doesn't need to be done again.
      // The compilation phase can require some effort, particularly to find the best optimizations for the execution path
      // (what indexes to use, what join methods, and so on).
      PreparedStatement preparedStatement = dbConnection
          .prepareStatement("INSERT INTO TEST VALUES (?, ?)");
      preparedStatement.setInt(1, 3);
      preparedStatement.setString(2, "CMA_PS");
      preparedStatement.execute();

      // The interface used to execute SQL stored procedures.
      // CallableStatement callableStatement = dbConnection.prepareCall("{call demo_stored_proc(?, ?)}");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // commit work
    try {
      dbConnection.commit();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // close dbConnection
    try {
      dbConnection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
