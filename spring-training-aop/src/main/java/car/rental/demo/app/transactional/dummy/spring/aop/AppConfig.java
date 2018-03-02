package car.rental.demo.app.transactional.dummy.spring.aop;

import java.lang.reflect.Proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AppConfig {
	
	@Bean
	public ILoyaltyAccrualService loyaltyAccrualService() {
		return new LoyaltyAccrualService(loyaltyDataService());
	}
	
	@Bean
	public ILoyaltyRedemptionService loyaltyRedemptionService() {
		return new LoyaltyRedemptionService(loyaltyDataService());
	}
	
	@Bean
	public ILoyaltyDataService loyaltyDataService() {
		return new H2LoyaltyDataService();
	}

}
