package com.wolfhack.eventsourcingcqrs.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Buy extends Event {

	private Double price;
	private String username;

}
