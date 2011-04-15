package com.binomed.cineshowtime.client.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.event.EventTypeEnum;
import com.binomed.cineshowtime.client.event.HandledEvent;

public class EventHandlerBus {

	private Map<EventTypeEnum, List<EventHandler>> eventListeners;

	public void fireEvent(HandledEvent baseEvent) {
		if (eventListeners == null) {
			return;
		}
		List<EventHandler> eventListenerList = eventListeners.get(baseEvent.getEventEnum());
		if (eventListenerList != null) {
			for (EventHandler eventHandler : eventListenerList) {
				eventHandler.handleEvent(baseEvent);
			}
		}
	}

	public void fireEventError(EventTypeEnum type, Throwable error) {
		if (eventListeners == null) {
			return;
		}
		List<EventHandler> eventListenerList = eventListeners.get(type);
		if (eventListenerList != null) {
			for (EventHandler eventHandler : eventListenerList) {
				eventHandler.handleError(error);
			}
		}
	}

	public synchronized List<EventHandler> put(EventTypeEnum eventTypeEnum, EventHandler eventHandler) {
		if (eventListeners == null) {
			eventListeners = new HashMap<EventTypeEnum, List<EventHandler>>();
		}
		List<EventHandler> eventListenerList = eventListeners.get(eventTypeEnum);
		if (eventListenerList == null) {
			eventListenerList = new ArrayList<EventHandler>();
			eventListeners.put(eventTypeEnum, eventListenerList);
		}
		if (!eventListenerList.contains(eventHandler)) {
			eventListenerList.add(eventHandler);
		}
		return eventListenerList;
	}

	public synchronized void remove(EventTypeEnum eventTypeEnum, EventHandler eventHandler) {
		if (eventListeners == null) {
			return;
		}
		List<EventHandler> eventListenerList = eventListeners.get(eventTypeEnum);
		if (eventListenerList == null) {
			return;
		}
		eventListenerList.remove(eventHandler);

	}
}
