package car.rental.demo.app;

public class FakeLoyaltyDataService implements ILoyaltyDataService {

	public void addPoints(String customerId, long points) {
		System.out.println(String.format("Adding %d points for customer %s", points, customerId));
	}

	public void subtractPoints(String customerId, long points) {
		System.out.println(String.format("Subtract %d points from customer %s", points, customerId));
	}

}
