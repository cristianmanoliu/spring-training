package car.rental.demo.app.transactional.dummy.proxy;

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
		ILoyaltyRedemptionService redemptionService = ServiceFactory.createILoyaltyRedemptionService();
		
		Invoice invoice = new Invoice("INV2233/23.01.2018","1890315182118",
				"TS1233B2553HXXXVYAA", 31.5);
		
		redemptionService.redeem(invoice, 3);
	}

	private static void simulateAddingPoints() {
		ILoyaltyAccrualService accrualService = ServiceFactory.createILoyaltyAccrualService();
		RentalAgreement rentalAgreement = new RentalAgreement(
			"1890315182118",
			"TS1233B2553HXXXVYAA",
			LocalDateTime.now().plusDays(-3), LocalDateTime.now()
		);
		
		accrualService.accrue(null);
	}
}
