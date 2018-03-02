package car.rental.demo.app.transactional.dummy.spring.transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class H2LoyaltyDataService implements ILoyaltyDataService {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	
	@Transactional(transactionManager="rentalTxnManager")
	public Customer getCustomerById(String customerId) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Customer> query = jdbcTemplate.query("select * from CUSTOMER where customerId='" + customerId + "'", new RowMapper<Customer>() {

			public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
				Customer customer = new Customer(rs.getString("customerId"), rs.getString("name"), 
						rs.getString("driversLicense"), Instant.ofEpochMilli(rs.getDate("dateOfBirth").getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
				return customer;
			}
			
		});
		if(query.size()>0) {
			return query.get(0);
		}
		return null;
	}
	
	@Cacheable(value="vehicles")
	@Transactional(transactionManager="rentalTxnManager", propagation=Propagation.REQUIRES_NEW)
	public Vehicle getVehicleById(String vin) {
		
		if (TransactionSynchronizationManager.isActualTransactionActive()) {
			   TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
			   System.out.println();
			}
		
		try {
			System.out.println("waiting for service to return for id " + vin);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<Vehicle> query = jdbcTemplate.query("select * from VEHICLE where vin='" + vin + "'", new RowMapper<Vehicle>() {

			public Vehicle mapRow(ResultSet rs, int arg1) throws SQLException {
				Vehicle veh = new Vehicle(rs.getString("make"), rs.getString("model"), 
						rs.getString("vin"), Size.valueOf(rs.getString("size")));
				return veh;
			}
			
		});
		if(query.size()>0) {
			return query.get(0);
		}
		return null;
	}
	
	@Transactional(transactionManager="rentalTxnManager", propagation=Propagation.REQUIRES_NEW)
	public void addPoints(String customerId, long points) {
		
		System.out.println(String.format("Adding %d points for customer %s", points, customerId));
		
		List<Integer> query = jdbcTemplate.query("select numberofpoints from POINTS where customerId='" + customerId + "'", new RowMapper<Integer>() {

			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				
				return rs.getInt("numberofpoints");
			}
			
		});
		
		int numberOfPoints = 0;
		if(query.size()==0) {
			jdbcTemplate.update("INSERT INTO POINTS(customerId, numberofpoints) VALUES(?, ?)", customerId, 0);
		} else {
			numberOfPoints = query.get(0);
		}
		
		jdbcTemplate.update("UPDATE POINTS set numberofpoints=? where customerId = ?", numberOfPoints + points,customerId);
		
	}
	
	@Transactional(transactionManager="rentalTxnManager")
	public void listPoints() {
		List<Integer> query = jdbcTemplate.query("select * from POINTS", new RowMapper<Integer>() {

			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				System.out.println(String.format("CustomerId: %s, Points %d", rs.getString("customerId"), rs.getInt("numberofpoints")));
				return 0;
			}
			
		});
	}

	public void subtractPoints(String customerId, long points) {
		System.out.println(String.format("Subtract %d points from customer %s", points, customerId));
	}

}
