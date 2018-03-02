package car.rental.demo.app.logging.and.defensive;

public class Invoice {
	private String invoiceId;
	private Customer customer;
	private Vehicle vehicle;
	private double costPerDay;
	private double discount;
	
	public Invoice(String invoiceId, Customer customer, Vehicle vehicle, double costPerDay) {
		super();
		this.invoiceId = invoiceId;
		this.customer = customer;
		this.vehicle = vehicle;
		this.costPerDay = costPerDay;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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
	public double getCostPerDay() {
		return costPerDay;
	}
	public void setCostPerDay(double costPerDay) {
		this.costPerDay = costPerDay;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
