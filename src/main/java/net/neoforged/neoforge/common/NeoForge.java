package net.neoforged.neoforge.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import net.jodah.typetools.TypeResolver;
import net.neoforged.bus.api.IEventBus;

public class NeoForge
{
	private static final Map<Class<?>, List<Consumer<?>>> EVENTS = new HashMap<>();
	
	public static final IEventBus EVENT_BUS = new IEventBus()
	{
		@Override
		public <E> void addListener(Consumer<E> eventHandler)
		{
			Class<?> clazz = TypeResolver.resolveRawArgument(Consumer.class, eventHandler.getClass());
			EVENTS.computeIfAbsent(clazz, $ -> new ArrayList<>()).add(eventHandler);
		}
	};
}
