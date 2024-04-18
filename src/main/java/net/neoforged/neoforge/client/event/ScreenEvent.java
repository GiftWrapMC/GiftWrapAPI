package net.neoforged.neoforge.client.event;

import net.minecraft.client.gui.screen.Screen;

public class ScreenEvent
{
	private final Screen screen;
	
	public ScreenEvent(Screen screen)
	{
		this.screen = screen;
	}
	
	public Screen getScreen()
	{
		return screen;
	}
	
	public static abstract class Init extends ScreenEvent
	{
		public Init(Screen screen)
		{
			super(screen);
		}
		
		public static class Post extends Init
		{
			public Post(Screen screen)
			{
				super(screen);
			}
		}
	}
}
