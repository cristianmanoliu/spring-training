package car.rental.demo.app.logging.and.defensive;

import java.time.LocalDateTime;

public class LoyaltyRedemptionService implements ILoyaltyRedemptionService {
	
	private ILoyaltyDataService iLoyaltyDataService;

	public LoyaltyRedemptionService(ILoyaltyDataService iLoyaltyDataService) {
		super();
		this.iLoyaltyDataService = iLoyaltyDataService;
	}

	public void redeem(Invoice invoice, int numberOfDays) {
		
		if(invoice==null) {
			throw new NullPointerException("Non null invoice is required");
		}
		
		if(numberOfDays<=0) {
			throw new IllegalArgumentException("NumberOfDays should be bigger than 0");
		}
		
		
		System.out.println("Redeem: " + LocalDateTime.now().toString());
		System.out.println("Invoice: " + invoice.getInvoiceId());
		
		int pointsPerDay = 10;
		if(invoice.getVehicle().getSize() == Size.Luxury ||
				invoice.getVehicle().getSize() == Size.Truck ||
						invoice.getVehicle().getSize() == Size.SUV) {
			pointsPerDay = 15;
		}
		long points = numberOfDays*pointsPerDay;
		iLoyaltyDataService.subtractPoints(invoice.getCustomer().getCustomerId(), points);
		invoice.setDiscount(numberOfDays*invoice.getCostPerDay());
		
		System.out.println("Redeem complete: " + LocalDateTime.now().toString());
	}

}
