package virtuoel.gift_wrap_api;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Consumer;

import org.quiltmc.loader.api.LanguageAdapter;
import org.quiltmc.loader.api.LanguageAdapterException;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jodah.typetools.TypeResolver;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.RegisterEvent;
import virtuoel.gift_wrap_api.extensions.RegistryFreezeExtensions;

public class GiftWrapApiLanguageAdapter implements LanguageAdapter
{
	public static final Logger LOGGER = LoggerFactory.getLogger("gift_wrap_api");
	public static final Map<String, IEventBus> MOD_BUSSES = new HashMap<>();
	
	@Override
	public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException
	{
		if (type == ModInitializer.class)
		{
			Map<Class<?>, List<Consumer<?>>> modEvents = new HashMap<>();
			
			IEventBus bus = MOD_BUSSES.computeIfAbsent(mod.metadata().id(), $ -> new IEventBus()
			{
				@Override
				public <E> void addListener(Consumer<E> eventHandler)
				{
					Class<?> clazz = TypeResolver.resolveRawArgument(Consumer.class, eventHandler.getClass());
					modEvents.computeIfAbsent(clazz, $ -> new ArrayList<>()).add(eventHandler);
				}
			});
			
			@SuppressWarnings("unchecked")
			T t = (T) new ModInitializer()
			{
				@Override
				public void onInitialize(ModContainer mod)
				{
					try
					{
						final Constructor<?> constructor = Class.forName(value).getDeclaredConstructors()[0];
						final Class<?>[] parameterTypes = constructor.getParameterTypes();
						final Object[] parameters = new Object[parameterTypes.length];

						for (int i = 0; i < parameterTypes.length; i++)
						{
							if (parameterTypes[i] == IEventBus.class)
							{
								parameters[i] = bus;
							}
						}

						constructor.newInstance(parameters);

						@SuppressWarnings({"unchecked", "rawtypes"})
						List<Consumer<RegisterEvent>> registerHandlers = (List<Consumer<RegisterEvent>>) (List) modEvents.computeIfAbsent(RegisterEvent.class, $ -> new ArrayList<>());

						if (!registerHandlers.isEmpty())
						{
							RegistryFreezeExtensions.REGISTRARS.addAll(registerHandlers);
						}
					}
					catch (Throwable e)
					{
						e.printStackTrace();
					}
				}
			};
			return t;
		}
		
		throw new LanguageAdapterException("Failed to create entrypoint for type " + type);
	}
}
