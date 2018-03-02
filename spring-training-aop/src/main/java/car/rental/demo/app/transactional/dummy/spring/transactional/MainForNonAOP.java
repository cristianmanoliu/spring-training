package car.rental.demo.app.transactional.dummy.spring.transactional;

import java.sql.Connection;
import java.time.LocalDateTime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainForNonAOP {
	
	static AnnotationConfigApplicationContext ctx;

	public static void main(String[] args) throws Exception {
		
		ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		H2Config.dbSetup();
		Connection conn = H2Config.createConnectionToH2();
		
		simulateAddingPoints();
		
		System.out.println(" ******************************************** ");
		
		simulateRemovingPoints();
		
		ILoyaltyDataService bean = ctx.getBean(ILoyaltyDataService.class);
		bean.listPoints();
		
		ctx.close();
		
	}

	private static void simulateRemovingPoints() {
		ILoyaltyRedemptionService redemptionService = ctx.getBean(ILoyaltyRedemptionService.class);
		
		Invoice invoice = new Invoice("INV2233/23.01.2018","1890315182118",
				"TS1233B2553HXXXVYAA", 31.5);
		
		redemptionService.redeem(invoice, 3);
	}

	private static void simulateAddingPoints() {
		ILoyaltyAccrualService accrualService = ctx.getBean(ILoyaltyAccrualService.class);
		RentalAgreement rentalAgreement = new RentalAgreement(
			"1890315182118",
			"TS1233B2553HXXXVYAA",
			LocalDateTime.now().plusDays(-3), LocalDateTime.now()
		);
		
		accrualService.accrue(rentalAgreement);
	}
}
