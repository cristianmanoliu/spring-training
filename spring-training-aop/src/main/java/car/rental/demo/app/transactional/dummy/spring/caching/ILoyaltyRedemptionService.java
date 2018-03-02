package car.rental.demo.app.transactional.dummy.spring.caching;

public interface ILoyaltyRedemptionService {
	void redeem(Invoice invoice, int numberOfDays);
}
