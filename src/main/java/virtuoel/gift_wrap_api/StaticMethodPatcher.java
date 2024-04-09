package virtuoel.gift_wrap_api;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.function.BiPredicate;

import org.objectweb.asm.tree.MethodInsnNode;
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
			node.owner = "virtuoel/gift_wrap_api/StaticMethodPatcher";
			node.name = "hookSetUrlStreamHandlerFactory";
			return true;
		}
		
		return false;
	}
}
