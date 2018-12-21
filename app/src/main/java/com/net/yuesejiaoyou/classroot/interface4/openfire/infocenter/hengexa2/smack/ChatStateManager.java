package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack;

import java.util.Map;
import java.util.WeakHashMap;





public class ChatStateManager {
	private final Map<Chat, ChatState> chatStates = new WeakHashMap<Chat, ChatState>();
	public static final String NAMESPACE = "http://jabber.org/protocol/chatstates";
	

	private synchronized boolean updateChatState(Chat chat, ChatState newState) {
		ChatState lastChatState = chatStates.get(chat);
		if (lastChatState != newState) {
			chatStates.put(chat, newState);
			return true;
		}
		return false;
	}
	
	
	 private void fireNewChatState(Chat chat, ChatState state) {
         for (MessageListener listener : chat.getListeners()) {
             if (listener instanceof ChatStateListener) {
                 ((ChatStateListener) listener).stateChanged(chat, state);
             }
         }
     }
     
	 private class IncomingMessageInterceptor implements ChatManagerListener, MessageListener {

	        public void chatCreated(final Chat chat, boolean createdLocally) {
	            chat.addMessageListener(this);
	        }

	        public void processMessage(Chat chat, Message message) {
	            PacketExtension extension = message.getExtension(NAMESPACE);
	            if (extension == null) {
	                return;
	            }

	            ChatState state;
	            try {
	                state = ChatState.valueOf(extension.getElementName());
	            }
	            catch (Exception ex) {
	                return;
	            }

	            fireNewChatState(chat, state);
	        }
	    }
	 
	 
}