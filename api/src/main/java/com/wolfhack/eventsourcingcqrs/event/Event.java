package com.wolfhack.eventsourcingcqrs.event;

import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Command {

	private UUID id;
	private Long eventId;
	@Builder.Default
	private Instant created = Instant.now();

}
