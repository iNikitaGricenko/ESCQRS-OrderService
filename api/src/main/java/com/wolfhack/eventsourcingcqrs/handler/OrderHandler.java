package com.wolfhack.eventsourcingcqrs.handler;

import com.wolfhack.eventsourcingcqrs.annotations.Handler;
import com.wolfhack.eventsourcingcqrs.event.Buy;
import com.wolfhack.eventsourcingcqrs.event.ResendMoney;
import com.wolfhack.eventsourcingcqrs.event.SendBuyNotification;
import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import com.wolfhack.eventsourcingcqrs.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@Handler
@RequiredArgsConstructor
public class OrderHandler {

	private final OrderService orderService;

	public List<Command> handle(Buy command) {
		if (command.getUsername() != null && command.getUsername().isBlank()) {
			throw new RuntimeException("Username must not be blank");
		}

		if (command.getPrice() == null || command.getPrice() <= 0) {
			throw new RuntimeException("Price must be more than zero");
		}

		log.info("User {} buying order for price of {}", command.getUsername(), command.getPrice());

		return List.of(SendBuyNotification.builder().username(command.getUsername()).build());
	}

	@Handler
	public List<Command> handle(SendBuyNotification command) {
		log.info("User {} bought an new order", command.getUsername());
		return Collections.emptyList();
	}

	@Handler
	public List<Command> handle(ResendMoney command) {
		log.info("{} gives back {}", command.getUsername(), command.getAmount());
		return Collections.emptyList();
	}

}
