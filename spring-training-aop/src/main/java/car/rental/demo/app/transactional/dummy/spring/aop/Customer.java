package car.rental.demo.app.transactional.dummy.spring.aop;

import java.time.LocalDateTime;
import java.util.Date;

public class Customer {
	
	private String customerId;
	private String name;
	private String driversLicense;
	private LocalDateTime dateOfBirth;
	
	public Customer(String customerId, String name, String driversLicense, LocalDateTime dateOfBirth) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.driversLicense = driversLicense;
		this.dateOfBirth = dateOfBirth;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDriversLicense() {
		return driversLicense;
	}
	public void setDriversLicense(String driversLicense) {
		this.driversLicense = driversLicense;
	}
	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
