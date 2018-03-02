package car.rental.demo.app.logging.and.defensive;

import java.time.LocalDateTime;
import java.util.Date;

public class RentalAgreement {
	private Customer customer;
	private Vehicle vehicle;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public RentalAgreement(Customer customer, Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.customer = customer;
		this.vehicle = vehicle;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	
}
