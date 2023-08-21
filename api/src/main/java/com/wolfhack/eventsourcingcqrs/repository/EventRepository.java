package com.wolfhack.eventsourcingcqrs.repository;

import com.wolfhack.eventsourcingcqrs.event.Event;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Repository
public class EventRepository {

	private final List<Event> eventList = new LinkedList<>();

	public void save(Event event) {
		eventList.add(event);
	}

	public List<Event> findById(Long id) {
		return eventList.stream()
				.filter(event -> event.getEventId().equals(id))
				.sorted(Comparator.comparing(Event::getCreated))
				.toList();
	}

	public List<Event> findById(Long id, Instant createdAfter) {
		return eventList.stream()
				.filter(event -> event.getEventId().equals(id))
				.filter(event -> event.getCreated().isAfter(createdAfter))
				.sorted(Comparator.comparing(Event::getCreated))
				.toList();
	}

}
