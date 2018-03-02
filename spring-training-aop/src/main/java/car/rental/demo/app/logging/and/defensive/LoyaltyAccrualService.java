package car.rental.demo.app.logging.and.defensive;

import java.time.LocalDateTime;
import static java.time.temporal.ChronoUnit.DAYS;;

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
		System.out.println("Customer: " + agreement.getCustomer().getCustomerId());
		System.out.println("Vehicle: " + agreement.getVehicle().getVin());
		
		long daysBetween = DAYS.between(agreement.getEndDate(), agreement.getStartDate());
		daysBetween = Math.abs(daysBetween);
		int pointsPerDay = 1;
		if(agreement.getVehicle().getSize() == Size.Luxury ||
				agreement.getVehicle().getSize() == Size.Truck ||
				agreement.getVehicle().getSize() == Size.SUV) {
			pointsPerDay = 2;
		}
		long points = daysBetween * pointsPerDay;
		iLoyaltyDataService.addPoints(agreement.getCustomer().getCustomerId(), points);
		
		System.out.println("Accrue complete: " + LocalDateTime.now().toString());
	}

}
