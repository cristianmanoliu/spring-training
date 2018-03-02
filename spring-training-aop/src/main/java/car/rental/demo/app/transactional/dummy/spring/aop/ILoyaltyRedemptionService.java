package car.rental.demo.app.transactional.dummy.spring.aop;

public interface ILoyaltyRedemptionService {
	void redeem(Invoice invoice, int numberOfDays);
}
