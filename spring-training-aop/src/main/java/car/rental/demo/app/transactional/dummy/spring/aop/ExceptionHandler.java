package car.rental.demo.app.transactional.dummy.spring.aop;

public class ExceptionHandler {
	public static boolean canHandle(Throwable t) {
		return false;
	}
}
