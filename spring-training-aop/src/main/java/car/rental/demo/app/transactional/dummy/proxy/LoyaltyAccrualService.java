package car.rental.demo.app.transactional.dummy.proxy;

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
		
		System.out.println("Accrue date: " + LocalDateTime.now().toString());
		System.out.println("Customer: " + agreement.getCustomerId());
		System.out.println("Vehicle: " + agreement.getVehicleId());
		
		long daysBetween = DAYS.between(agreement.getEndDate(), agreement.getStartDate());
		daysBetween = Math.abs(daysBetween);
		int pointsPerDay = 1;
		
		Connection connection = TransactionManager.getConnection();
		Vehicle vehicle = H2Config.getVehicle(connection, agreement.getVehicleId());
		
		if(vehicle.getSize() == Size.Luxury ||
				vehicle.getSize() == Size.Truck ||
						vehicle.getSize() == Size.SUV) {
			pointsPerDay = 2;
		}
		long points = daysBetween * pointsPerDay;
		iLoyaltyDataService.addPoints(agreement.getCustomerId(), points);
		
		System.out.println("Accrue complete: " + LocalDateTime.now().toString());
	}

}
