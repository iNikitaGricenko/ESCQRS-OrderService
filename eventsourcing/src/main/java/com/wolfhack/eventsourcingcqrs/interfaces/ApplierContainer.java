package com.wolfhack.eventsourcingcqrs.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ApplierContainer<T> {

	private Map<Class, ApplierFunction<T>> classConsumerMap = new HashMap<>();

	public T apply(T object, List<? extends Command> commands) {
		for (com.wolfhack.eventsourcingcqrs.interfaces.Command command : commands) {
			if (classConsumerMap.containsKey(command.getClass())) {
				object = classConsumerMap.get(command.getClass()).apply(object, command);
			}
		}
		return object;
	}

	public T apply(List<? extends com.wolfhack.eventsourcingcqrs.interfaces.Command> commands) {
		return apply(null, commands);
	}

	public void add(Class _class, com.wolfhack.eventsourcingcqrs.interfaces.ApplierFunction<T> applierFunction) {
		classConsumerMap.put(_class, applierFunction);
	}

	public abstract  Class<T> getApplyingClass();

}
