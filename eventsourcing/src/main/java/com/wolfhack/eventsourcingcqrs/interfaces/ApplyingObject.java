package com.wolfhack.eventsourcingcqrs.interfaces;

import java.time.Instant;

public interface ApplyingObject<I> {

	I getId();
	Instant getLastDate();

}
