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



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.StringUtils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPTCPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Message.Type;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;









/**
 * The chat manager keeps track of references to all current chats. It will not
 * hold any references in memory on its own so it is necessary to keep a
 * reference to the chat object itself. To be made aware of new chats, register
 * a listener by calling {@link #addChatListener(ChatManagerListener)}.
 *
 * @author Alexander Wenckus
 */
public class ChatManager extends Manager {
	// private static final Map<XMPPConnection, ChatManager> INSTANCES = new
	// WeakHashMap<XMPPConnection, ChatManager>();
	private static final Map<XMPPConnection, ChatManager> INSTANCES = new WeakHashMap<XMPPConnection, ChatManager>();
	public ChatManager(XMPPTCPConnection connection) {
		super(connection);
		PacketFilter filter = new PacketFilter() {
            public boolean accept(Packet packet) {
                if (!(packet instanceof Message)) {
                    return false;
                }
                Message.Type messageType = ((Message) packet).getType();
                return (messageType == Type.chat) || (normalIncluded ? messageType == Type.normal : false);
                //return true;
            }
        };
        
        // Add a listener for all message packets so that we can deliver
        // messages to the best Chat instance available.
        connection.addPacketListener(new PacketListener() {
            public void processPacket(Packet packet) {

				////////////////////------------------>>>>>>>>
				LogDetect.send(DataType.specialType,"xmpp内二层:","5");
				////////////////////------------------>>>>>>>>
                Message message = (Message) packet;
                Chat chat;
//                if (message.getThread() == null) {
                	chat = getUserChat(message.getFrom());
//                }
//                else {
//                    chat = getThreadChat(message.getThread());
//                }

                if(chat == null) {
                    chat = createChat(message);
                }
                // The chat could not be created, abort here
                //if (chat == null)
                    //return;
				////////////////////------------------>>>>>>>>
				LogDetect.send(DataType.specialType,"xmpp内二层:","6");
				////////////////////------------------>>>>>>>>
                deliverMessage(chat, message);
            }
        }, filter);
        INSTANCES.put(connection, this);
		// TODO Auto-generated constructor stub
	}
	private void deliverMessage(Chat chat, Message message) {
        // Here we will run any interceptors
		//LogDetect.send(DataType.noType, Utils.seller_id+"=phone="+Utils.android+"收到消息", message.toXML().toString());
        chat.deliver(message);
    }
	 public static synchronized ChatManager getInstanceFor(XMPPTCPConnection connection) {
	        ChatManager manager = INSTANCES.get(connection);
	        if (manager == null)
	            manager = new ChatManager(connection);
	        return manager;
	    }
	/**
     * Creates a new {@link Chat} based on the message. May returns null if no chat could be
     * created, e.g. because the message comes without from.
     *
     * @param message
     * @return a Chat or null if none can be created
     */
    private Chat createChat(Message message) {
        String userJID = message.getFrom();
        // According to RFC6120 8.1.2.1 4. messages without a 'from' attribute are valid, but they
        // are of no use in this case for ChatManager
        if (userJID == null) {
            return null;
        }
        String threadID = message.getThread();
        if(threadID == null) {
            threadID = nextID();
        }

        return createChat(userJID, threadID, false);
    }
	 /**
     * Register a new listener with the ChatManager to recieve events related to chats.
     *
     * @param listener the listener.
     */
    public void addChatListener(ChatManagerListener listener) {
        chatManagerListeners.add(listener);
    }
	/**
	 * Sets the default behaviour for allowing 'normal' messages to be used in
	 * chats. As some clients don't set the message type to chat, the type
	 * normal has to be accepted to allow chats with these clients.
	 */
	private static boolean defaultIsNormalInclude = true;

	/**
	 * Sets the default behaviour for how to match chats when there is NO thread
	 * id in the incoming message.
	 */
	private static MatchMode defaultMatchMode = MatchMode.BARE_JID;

	/**
	 * Returns the ChatManager instance associated with a given XMPPConnection.
	 *
	 * @param connection
	 *            the connection used to look for the proper
	 *            ServiceDiscoveryManager.
	 * @return the ChatManager associated with a given XMPPConnection.
	 */

