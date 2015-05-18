package me.chris.SimpleChat;
/**
 * @author Christopher Pybus
 * @date Feb 29, 2012
 * @file SimpleChatHelperMethods.java
 * @package me.chris.SimpleChat
 * 
 * @purpose 
 */

public class SimpleChatChatState
{
	public static String chatState;
	
	public SimpleChatChatState()
	{
		chatState = "on";
	}
	
	public static String getChatState()
	{
		if(chatState.equalsIgnoreCase("on"))
			return "on";
		else if(chatState.equalsIgnoreCase("off"))
			return "off";
		else
			return "on";
	}
	
	public static void setChatState(String state)
	{
		if(state.equalsIgnoreCase("on"))
		    chatState = "on";
		else if(state.equalsIgnoreCase("off"))
			chatState = "off";
		else
			chatState = "on";
	}
}
