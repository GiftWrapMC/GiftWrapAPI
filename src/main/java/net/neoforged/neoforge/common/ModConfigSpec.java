package net.neoforged.neoforge.common;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

public class ModConfigSpec
{
	public static class Builder
	{
		public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> func)
		{
			return Pair.of(func.apply(this), build());
		}
		
		public ModConfigSpec build()
		{
			return new ModConfigSpec();
		}
		
		public Builder comment(String comment)
		{
			return this;
		}
		
		public IntValue defineInRange(String path, int defaultValue, int min, int max)
		{
			return new IntValue(() -> defaultValue);
		}
	}
	
	public static class ConfigValue<T> implements Supplier<T>
	{
		Supplier<T> defaultSupplier;
		
		public ConfigValue(Supplier<T> defaultSupplier)
		{
			this.defaultSupplier = defaultSupplier;
		}
		
		@Override
		public T get()
		{
			return defaultSupplier.get();
		}
		
	}
	
	public static class IntValue extends ConfigValue<Integer>
	{
		IntValue(Supplier<Integer> defaultSupplier)
		{
			super(defaultSupplier);
		}
	}
}
