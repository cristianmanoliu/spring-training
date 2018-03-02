package car.rental.demo.app.logging.and.defensive;

public interface ILoyaltyDataService {
	void addPoints(String customerId, long points);
	void subtractPoints(String customerId, long points);
}
