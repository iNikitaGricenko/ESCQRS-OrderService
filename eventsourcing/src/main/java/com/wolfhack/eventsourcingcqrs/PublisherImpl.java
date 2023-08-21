package com.wolfhack.eventsourcingcqrs;

import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import com.wolfhack.eventsourcingcqrs.interfaces.CommandProcessor;
import com.wolfhack.eventsourcingcqrs.interfaces.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class PublisherImpl implements Publisher {

	private final Map<Class<? extends Command>, Collection<Consumer<Command>>> consumerHandlerMap =
			new HashMap<>();

	private final CommandProcessor commandProcessor;

	@Override
	public void publish(Command command) {
		if (command == null) return;
		Collection<Consumer<Command>> handlers = consumerHandlerMap.get(command.getClass());
		if (handlers != null && !handlers.isEmpty()){
			Command processedCommand = commandProcessor.processCommand(command);
			handlers.forEach(x -> x.accept(processedCommand));
		} else {
			throw new IllegalStateException("No handlers exist for class %s".formatted(command.getClass()));
		}
	}

	@Override
	public void publish(List<Command> commands) {
		if (commands == null) return;
		commands.forEach(this::publish);
	}

	@Override
	public void fail(Command command) {
		Collection<Consumer<Command>> handlers = consumerHandlerMap.get(command.getClass());
		if (handlers != null && !handlers.isEmpty()){
			Command processedCommand = commandProcessor.processFailure(command);
			handlers.forEach(x -> x.accept(processedCommand));
		} else {
			throw new IllegalStateException("No handlers exist for class %s".formatted(command.getClass()));
		}
	}

	@Override
	public void fail(List<Command> commands) {
		commands.forEach(this::fail);
	}

	@Override
	public void addCommandHandler(Class type, Consumer<Command> consumer) {
		if (consumerHandlerMap.containsKey(type)) {
			consumerHandlerMap.get(type).add(consumer);
		} else {
			List<Consumer<Command>> handlerList = new LinkedList<>();
			handlerList.add(consumer);
			consumerHandlerMap.put(type, handlerList);
		}
	}

}
