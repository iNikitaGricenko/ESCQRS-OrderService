package com.wolfhack.eventsourcingcqrs.bean.postprocessor;

import com.wolfhack.eventsourcingcqrs.annotations.Handler;
import com.wolfhack.eventsourcingcqrs.interfaces.Publisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CommandHandlerProcessor implements BeanPostProcessor {

	private final Map<String, List<Method>> handlers = new HashMap<>();
	private final Publisher publisher;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		List<Method> handleMethods = Stream.of(bean.getClass().getMethods())
				.filter(x -> x.isAnnotationPresent(Handler.class))
				.toList();
		if (!handleMethods.isEmpty()){
			handlers.put(beanName, handleMethods);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (handlers.containsKey(beanName)) {
			handlers.get(beanName).forEach(x -> addWithInvoke(bean, x));
		}
		return bean;
	}

	private void addWithInvoke(@NonNull Object object, @NonNull Method handleMethod) {
		Class[] classes = handleMethod.getParameterTypes();
		if (classes.length != 1) throw new RuntimeException("handler mast contains in params only Command");
		publisher.addCommandHandler(classes[0], a -> invoke(object, handleMethod, a));
	}

	private void invoke(@NonNull Object object, @NonNull Method handleMethod, Object param)
	{
		try {
			handleMethod.invoke(object, param);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			log.error("Failed to invoke handler bean");
			throw new RuntimeException(e);
		}
	}


}
