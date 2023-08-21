package com.wolfhack.eventsourcingcqrs.applier;

import com.wolfhack.eventsourcingcqrs.interfaces.SnapshotApplierContainer;
import com.wolfhack.eventsourcingcqrs.annotations.Applier;
import com.wolfhack.eventsourcingcqrs.event.Buy;
import com.wolfhack.eventsourcingcqrs.model.Order;
import com.wolfhack.eventsourcingcqrs.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

@Applier
@RequiredArgsConstructor
public class OrderApplier extends SnapshotApplierContainer<Order, Long> {

	private final OrderRepository orderRepository;

	@Applier
	public Order apply(Order order, Buy command) {
		if (order == null) {
			order = new Order();
		}

		if (order.getUsername() != null){
			if (!order.getUsername().equals(command.getUsername())) {
				throw new RuntimeException("Different users buy same order");
			}
		}

		order.setId(command.getEventId());
		order.setLastDate(command.getCreated());
		order.setUsername(command.getUsername());
		order.setPrice(order.getPrice() + command.getPrice());

		return order;
	}

	@Override
	public Class getApplyingClass() {
		return Long.class;
	}

	@Override
	protected Order getApplyingObjectById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	protected void saveApplyingObject(Order applyingObject) {
		orderRepository.save(applyingObject);
	}

}
