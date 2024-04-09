package net.neoforged.bus.api;

import java.util.function.Consumer;

public interface IEventBus
{
	default <E> void addListener(Consumer<E> eventHandler)
	{
		
	}
}
