package car.rental.demo.app.logging.and.defensive;

public interface ILoyaltyRedemptionService {
	void redeem(Invoice invoice, int numberOfDays);
}
