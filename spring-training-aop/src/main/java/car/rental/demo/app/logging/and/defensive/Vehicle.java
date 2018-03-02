package car.rental.demo.app.logging.and.defensive;

public class Vehicle {
	private String make;
	private String model;
	private String vin;
	private Size size;
	
	public Vehicle(String make, String model, String vin, Size size) {
		super();
		this.make = make;
		this.model = model;
		this.vin = vin;
		this.size = size;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	
	
}
