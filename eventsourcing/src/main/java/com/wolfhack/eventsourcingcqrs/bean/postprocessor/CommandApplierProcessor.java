package com.wolfhack.eventsourcingcqrs.bean.postprocessor;

import com.wolfhack.eventsourcingcqrs.annotations.Applier;
import com.wolfhack.eventsourcingcqrs.interfaces.ApplierContainer;
import com.wolfhack.eventsourcingcqrs.interfaces.ApplierFunction;
import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
@Component
public class CommandApplierProcessor implements BeanPostProcessor {

	private final Map<String, List<Method>> appliersNames = new HashMap<>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ApplierContainer) {
			List<Method> applierMethods = Stream.of(bean.getClass().getMethods())
					.filter(method -> method.isAnnotationPresent(Applier.class))
					.toList();
			appliersNames.put(beanName, applierMethods);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (appliersNames.containsKey(beanName)) {
			if (!appliersNames.get(beanName).isEmpty()) {
				for (Method method : appliersNames.get(beanName)) {
					((ApplierContainer<?>) bean).add(method.getParameterTypes()[1], getApplierFunction(bean, method));
				}
			}
		}
		return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
	}

	private ApplierFunction getApplierFunction(@NonNull Object object, @NonNull Method applyMethod) {
		return (obj, command) -> invokeApplierFunction(object, applyMethod, obj, command);
	}

	private Object invokeApplierFunction(
			Object bean,
			Method applyMethod,
			Object processingObject,
			Command command) {
		try {
			return applyMethod.invoke(bean, processingObject, command);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			log.error("Failed to invoke applier bean");
			throw new RuntimeException(e);
		}
	}

}
