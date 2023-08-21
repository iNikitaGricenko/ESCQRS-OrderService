package com.wolfhack.eventsourcingcqrs.repository;

import com.wolfhack.eventsourcingcqrs.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

	private final List<Order> orders = new ArrayList<>();

	public Order findById(Long id){
		return orders.stream()
				.filter(order-> order.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public void save(Order order){
		orders.add(order);
	}

}
