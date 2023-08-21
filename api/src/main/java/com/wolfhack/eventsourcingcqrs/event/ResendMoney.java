package com.wolfhack.eventsourcingcqrs.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResendMoney extends Event {

	private Double amount;
	private String username;
	private String targetUser;

}
