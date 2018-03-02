package car.rental.demo.app.transactional.dummy.spring.transactional;

public interface ILoyaltyDataService {
	void addPoints(String customerId, long points);
	void subtractPoints(String customerId, long points);
	public Customer getCustomerById(String customerId);
	public Vehicle getVehicleById(String vin);
	void listPoints();
}
