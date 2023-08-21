package com.wolfhack.eventsourcingcqrs.event;

import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendBuyNotification implements Command {
	private String username;
}
