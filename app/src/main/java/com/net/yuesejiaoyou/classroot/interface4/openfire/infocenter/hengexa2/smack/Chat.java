/**
 *
 * Copyright 2003-2007 Jive Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack;




import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArraySet;

//import com.lazysellers.sellers.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
//import com.lazysellers.sellers.utils.LogDetect;
//import com.lazysellers.sellers.utils.LogDetect.DataType;



/**
 * A chat is a series of messages sent between two users. Each chat has a unique
 * thread ID, which is used to track which messages are part of a particular
 * conversation. Some messages are sent without a thread ID, and some clients
 * don't send thread IDs at all. Therefore, if a message without a thread ID
 * arrives it is routed to the most recently created Chat with the message
 * sender.
 * 
 * @author Matt Tucker
 */
public class Chat {

	private ChatManager chatManager;
	private String threadID;
	private String participant;
	// private final Set<MessageListener> listeners = new
	// CopyOnWriteArraySet<MessageListener>();
	private final Set<MessageListener> listeners = new CopyOnWriteArraySet<MessageListener>();

	/**
	 * Creates a new chat with the specified user and thread ID.
	 *
	 * @param chatManager
	 *            the chatManager the chat will use.
	 * @param participant
	 *            the user to chat with.
	 * @param threadID
	 *            the thread ID to use.
	 */
	Chat(ChatManager chatManager, String participant, String threadID) {
		this.chatManager = chatManager;
		this.participant = participant;
		this.threadID = threadID;
	}
	  void deliver(Message message) {
	        // Because the collector and listeners are expecting a thread ID with
	        // a specific value, set the thread ID on the message even though it
	        // probably never had one.
	        message.setThread(threadID);
	        //LogDetect.send(DataType.noType, Utils.seller_id+"=phone="+Utils.android+"收到消息", message.toXML().toString());
	        for (MessageListener listener : listeners) {
	            listener.processMessage(this, message);
	        }
	    }
	public void sendMessage(String text,String from) throws NotConnectedException {
		// 组装message
		Message message = new Message(participant, Message.Type.chat);
		message.setThread(threadID);
		message.setFrom(from);
		message.setBody(text);
		chatManager.sendMessage(this, message);
	}

	public void addMessageListener(MessageListener listener) {
		if (listener == null) {
			return;
		}
		// TODO these references should be weak.
		listeners.add(listener);
	}
	public Collection<MessageListener> getListeners() {
        return Collections.unmodifiableCollection(listeners);
    }
	
}
