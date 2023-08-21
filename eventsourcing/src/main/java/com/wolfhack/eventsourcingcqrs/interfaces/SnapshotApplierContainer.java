package com.wolfhack.eventsourcingcqrs.interfaces;

import com.wolfhack.eventsourcingcqrs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class SnapshotApplierContainer<T extends com.wolfhack.eventsourcingcqrs.interfaces.ApplyingObject, I> extends com.wolfhack.eventsourcingcqrs.interfaces.ApplierContainer<T> implements ApplicationListener<TakeSnapshotApplicationEvent> {

	@Autowired
	private com.wolfhack.eventsourcingcqrs.interfaces.CommandService<I> commandService;

	protected abstract T getApplyingObjectById(I id);

	protected abstract void saveApplyingObject(T applyingObject);

	public T apply(I id) {
		T applyingObject = getApplyingObjectById(id);

		List<? extends com.wolfhack.eventsourcingcqrs.interfaces.Command> commands = applyingObject != null ?
				commandService.getCommandsByIdAndDate(id, applyingObject.getLastDate()) :
				commandService.getCommandsById(id);

		if (commands.isEmpty()) {
			return applyingObject;
		}

		applyingObject = apply(applyingObject, commands);
		saveApplyingObject(applyingObject);
		return applyingObject;
	}

	@Override
	public void onApplicationEvent(TakeSnapshotApplicationEvent event) {
		apply((I) event.getId());
	}
}
