package com.kh.spring.common.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.kh.spring.common.LoggerInterceptor;
import com.kh.spring.model.vo.Member;

@Component
@Aspect
public class LoggerAspect {
	
	private Logger logger=LoggerFactory.getLogger(LoggerInterceptor.class);
	
	@Pointcut("execution(* com.kh.spring..Memo*.*(..))")
	public void myPointcut() {
		
	}
	
	@Around("myPointcut()")
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		Signature sig=joinPoint.getSignature();
		String type=sig.getDeclaringTypeName();//클래스 이름
		String method=sig.getName();//메소드 이름
		
		String componentType="";
		if(type.indexOf("Controller")>-1) {
			componentType="Controller \t: ";
		}
		else if(type.indexOf("Service")>-1) {
			componentType="Service \t: ";
		}
		else if(type.indexOf("Dao")>-1) {
			componentType="Dao \t: ";
		}
		logger.warn("[before]"+componentType+ type+"."+method+"()");
		//proceed하는 순간 넘어간다
		Object obj= joinPoint.proceed();
		logger.warn("[agter]"+componentType+ type+"."+method+"()");
		return obj;
		
	}
	
	
	@Pointcut("execution(* com.kh.spring..MemberDao*.*(..))")
	public void beforePoint() {}
	
	
	//AOP부분 다시 공부하기 ㅠㅠ 
	
	@Before("beforePoint()")
	public void beforeLogger(JoinPoint joinPoint) throws Exception{
		
		/*//session속성 불러오기 - spring에서만 됨
		HttpSession session=(HttpSession)RequestContextHolder.currentRequestAttributes().resolveReference(RequestAttributes.REFERENCE_SESSION);
		
		//request속성 불러오기
		HttpRequest request=(HttpRequest)RequestContextHolder.currentRequestAttributes().resolveReference(RequestAttributes.REFERENCE_REQUEST);
		
		*/
		Signature sig=joinPoint.getSignature();
		
		//abcd로만 로그인이 됨
		//args arguments는 매개변수를 의미함
		Object[] objs=joinPoint.getArgs();
		for(Object o : objs) {
			logger.warn("매개변수 : "+o);
			Member m = null;
			if(o instanceof Member) {
				m=(Member)o;
			}
			/*if(m!=null&&!"abcd".equals(m.getUserId())) {
				throw new Exception();
			}*/
		}
		
		
		logger.warn("Before : "+sig.getDeclaringType());
		
	}
	
	@After("execution(* com.kh.spring..Member*.*(..))")
	public void afterLogger(JoinPoint joinPoint) {
		logger.warn("After : 한방에!");
	}
	
	
	
	
	
	

}
