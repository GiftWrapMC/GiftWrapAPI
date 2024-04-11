package virtuoel.gift_wrap_api.extensions;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface RegistryFreezeExtensions<T> {

	List<Consumer<RegisterEvent>> REGISTRARS = new ArrayList<>();

	Registry<T> partiallyFreeze();

	RegistryEntry.Reference<T> neoSet(int rawId, RegistryKey<T> key, T value, Lifecycle lifecycle);

	RegistryEntry.Reference<T> neoGetOrCreateEntry(RegistryKey<T> key);

	RegistryEntry.Reference<T> neoCreateEntry(T value);

	RegistryEntryLookup<T> createMutableEntryLookup();

	static void reminder() {
		for (Registry<?> registry : Registries.REGISTRIES) {
			RegistryFreezeExtensions.REGISTRARS.forEach(consumer -> {
				consumer.accept(new RegisterEvent(registry.getKey(), registry));
			});
		}
	}
}
