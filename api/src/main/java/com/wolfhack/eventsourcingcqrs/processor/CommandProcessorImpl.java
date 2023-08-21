package com.wolfhack.eventsourcingcqrs.processor;

import com.wolfhack.eventsourcingcqrs.event.Event;
import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import com.wolfhack.eventsourcingcqrs.interfaces.CommandProcessor;
import com.wolfhack.eventsourcingcqrs.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandProcessorImpl implements CommandProcessor {

	private final EventRepository eventRepository;

	@Override
	public Command processCommand(Command command) {
		if (command instanceof Event) {
			eventRepository.save((Event)command);
		}
		return command;
	}

	@Override
	public Command processFailure(Command command) {
		log.info("Handle exception");
		return command;
	}
}
