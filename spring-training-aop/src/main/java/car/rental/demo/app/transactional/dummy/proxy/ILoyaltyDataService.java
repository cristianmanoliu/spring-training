package car.rental.demo.app.transactional.dummy.proxy;

public interface ILoyaltyDataService {
	void addPoints(String customerId, long points);
	void subtractPoints(String customerId, long points);
}
