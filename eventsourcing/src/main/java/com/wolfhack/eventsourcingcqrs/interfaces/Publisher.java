package com.wolfhack.eventsourcingcqrs.interfaces;

import java.util.List;
import java.util.function.Consumer;

public interface Publisher {

	void publish(com.wolfhack.eventsourcingcqrs.interfaces.Command message);

	void publish(List<com.wolfhack.eventsourcingcqrs.interfaces.Command> proceed);

	void fail(com.wolfhack.eventsourcingcqrs.interfaces.Command message);

	void fail(List<com.wolfhack.eventsourcingcqrs.interfaces.Command> proceed);

	void addCommandHandler(Class type, Consumer<com.wolfhack.eventsourcingcqrs.interfaces.Command> consumer);

}
