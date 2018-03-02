package car.rental.demo.app.transactional.dummy.spring.caching;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import static java.time.temporal.ChronoUnit.DAYS;

import java.sql.Connection;
import java.sql.SQLException;;

public class LoyaltyAccrualService implements ILoyaltyAccrualService {
	
	private ILoyaltyDataService iLoyaltyDataService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public LoyaltyAccrualService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	/**
	 * This method contains the logic and rules of the loyalty program
	 */
	@Transactional(transactionManager="rentalTxnManager")
	public void accrue(RentalAgreement agreement) {
		
		System.out.println("Accrue date: " + LocalDateTime.now().toString());
		System.out.println("Customer: " + agreement.getCustomerId());
		System.out.println("Vehicle: " + agreement.getVehicleId());
		
		long daysBetween = DAYS.between(agreement.getEndDate(), agreement.getStartDate());
		daysBetween = Math.abs(daysBetween);
		int pointsPerDay = 1;

		Vehicle vehicle = iLoyaltyDataService.getVehicleById(agreement.getVehicleId());
		
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
