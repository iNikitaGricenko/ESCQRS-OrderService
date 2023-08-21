package com.wolfhack.eventsourcingcqrs.aspect;

import com.wolfhack.eventsourcingcqrs.error.CommandException;
import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import com.wolfhack.eventsourcingcqrs.interfaces.Publisher;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class HandlerContextAspect {

	private final Publisher publisher;

	@Pointcut(value = "@annotation(com.wolfhack.eventsourcingcqrs.annotations.Handler)")
	public void callAtHandlerAnnotation() { }

	@Around(value = "callAtHandlerAnnotation()")
	public Object aroundCallAt(ProceedingJoinPoint joinPoint) {
		List<Command> retrievedValue = null;
		try {
			retrievedValue = (List<Command>) joinPoint.proceed();
			publisher.publish(retrievedValue);
		} catch (CommandException exception) {
			publisher.fail(exception.getCommand());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return retrievedValue;
	}

}
