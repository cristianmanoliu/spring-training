package car.rental.demo.app.logging.and.defensive;

import java.time.LocalDateTime;

public class MainForNonAOP {

	public static void main(String[] args) {
		simulateAddingPoints();
		
		System.out.println(" ******************************************** ");
		
		simulateRemovingPoints();
		
	}

	private static void simulateRemovingPoints() {
		ILoyaltyDataService dataService = new FakeLoyaltyDataService();
		ILoyaltyRedemptionService redemptionService = new LoyaltyRedemptionService(dataService);
		
		Invoice invoice = new Invoice("INV2233/23.01.2018", 
				new Customer("1890315182118", "Trump", "B12345566", LocalDateTime.of(1989, 03, 15, 13, 25)),
				new Vehicle("Tesla", "M3", "TS1233B2553HXXXVYAA", Size.FullSize), 
				31.5);
		
		redemptionService.redeem(invoice, 3);
	}

	private static void simulateAddingPoints() {
		ILoyaltyDataService dataService = new FakeLoyaltyDataService();
		ILoyaltyAccrualService accrualService = new LoyaltyAccrualService(dataService);
		RentalAgreement rentalAgreement = new RentalAgreement(
			new Customer("1890315182118", "Trump", "B12345566", LocalDateTime.of(1989, 03, 15, 13, 25)),
			new Vehicle("Tesla", "M3", "TS1233B2553HXXXVYAA", Size.FullSize),
			LocalDateTime.now().plusDays(-3), LocalDateTime.now()
		);
		
		accrualService.accrue(rentalAgreement);
	}
}
