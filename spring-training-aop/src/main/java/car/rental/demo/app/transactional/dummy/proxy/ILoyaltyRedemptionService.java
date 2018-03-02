package car.rental.demo.app.transactional.dummy.proxy;

public interface ILoyaltyRedemptionService {
	void redeem(Invoice invoice, int numberOfDays);
}
