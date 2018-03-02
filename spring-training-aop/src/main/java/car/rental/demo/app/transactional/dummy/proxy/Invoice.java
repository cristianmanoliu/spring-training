package car.rental.demo.app.transactional.dummy.proxy;

public class Invoice {
	private String invoiceId;
	private String customerId;
	private String vehicleId;
	private double costPerDay;
	private double discount;
	
	public Invoice(String invoiceId, String customerId, String vehicleId, double costPerDay) {
		super();
		this.invoiceId = invoiceId;
		this.customerId = customerId;
		this.vehicleId = vehicleId;
		this.costPerDay = costPerDay;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
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
