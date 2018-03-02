package car.rental.demo.app;

public interface ILoyaltyDataService {
	void addPoints(String customerId, long points);
	void subtractPoints(String customerId, long points);
}
