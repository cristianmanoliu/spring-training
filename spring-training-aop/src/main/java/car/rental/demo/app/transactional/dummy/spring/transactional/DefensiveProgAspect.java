package car.rental.demo.app.transactional.dummy.spring.transactional;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DefensiveProgAspect {
	
	Map<String, Map<String, Object>> cacheMap = new HashMap<String, Map<String, Object>>();
	
	@Pointcut("within(H2LoyaltyDataService)")
	public void handleNullValues() {}
	
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
	
	@Around("target(H2LoyaltyDataService) && @annotation(org.springframework.cache.annotation.Cacheable) && args(id,..)")
	public Object handleCache(ProceedingJoinPoint jp, String id) throws Throwable {
		MethodSignature ms = (MethodSignature)jp.getSignature();
		Method method = ms.getMethod();
		Method declaredMethod = jp.getTarget().getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());
		Cacheable annotation = declaredMethod.getAnnotation(Cacheable.class);
		if(annotation!=null) {
			String[] value = annotation.value();
			String cacheName = value[0];
			Map<String, Object> map = cacheMap.get(cacheName);
			if(map==null) {
				map = new HashMap<String, Object>();
				cacheMap.put(cacheName, map);
			}
			Object object = map.get(id);
			if(object!=null) {
				return object;
			} else {
				Object proceed = jp.proceed();
				map.put(id, proceed);
				return proceed;
			}
		}
		
		return jp.proceed();
	}
	
}
