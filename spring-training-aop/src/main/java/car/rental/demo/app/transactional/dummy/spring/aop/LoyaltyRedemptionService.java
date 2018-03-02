package car.rental.demo.app.transactional.dummy.spring.aop;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LoyaltyRedemptionService implements ILoyaltyRedemptionService {
	
	private ILoyaltyDataService iLoyaltyDataService;

	public LoyaltyRedemptionService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	public void redeem(Invoice invoice, int numberOfDays) {
		System.out.println("Redeem: " + LocalDateTime.now().toString());
		System.out.println("Invoice: " + invoice.getInvoiceId());
		
		Connection connection = TransactionManager.getConnection();
		Vehicle vehicle = H2Config.getVehicle(connection, invoice.getVehicleId());
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
