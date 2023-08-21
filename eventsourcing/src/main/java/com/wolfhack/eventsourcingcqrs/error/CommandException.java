package com.wolfhack.eventsourcingcqrs.error;

import com.wolfhack.eventsourcingcqrs.interfaces.Command;
import lombok.Getter;

@Getter
public class CommandException extends RuntimeException {

	private final Command command;

	public CommandException(Command command) {
		this.command = command;
	}

	public CommandException(Command command, Throwable cause) {
		super(cause);
		this.command = command;
	}

	public CommandException(Command command, String message) {
		super(message);
		this.command = command;
	}

}
