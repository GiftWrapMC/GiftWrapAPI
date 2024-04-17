package net.neoforged.bus.api;

import java.util.function.Consumer;

public interface IEventBus
{
	default void register(Object target)
	{
		
	}
	
	default <T extends Event> void addListener(Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(Class<T> eventType, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(EventPriority priority, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(EventPriority priority, Class<T> eventType, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(EventPriority priority, boolean receiveCanceled, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(EventPriority priority, boolean receiveCanceled, Class<T> eventType, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(boolean receiveCanceled, Consumer<T> consumer)
	{
		
	}
	
	default <T extends Event> void addListener(boolean receiveCanceled, Class<T> eventType, Consumer<T> consumer)
	{
		
	}
	
	default void unregister(Object object)
	{
		
	}
	
	default <T extends Event> T post(T event)
	{
		return event;
	}
	
	default <T extends Event> T post(EventPriority phase, T event)
	{
		return event;
	}
	
	default void start()
	{
		
	}
}
