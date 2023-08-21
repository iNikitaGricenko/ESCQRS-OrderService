package com.wolfhack.eventsourcingcqrs.interfaces;

public interface CommandProcessor {

	com.wolfhack.eventsourcingcqrs.interfaces.Command processCommand(com.wolfhack.eventsourcingcqrs.interfaces.Command command);

	com.wolfhack.eventsourcingcqrs.interfaces.Command processFailure(com.wolfhack.eventsourcingcqrs.interfaces.Command command);

}
