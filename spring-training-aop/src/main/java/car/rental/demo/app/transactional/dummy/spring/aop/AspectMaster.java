package car.rental.demo.app.transactional.dummy.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*@Aspect
@Order(2)
@Component*/
public class AspectMaster {
	@Pointcut("execution(* car.rental.demo.app.transactional.dummy.spring.aop.*Service.*(..))")
	public void handleTxnManagement() {}
	
	@Around("handleTxnManagement()")
	public void handleTxnManagementAdviceAround1(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("handleTxnManagementAdviceAround1");
		jp.proceed();
	}
}
