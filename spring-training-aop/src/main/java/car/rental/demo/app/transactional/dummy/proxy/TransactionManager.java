package car.rental.demo.app.transactional.dummy.proxy;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TransactionManager {
	
	private static Map<Long, Connection> connections = new HashMap<Long, Connection>(); 
	
	public static Connection getConnection() {
		long threadId = Thread.currentThread().getId();
		Connection connection = connections.get(threadId);
	
		try {
			if(connection!=null && !connection.isClosed())
				return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection createConnectionToH2 = H2Config.createConnectionToH2();
		connections.put(threadId, createConnectionToH2);
		return createConnectionToH2;
	}
	
}
