package com.wolfhack.eventsourcingcqrs.service;

import com.wolfhack.eventsourcingcqrs.applier.OrderApplier;
import com.wolfhack.eventsourcingcqrs.model.Order;
import com.wolfhack.eventsourcingcqrs.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderApplier orderApplier;
	private final EventRepository eventRepository;

	public Order getById(Long id) {
		return orderApplier.apply(id);
	}

}
