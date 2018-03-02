package car.rental.demo.app.transactional.dummy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

public class ServiceFactory {
	public static ILoyaltyAccrualService createILoyaltyAccrualService() {
		ILoyaltyAccrualService proxiedCls = (ILoyaltyAccrualService)Proxy.newProxyInstance(LoyaltyAccrualService.class.getClassLoader(), 
				new Class[] {ILoyaltyAccrualService.class}, 
				new RentACarProxyHandler(new LoyaltyAccrualService(createILoyaltyDataService())));
		return proxiedCls;
	}
	
	public static ILoyaltyRedemptionService createILoyaltyRedemptionService() {
		ILoyaltyRedemptionService proxiedCls = (ILoyaltyRedemptionService)Proxy.newProxyInstance(LoyaltyRedemptionService.class.getClassLoader(), 
				new Class[] {ILoyaltyRedemptionService.class}, 
				new RentACarProxyHandler(new LoyaltyRedemptionService(createILoyaltyDataService())));
		return proxiedCls;
	}
	
	public static ILoyaltyDataService createILoyaltyDataService() {
		return new H2LoyaltyDataService();
	}
	
	private static class RentACarProxyHandler implements InvocationHandler{
		
		private Object target;
		
		public RentACarProxyHandler(Object target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO Auto-generated method stub
			
			//Method method2 = Customer.class.getMethod("getName", new Class[] {});
			
			//handle defensive programming
			Parameter[] parameters = method.getParameters();
			for(int i = 0; i < args.length; i++) {
				if(args[i] == null) {
					if(parameters[i].isNamePresent()) {
						throw new NullPointerException(method.getName() + " - Non null value is required for " + parameters[i].getName());
					} else {
						throw new NullPointerException(
								String.format(target.getClass().getName() + "#" + method.getName() + " - Non null value is required for argument of type %s at index %d", parameters[i].getType().getName(), i));
					}
				}
			}
			
			int maxRetries = 5;
			boolean succeded = false;
			while(!succeded) {
				Connection connection = TransactionManager.getConnection();
				connection.setAutoCommit(false);
				try {
					method.invoke(target, args);
					connection.commit();
					connection.setAutoCommit(true);
					succeded = true;
				}catch(Exception ex) {
					ex.printStackTrace();
					try {
						connection.rollback();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					
					if(maxRetries>=0) {
						maxRetries--;
					} else {
						throw new RuntimeException(ex);
					}
				} 
			}
			
			return null;
		}
		
	}
}
