package car.rental.demo.app.transactional.dummy;

import java.sql.Connection;
import java.time.LocalDateTime;

public class MainForNonAOP {

	public static void main(String[] args) throws Exception {
		
		H2Config.dbSetup();
		Connection conn = H2Config.createConnectionToH2();
		Customer customer = H2Config.getCustomer(conn, "1890315182118");
		
		simulateAddingPoints();
		
		System.out.println(" ******************************************** ");
		
		simulateRemovingPoints();
		
	}

	private static void simulateRemovingPoints() {
		ILoyaltyDataService dataService = new H2LoyaltyDataService();
		ILoyaltyRedemptionService redemptionService = new LoyaltyRedemptionService(dataService);
		
		Invoice invoice = new Invoice("INV2233/23.01.2018","1890315182118",
				"TS1233B2553HXXXVYAA", 31.5);
		
		redemptionService.redeem(invoice, 3);
	}

	private static void simulateAddingPoints() {
		ILoyaltyDataService dataService = new H2LoyaltyDataService();
		ILoyaltyAccrualService accrualService = new LoyaltyAccrualService(dataService);
		RentalAgreement rentalAgreement = new RentalAgreement(
			"1890315182118",
			"TS1233B2553HXXXVYAA",
			LocalDateTime.now().plusDays(-3), LocalDateTime.now()
		);
		
		accrualService.accrue(rentalAgreement);
	}
}
