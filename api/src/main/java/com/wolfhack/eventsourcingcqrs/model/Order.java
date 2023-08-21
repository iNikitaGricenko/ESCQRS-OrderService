package com.wolfhack.eventsourcingcqrs.model;

import com.wolfhack.eventsourcingcqrs.interfaces.ApplyingObject;
import lombok.Data;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Data
public class Order implements ApplyingObject<Long> {

	private Long id;
	private Instant lastDate;
	private String username;
	private Double price = 0D;
	private Map<String, Integer> borrowers = new HashMap<>();

}
