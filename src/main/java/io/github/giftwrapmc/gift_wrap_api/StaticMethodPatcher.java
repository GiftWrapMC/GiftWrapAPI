package io.github.giftwrapmc.gift_wrap_api;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.function.BiPredicate;

import org.objectweb.asm.tree.MethodInsnNode;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.impl.filesystem.DelegatingUrlStreamHandlerFactory;

public class StaticMethodPatcher implements BiPredicate<String, MethodInsnNode>
{
	public static void hookSetUrlStreamHandlerFactory(final URLStreamHandlerFactory factory)
	{
		try
		{
			DelegatingUrlStreamHandlerFactory.class.getMethod("appendFactory", URLStreamHandlerFactory.class).invoke(null, factory);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean test(final String t, final MethodInsnNode u)
	{
		return patch(t, u);
	}
	
	public static boolean patch(final String className, final MethodInsnNode node)
	{
		if ("setURLStreamHandlerFactory".equals(node.name) && URL.class.getName().equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/StaticMethodPatcher";
			node.name = "hookSetUrlStreamHandlerFactory";
			return true;
		}
		else if ("create".equals(node.name) && BLOCK_TAGS.equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/hooks/BlockTagsHooks";
			return true;
		}
		else if ("getColor".equals(node.name) && DYE_COLOR.equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/hooks/DyeColorHooks";
			return true;
		}
		else if ("create".equals(node.name) && FLUID_TAGS.equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/hooks/FluidTagsHooks";
			return true;
		}
		else if ("builder".equals(node.name) && ITEM_GROUP.equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/hooks/ItemGroupHooks";
			return true;
		}
		else if ("create".equals(node.name) && ITEM_TAGS.equals(node.owner.replace('/', '.')))
		{
			node.owner = "io/github/giftwrapmc/gift_wrap_api/hooks/ItemTagsHooks";
			return true;
		}
		
		return false;
	}
	
	public static final String BLOCK_TAGS = mapClass("net.minecraft.class_3481");
	public static final String DYE_COLOR = mapClass("net.minecraft.class_1767");
	public static final String FLUID_TAGS = mapClass("net.minecraft.class_3486");
	public static final String ITEM_GROUP = mapClass("net.minecraft.class_1761");
	public static final String ITEM_TAGS = mapClass("net.minecraft.class_3489");
	
	private static String mapClass(final String intermediary)
	{
		return QuiltLoader.getMappingResolver().mapClassName("intermediary", intermediary);
	}
}