	/**
	 * Defines the different modes under which a match will be attempted with an
	 * existing chat when the incoming message does not have a thread id.
	 */
	public enum MatchMode {
		/**
		 * Will not attempt to match, always creates a new chat.
		 */
		NONE,
		/**
		 * Will match on the JID in the from field of the message.
		 */
		SUPPLIED_JID,
		/**
		 * Will attempt to match on the JID in the from field, and then attempt
		 * the base JID if no match was found. This is the most lenient
		 * matching.
		 */
		BARE_JID;
	}

	/**
	 * Determines whether incoming messages of type normal can create chats.
	 */
	private boolean normalIncluded = defaultIsNormalInclude;

	/**
	 * Determines how incoming message with no thread will be matched to
	 * existing chats.
	 */
	private Set<ChatManagerListener> chatManagerListeners = new CopyOnWriteArraySet<ChatManagerListener>();
	private MatchMode matchMode = defaultMatchMode;
	private Map<String, Chat> jidChats = Collections
			.synchronizedMap(new HashMap<String, Chat>());
	private Map<String, Chat> baseJidChats = Collections
			.synchronizedMap(new HashMap<String, Chat>());
	/**
	 * Maps thread ID to chat.
	 */
	private Map<String, Chat> threadChats = Collections
			.synchronizedMap(new HashMap<String, Chat>());

	void sendMessage(Chat chat, Message message) throws NotConnectedException {
		/*
		 * for(Map.Entry<PacketInterceptor, PacketFilter> interceptor :
		 * interceptors.entrySet()) { PacketFilter filter =
		 * interceptor.getValue(); if(filter != null && filter.accept(message))
		 * { interceptor.getKey().interceptPacket(message); } } // Ensure that
		 * messages being sent have a proper FROM value if (message.getFrom() ==
		 * null) { message.setFrom(connection().getUser()); }
		 */
		connection().sendPacket(message);
	}

	public Chat createChat(String userJID, MessageListener listener) {
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"内一层:","12");
		////////////////////------------------>>>>>>>>
		return createChat(userJID, null, listener);
	}

	public Chat createChat(String userJID, String thread,
			MessageListener listener) {
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"内一层:","11");
		////////////////////------------------>>>>>>>>
		if (thread == null) {
			thread = nextID();
		}
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"内一层:","1");
		////////////////////------------------>>>>>>>>
		Chat chat = threadChats.get(thread);
		if (chat != null) {
			throw new IllegalArgumentException("ThreadID is already used");
		}
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"内一层:","2");
		////////////////////------------------>>>>>>>>
		chat = createChat(userJID, thread, true);
		chat.addMessageListener(listener);
		return chat;
	}

	private Chat createChat(String userJID, String threadID,
			boolean createdLocally) {
		Chat chat = new Chat(this, userJID, threadID);
		threadChats.put(threadID, chat);
		jidChats.put(userJID, chat);
		baseJidChats.put(StringUtils.parseBareAddress(userJID), chat);
		////////////////////------------------>>>>>>>>
		LogDetect.send(DataType.specialType,"内一层:","3");
		////////////////////------------------>>>>>>>>
		for (ChatManagerListener listener : chatManagerListeners) {
			listener.chatCreated(chat, createdLocally);
		}

		return chat;
	}

	private static String nextID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Try to get a matching chat for the given user JID, based on the
	 * {@link MatchMode}. <li>NONE - return null <li>SUPPLIED_JID - match the
	 * jid in the from field of the message exactly. <li>BARE_JID - if not match
	 * for from field, try the bare jid.
	 * 
	 * @param userJID
	 *            jid in the from field of message.
	 * @return Matching chat, or null if no match found.
	 */
	public Chat getUserChat(String userJID) {
		if (matchMode == MatchMode.NONE) {
			return null;
		}
		// According to RFC6120 8.1.2.1 4. messages without a 'from' attribute
		// are valid, but they
		// are of no use in this case for ChatManager
		if (userJID == null) {
			return null;
		}
		Chat match = jidChats.get(userJID);

		if (match == null && (matchMode == MatchMode.BARE_JID)) {
			match = baseJidChats.get(StringUtils.parseBareAddress(userJID));
		}
		return match;
	}

	public Chat getThreadChat(String thread) {
		return threadChats.get(thread);
	}

	/**
	 * Register a new listener with the ChatManager to recieve events related to
	 * chats.
	 *
	 * @param listener
	 *            the listener.
	 */

}
