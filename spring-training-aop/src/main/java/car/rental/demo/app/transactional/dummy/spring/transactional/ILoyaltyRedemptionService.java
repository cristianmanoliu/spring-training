package car.rental.demo.app.transactional.dummy.spring.transactional;

public interface ILoyaltyRedemptionService {
	void redeem(Invoice invoice, int numberOfDays);
}
