package car.rental.demo.app;

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
	
	}

}
