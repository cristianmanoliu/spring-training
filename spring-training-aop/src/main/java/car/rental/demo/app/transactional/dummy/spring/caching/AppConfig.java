package car.rental.demo.app.transactional.dummy.spring.caching;

import java.lang.reflect.Proxy;

import javax.sql.DataSource;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@EnableAspectJAutoProxy
@ComponentScan
@EnableCaching
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
	
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:~/test6;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("");
        dataSource.setPassword("");
 
        return dataSource;
	}
	
	@Bean
	public DataSourceTransactionManager rentalTxnManager() {
		return new DataSourceTransactionManager(dataSource());
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}

}
