package car.rental.demo.app.transactional.dummy.spring.aop;

import java.time.LocalDateTime;
import java.util.Date;

public class RentalAgreement {
	private String customerId;
	private String vehicleId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public RentalAgreement(String customerId, String vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
		super();
		this.customerId = customerId;
		this.vehicleId = vehicleId;
		this.startDate = startDate;
		this.endDate = endDate;
	}
		
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
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
