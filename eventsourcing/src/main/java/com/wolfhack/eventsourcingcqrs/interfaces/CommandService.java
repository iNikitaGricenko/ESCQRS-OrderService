package com.wolfhack.eventsourcingcqrs.interfaces;

import java.time.Instant;
import java.util.List;

public interface CommandService<I> {
	List<? extends com.wolfhack.eventsourcingcqrs.interfaces.Command> getCommandsByIdAndDate(I id, Instant lastAppliedEventCreateDate);

	List<? extends com.wolfhack.eventsourcingcqrs.interfaces.Command> getCommandsById(I id);
}
