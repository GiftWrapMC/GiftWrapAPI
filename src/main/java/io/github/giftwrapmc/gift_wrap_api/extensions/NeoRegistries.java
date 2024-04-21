package io.github.giftwrapmc.gift_wrap_api.extensions;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NeoRegistries {

	public static final List<Consumer<RegisterEvent>> QUEUED_CONTENT = new ArrayList<>();

	public static void partiallyFreezeRegistries() {
		((RegistryFreezeExtensions<?>) Registries.REGISTRIES).partiallyFreeze();

		for(Registry<?> registry : Registries.REGISTRIES) {
			((RegistryFreezeExtensions<?>) registry).partiallyFreeze();
		}
	}

	public static void acceptQueuedContent() {
		if (!NeoRegistries.QUEUED_CONTENT.isEmpty()) {
			for (Registry<?> registry : Registries.REGISTRIES) {
				NeoRegistries.QUEUED_CONTENT.forEach(consumer -> {
					consumer.accept(new RegisterEvent(registry.getKey(), registry));
				});
			}
		}
	}

	public static void fullyFreezeRegistries() {
		Registries.REGISTRIES.freeze();

		for(Registry<?> registry : Registries.REGISTRIES) {
			registry.freeze();
		}
	}
}
