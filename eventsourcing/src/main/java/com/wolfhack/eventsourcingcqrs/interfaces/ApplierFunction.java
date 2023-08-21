package com.wolfhack.eventsourcingcqrs.interfaces;

public interface ApplierFunction<T> {
	T apply(T object, Command event);
}
