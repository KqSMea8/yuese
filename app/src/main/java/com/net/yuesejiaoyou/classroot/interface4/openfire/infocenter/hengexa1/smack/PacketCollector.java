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

package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;

import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack.XMPPException.XMPPErrorException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Packet;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.PacketFilter;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NoResponseException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.XMPPConnection;






/**
 * Provides a mechanism to collect packets into a result queue that pass a
 * specified filter. The collector lets you perform blocking and polling
 * operations on the result queue. So, a PacketCollector is more suitable to
 * use than a {@link PacketListener} when you need to wait for a specific
 * result.<p>
 *
 * Each packet collector will queue up a configured number of packets for processing before
 * older packets are automatically dropped.  The default number is retrieved by 
 * {@link SmackConfiguration#getPacketCollectorSize()}.
 *
 * @see XMPPConnection#createPacketCollector(PacketFilter)
 * @author Matt Tucker
 */
public class PacketCollector {

    private static final Logger LOGGER = Logger.getLogger(PacketCollector.class.getName());
    private PacketFilter packetFilter;
    private ArrayBlockingQueue<Packet> resultQueue;
    private XMPPConnection connection;
    private boolean cancelled = false;
    //private PacketFilter packetFilter;
    public PacketCollector(XMPPConnection connection, PacketFilter packetFilter) {
        this(connection, packetFilter, 1000/*SmackConfiguration.getPacketCollectorSize()*/);
    }
    protected PacketCollector(XMPPConnection connection, PacketFilter packetFilter, int maxSize) {
        this.connection = connection;
        this.packetFilter = packetFilter;
        this.resultQueue = new ArrayBlockingQueue<Packet>(maxSize);
    }
    
    
    /**
     * Returns the next available packet. The method call will block until a packet is available or
     * the connections reply timeout has elapsed. If the timeout elapses without a result,
     * <tt>null</tt> will be returned. This method does also cancel the PacketCollector.
     * 
     * @return the next available packet.
     * @throws XMPPErrorException in case an error response.
     * @throws NoResponseException if there was no response from the server.
     */
    public Packet nextResultOrThrow() throws NoResponseException, XMPPErrorException {
        return nextResultOrThrow(connection.getPacketReplyTimeout());
    }
    
    
    public Packet nextResultOrThrow(long timeout) throws NoResponseException, XMPPErrorException {
        Packet result = nextResult(timeout);
        /*cancel();
        if (result == null) {
            throw new NoResponseException();
        }

        XMPPError xmppError = result.getError();
        if (xmppError != null) {
            throw new XMPPErrorException(xmppError);
        }*/

        return result;
    }
    public Packet nextResult(final long timeout) {
        Packet res = null;
        long remainingWait = timeout;
        final long waitStart = System.currentTimeMillis();
        while (res == null && remainingWait > 0) {
            try {
                res = resultQueue.poll(remainingWait, TimeUnit.MILLISECONDS);
                remainingWait = timeout - (System.currentTimeMillis() - waitStart);
            } catch (InterruptedException e) {
                LOGGER.log(Level.FINE, "nextResult was interrupted", e);
            }
        }
        return res;
    }
    
    /**
     * Returns the next available packet. The method call will block until the connection's default
     * timeout has elapsed.
     * 
     * @return the next availabe packet.
     */
    public Packet nextResult() {
        return nextResult(connection.getPacketReplyTimeout());
    }
    
    
    /**
     * Processes a packet to see if it meets the criteria for this packet collector.
     * If so, the packet is added to the result queue.
     *
     * @param packet the packet to process.
     */
    
    
    public void processPacket(Packet packet) {
        if (packet == null) {
            return;
        }

        ////////////////////------------------>>>>>>>>
        LogDetect.send(DataType.specialType,"xmpp内二:","2");
        ////////////////////------------------>>>>>>>>
        //if (packetFilter == null || packetFilter.accept(packet)) {
        	while (!resultQueue.offer(packet)) {
        		// Since we know the queue is full, this poll should never actually block.
        		resultQueue.poll();
        	}
        //}
    }
}
