package car.rental.demo.app.transactional.dummy;

import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;

import java.sql.Connection;
import java.sql.SQLException;;

public class LoyaltyAccrualService implements ILoyaltyAccrualService {
	
	private ILoyaltyDataService iLoyaltyDataService;
	
	public LoyaltyAccrualService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	/**
	 * This method contains the logic and rules of the loyalty program
	 */
	public void accrue(RentalAgreement agreement) {
		if(agreement==null) {
			throw new NullPointerException("Non null agreement is required");
		}
		
		System.out.println("Accrue date: " + LocalDateTime.now().toString());
		System.out.println("Customer: " + agreement.getCustomerId());
		System.out.println("Vehicle: " + agreement.getVehicleId());
		
		try {
		
		int maxRetries = 5;
		boolean succeded = false;
		while(!succeded) {
			Connection connection = H2Config.createConnectionToH2();
			try {
				long daysBetween = DAYS.between(agreement.getEndDate(), agreement.getStartDate());
				daysBetween = Math.abs(daysBetween);
				int pointsPerDay = 1;
				connection.setAutoCommit(false);
				
				Vehicle vehicle = H2Config.getVehicle(connection, agreement.getVehicleId());
				
				if(vehicle.getSize() == Size.Luxury ||
						vehicle.getSize() == Size.Truck ||
								vehicle.getSize() == Size.SUV) {
					pointsPerDay = 2;
				}
				long points = daysBetween * pointsPerDay;
				iLoyaltyDataService.addPoints(agreement.getCustomerId(), points);
				connection.commit();
				succeded = true;
			}catch(Exception ex) {
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				
				if(maxRetries>=0) {
					maxRetries--;
				} else {
					throw new RuntimeException(ex);
				}
			} 
		}
		
		}catch(Throwable ex) {
			if(!ExceptionHandler.canHandle(ex)) {
				throw new RuntimeException(ex);
			}
		}
		
		System.out.println("Accrue complete: " + LocalDateTime.now().toString());
	}

}
