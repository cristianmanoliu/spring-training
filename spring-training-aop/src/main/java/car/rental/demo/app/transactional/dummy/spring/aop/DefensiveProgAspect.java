package car.rental.demo.app.transactional.dummy.spring.aop;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class DefensiveProgAspect {
	
	@Pointcut("execution(* car.rental.demo.app.transactional.dummy.spring.aop.*Service.*(..))")
	public void handleNullValues() {}
	
	@Pointcut("execution(* car.rental.demo.app.transactional.dummy.spring.aop.*Service.*(..))")
	public void handleTxnManagement() {}
	
	@Before("handleNullValues()")
	public void handleNullValuesAdvice(JoinPoint jp) {
		Object[] args = jp.getArgs();
		for(int i = 0; i < args.length; i++) {
			if(args[i] == null) {
				throw new NullPointerException(
						String.format("Non null value is required for argument of method '%s' at index %d", jp.getSignature().toLongString(), i));
			}
		}
	}
	
	@Before("args(RentalAgreement)")
	public void testWithArgs(JoinPoint jp) {
		System.out.println("---- test args: " + jp.getSignature().toLongString());
	}
	
	@Before("@args(AopTestAnnotation,..)")
	public void testWithAtArgs(JoinPoint jp) {
		System.out.println("---- test @args: " + jp.getSignature().toLongString());
	}
	
	@Before("target(ILoyaltyDataService)")
	public void testWithATarget(JoinPoint jp) {
		System.out.println("---- test target: " + jp.getSignature().toLongString());
	}
	
	@AfterThrowing(pointcut="execution(* car.rental.demo.app.transactional.dummy.spring.aop.*Service.*(..))", throwing="error")
	public void afterThrowingAdvice(JoinPoint jp, Throwable error) {
		error.printStackTrace();
		System.out.println("");
	}
	
	@Around("handleTxnManagement()")
	public void handleTxnManagementAdviceAround1(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("handleTxnManagementAdviceAround1");
		jp.proceed();
	}
	
	
	@Around("handleTxnManagement()")
	public void handleTxnManagementAdvice(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("handleTxnManagement()");
		int maxRetries = 5;
		boolean succeded = false;
		while(!succeded) {
			Connection connection = TransactionManager.getConnection();
			connection.setAutoCommit(false);
			try {
				jp.proceed();
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
	}
	
}
