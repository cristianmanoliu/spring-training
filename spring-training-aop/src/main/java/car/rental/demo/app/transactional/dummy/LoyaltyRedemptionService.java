package car.rental.demo.app.transactional.dummy;

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
		
		if(invoice==null) {
			throw new NullPointerException("Non null invoice is required");
		}
		
		if(numberOfDays<=0) {
			throw new IllegalArgumentException("NumberOfDays should be bigger than 0");
		}
		
		
		System.out.println("Redeem: " + LocalDateTime.now().toString());
		System.out.println("Invoice: " + invoice.getInvoiceId());
		
		try {
		
			int maxRetries = 5;
			boolean succeded = false;
			while(!succeded) {
			Connection connection = H2Config.createConnectionToH2();
				try {
					connection.setAutoCommit(false);
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
					connection.commit();
					succeded = true;
				}catch(Exception ex) {
					ex.printStackTrace();
					try {
						connection.rollback();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					
					if(maxRetries>=0) {
						maxRetries--;
					} else {
						throw new RuntimeException(ex);
					}
				}
			}
		
		}catch(Throwable ex) {
			if(!ExceptionHandler.canHandle(ex)) {
				throw new RuntimeException(ex);
			}
		}
		
		System.out.println("Redeem complete: " + LocalDateTime.now().toString());
	}

}
