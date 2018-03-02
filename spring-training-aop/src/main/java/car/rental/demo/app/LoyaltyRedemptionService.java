package car.rental.demo.app;

import java.time.LocalDateTime;

public class LoyaltyRedemptionService implements ILoyaltyRedemptionService {
	
	private ILoyaltyDataService iLoyaltyDataService;

	public LoyaltyRedemptionService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	public void redeem(Invoice invoice, int numberOfDays) {
		int pointsPerDay = 10;
		if(invoice.getVehicle().getSize() == Size.Luxury ||
				invoice.getVehicle().getSize() == Size.Truck ||
						invoice.getVehicle().getSize() == Size.SUV) {
			pointsPerDay = 15;
		}
		long points = numberOfDays*pointsPerDay;
		iLoyaltyDataService.subtractPoints(invoice.getCustomer().getCustomerId(), points);
		invoice.setDiscount(numberOfDays*invoice.getCostPerDay());
	}

}
