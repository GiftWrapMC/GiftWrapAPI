package virtuoel.gift_wrap_api;

import java.lang.reflect.Constructor;

import org.quiltmc.loader.api.LanguageAdapter;
import org.quiltmc.loader.api.LanguageAdapterException;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiftWrapApiLanguageAdapter implements LanguageAdapter
{
	public static final Logger LOGGER = LoggerFactory.getLogger("gift_wrap_api");
	
	@Override
	public <T> T create(ModContainer mod, String value, Class<T> type) throws LanguageAdapterException
	{
		if (type == ModInitializer.class)
		{
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
						
						constructor.newInstance(parameters);
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
