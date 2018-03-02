package car.rental.demo.app.transactional.dummy.spring.caching;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

public class LoyaltyRedemptionService implements ILoyaltyRedemptionService {
	
	private ILoyaltyDataService iLoyaltyDataService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public LoyaltyRedemptionService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	@Transactional("rentalTxnManager")
	public void redeem(Invoice invoice, int numberOfDays) {
		System.out.println("Redeem: " + LocalDateTime.now().toString());
		System.out.println("Invoice: " + invoice.getInvoiceId());
		Vehicle vehicle = iLoyaltyDataService.getVehicleById(invoice.getVehicleId());
		int pointsPerDay = 10;
		if(vehicle.getSize() == Size.Luxury ||
				vehicle.getSize() == Size.Truck ||
						vehicle.getSize() == Size.SUV) {
			pointsPerDay = 15;
		}
		long points = numberOfDays*pointsPerDay;
		iLoyaltyDataService.subtractPoints(invoice.getCustomerId(), points);
		invoice.setDiscount(numberOfDays*invoice.getCostPerDay());
		
		System.out.println("Redeem complete: " + LocalDateTime.now().toString());
	}

}
