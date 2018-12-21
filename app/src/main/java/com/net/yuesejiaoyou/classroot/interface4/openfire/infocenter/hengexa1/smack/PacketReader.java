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



import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

//import com.lazysellers.sellers.core.Utils;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;

import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.IQ;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Packet;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.PacketParserUtils;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NoResponseException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.UnparsablePacket;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
//import com.lazysellers.sellers.utils.LogDetect;
//import com.lazysellers.sellers.utils.LogDetect.DataType;

import android.util.Log;



/**
 * Listens for XML traffic from the XMPP server and parses it into packet objects.
 * The packet reader also invokes all packet listeners and collectors.<p>
 *
 * @see XMPPConnection#createPacketCollector
 * @see XMPPConnection#addPacketListener
 * @author Matt Tucker
 */
class PacketReader {

    private Thread readerThread;
    private XmlPullParser parser;
    private XMPPTCPConnection connection;
    //private XmlPullParser parser;
    private int i=0;
    /**
     * Set to true if the last features stanza from the server has been parsed. A XMPP connection
     * handshake can invoke multiple features stanzas, e.g. when TLS is activated a second feature
     * stanza is send by the server. This is set to true once the last feature stanza has been
     * parsed.
     */
    private volatile boolean lastFeaturesParsed;

    volatile boolean done;
    int connectionCounterValue;
    protected PacketReader(final XMPPTCPConnection connection) throws SmackException {
    	AtomicInteger connectionCounter = new AtomicInteger(0);
		this.connectionCounterValue = connectionCounter.getAndIncrement();
        this.connection = connection;
        this.init();
        i=0;
    }

    /**
     * Initializes the reader in order to be used. The reader is initialized during the
     * first connection and when reconnecting due to an abruptly disconnection.
     *
     * @throws SmackException if the parser could not be reset.
     */
    protected void init() throws SmackException {
        done = false;
        lastFeaturesParsed = false;

        readerThread = new Thread() {
            public void run() {
                parsePackets(this);
            }
        };
        readerThread.setName("Smack Packet Reader (" + /*connection.getConnectionCounter()*/connectionCounterValue + ")");
        readerThread.setDaemon(true);

        resetParser();
    }
    private void resetParser() throws SmackException {
        try {
            parser = PacketParserUtils.newXmppParser();
            parser.setInput(connection.getReader());
        }
        catch (XmlPullParserException e) {
            throw new SmackException(e);
        }
    }
    /**
     * Starts the packet reader thread and returns once a connection to the server
     * has been established or if the server's features could not be parsed within
     * the connection's PacketReplyTimeout.
     *
     * @throws NoResponseException if the server fails to send an opening stream back
     *      within packetReplyTimeout.
     * @throws IOException 
     */
    synchronized public void startup() throws NoResponseException, IOException {
        readerThread.start();

        try {
            // Wait until either:
            // - the servers last features stanza has been parsed
            // - an exception is thrown while parsing
            // - the timeout occurs
        	
            //wait(connection.getPacketReplyTimeout());
            wait(5000);
        }
        catch (InterruptedException ie) {
            // Ignore.
        }
        if (!lastFeaturesParsed) {
            //connection.throwConnectionExceptionOrNoResponse();
        }
    }

    /**
     * Shuts the packet reader down. This method simply sets the 'done' flag to true.
     */
    public void shutdown() {
        done = true;
    }
    private void parsePackets(Thread thread) {
        try {
            //int eventType = parser.getEventType();
            ////////////////////------------------>>>>>>>>
            LogDetect.send(DataType.specialType,"xmpp内二:","3");
            ////////////////////------------------>>>>>>>>
        	int eventType =0;
            do {
            	Log.e("jj", "收到消息"+"11");
                if (eventType == XmlPullParser.START_TAG) {
                    int parserDepth = parser.getDepth();
                    //ParsingExceptionCallback callback = connection.getParsingExceptionCallback();
                    if (parser.getName().equals("message")) {
                    	Log.e("jj", "收到消息"+"44");
                    	
                        Packet packet;
                        try {
                            packet = PacketParserUtils.parseMessage(parser);
                            Log.e("jj", "收到消息"+packet.toXML().toString());
                            //LogDetect.send(DataType.noType, Utils.seller_id+"=phone="+Utils.android+"收到消息", packet.toXML().toString());
                        } catch (Exception e) {
                            LogDetect.writeCause(LogDetect.DataType.noType,"TT","02",e);
                            String content = PacketParserUtils.parseContentDepth(parser, parserDepth);
                            UnparsablePacket message = new UnparsablePacket(content, e);
                            //if (callback != null) {
                            //    callback.handleUnparsablePacket(message);
                            //}
                            // The parser is now at the end tag of the unparsable stanza. We need to advance to the next
                            // start tag in order to avoid an exception which would again lead to the execution of the
                            // catch block becoming effectively an endless loop.
                            eventType = parser.next();
                            continue;
                        }
                        i++;
                        if(!packet.toString().contains("AnotherLogin卍over_login")){
                        	connection.getWriter().write("<a xmlns='urn:xmpp:sm:3' h='"+i+"'/>");
                            //connection.getWriter().write("123456");
                            //connection.getWriter().write("<message id='0jBzw-2' to='228@192.168.0.4' from='147852@192.168.0.4' type='chat'><body>测试消息卍chat_text卍2016-06-15 13:15:53</body><thread>c1349856-3aab-4eb7-a1cc-d0c18d7af425</thread></message>");
                            connection.getWriter().flush();
                        }                        
                        //LogDetect.send(DataType.noType, Utils.seller_id+"=phone="+Utils.android+"收到消息", packet.toXML().toString());
                        connection.processPacket(packet);
                    }
                     else if (parser.getName().equals("iq")) {
                        IQ iq;
                        try {

                            //--------------------------------------------------------------------
                            // 服务器发来心跳包计数写入本地sharepreference
                            Log.v("TT","IQ packet");
                            if(Util.imManager != null) {
                                LogDetect.send(LogDetect.DataType.basicType,"PacketReader----iq-----","iq包来了");
                                Util.imManager.updateOpenfireHeartbeat();
                            }
                            //--------------------------------------------------------------------

                            iq = PacketParserUtils.parseIQ(parser, connection);
                        } catch (Exception e) {
                            LogDetect.writeCause(LogDetect.DataType.noType,"TT","01",e);
                            String content = PacketParserUtils.parseContentDepth(parser, parserDepth);
                            UnparsablePacket message = new UnparsablePacket(content, e);
                            /*01066 if (callback != null) {
                                callback.handleUnparsablePacket(message);
                            }*/
                            // The parser is now at the end tag of the unparsable stanza. We need to advance to the next
                            // start tag in order to avoid an exception which would again lead to the execution of the
                            // catch block becoming effectively an endless loop.
                            eventType = parser.next();
                            continue;
                        }
                        connection.sendPacketInternal(iq);
                        connection.processPacket(iq);
                    }
                     /*else if (parser.getName().equals("presence")) {
                        Presence presence;
                        try {
                            presence = PacketParserUtils.parsePresence(parser);
                        } catch (Exception e) {
                            String content = PacketParserUtils.parseContentDepth(parser, parserDepth);
                            UnparsablePacket message = new UnparsablePacket(content, e);
                            if (callback != null) {
                                callback.handleUnparsablePacket(message);
                            }
                            // The parser is now at the end tag of the unparsable stanza. We need to advance to the next
                            // start tag in order to avoid an exception which would again lead to the execution of the
                            // catch block becoming effectively an endless loop.
                            eventType = parser.next();
                            continue;
                        }
                        connection.processPacket(presence);
                    }
                    // We found an opening stream. Record information about it, then notify
                    // the connectionID lock so that the packet reader startup can finish.
                    else if (parser.getName().equals("stream")) {
                        // Ensure the correct jabber:client namespace is being used.
                        if ("jabber:client".equals(parser.getNamespace(null))) {
                            // Get the connection id.
                            for (int i=0; i<parser.getAttributeCount(); i++) {
                                if (parser.getAttributeName(i).equals("id")) {
                                    // Save the connectionID
                                    connection.connectionID = parser.getAttributeValue(i);
                                }
                            }
                        }
                    }
                    else if (parser.getName().equals("error")) {
                        throw new StreamErrorException(PacketParserUtils.parseStreamError(parser));
                    }
                    else if (parser.getName().equals("features")) {
                        parseFeatures(parser);
                    }
                    else if (parser.getName().equals("proceed")) {
                        // Secure the connection by negotiating TLS
                        connection.proceedTLSReceived();
                        // Reset the state of the parser since a new stream element is going
                        // to be sent by the server
                        resetParser();
                    }
                    else if (parser.getName().equals("failure")) {
                        String namespace = parser.getNamespace(null);
                        if ("urn:ietf:params:xml:ns:xmpp-tls".equals(namespace)) {
                            // TLS negotiation has failed. The server will close the connection
                            throw new Exception("TLS negotiation has failed");
                        }
                        else if ("http://jabber.org/protocol/compress".equals(namespace)) {
                            // Stream compression has been denied. This is a recoverable
                            // situation. It is still possible to authenticate and
                            // use the connection but using an uncompressed connection
                            connection.streamCompressionNegotiationDone();
                        }
                        else {
                            // SASL authentication has failed. The server may close the connection
                            // depending on the number of retries
                            final SASLFailure failure = PacketParserUtils.parseSASLFailure(parser);
                            connection.processPacket(failure);
                            connection.getSASLAuthentication().authenticationFailed(failure);
                        }
                    }
                    else if (parser.getName().equals("challenge")) {
                        // The server is challenging the SASL authentication made by the client
                        String challengeData = parser.nextText();
                        connection.processPacket(new Challenge(challengeData));
                        connection.getSASLAuthentication().challengeReceived(challengeData);
                    }
                    else if (parser.getName().equals("success")) {
                        connection.processPacket(new Success(parser.nextText()));
                        // We now need to bind a resource for the connection
                        // Open a new stream and wait for the response
                        connection.packetWriter.openStream();
                        // Reset the state of the parser since a new stream element is going
                        // to be sent by the server
                        resetParser();
                        // The SASL authentication with the server was successful. The next step
                        // will be to bind the resource
                        connection.getSASLAuthentication().authenticated();
                    }
                    else if (parser.getName().equals("compressed")) {
                        // Server confirmed that it's possible to use stream compression. Start
                        // stream compression
                        connection.startStreamCompression();
                        // Reset the state of the parser since a new stream element is going
                        // to be sent by the server
                        resetParser();
                    }
                }*/
                else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("stream")) {
                        // Disconnect the connection
                    	Log.e("kk", "connection.disconnect");
                        connection.disconnect();
                    }
                }
                }
                eventType = parser.next();
            } while (!done && eventType != XmlPullParser.END_DOCUMENT && thread == readerThread);
        }
        catch (Exception e) {
        	//LogDetect.send(DataType.specialType, "异常触发重新连接  ", Utils.seller_id+" parsePackets() Exception");
        	e.printStackTrace();
            LogDetect.writeCause(LogDetect.DataType.noType,"TT","00",e);
        	Log.v("PAOPAO",e+"");
            // The exception can be ignored if the the connection is 'done'
            // or if the it was caused because the socket got closed
            if (!(done || connection.isSocketClosed())) {
                synchronized(this) {
                    this.notify();
                }
                // Close the connection and notify connection listeners of the
                // error.
                Log.v("PAOPAO","触发重连");
                LogDetect.send("01107", "********** 触发重连 **********");
                connection.notifyConnectionError(e);
            }
        }
    }
   }

    
    /**
     * Resets the parser using the latest connection's reader. Reseting the parser is necessary
     * when the plain connection has been secured or when a new opening stream element is going
     * to be sent by the server.
     *
     * @throws SmackException if the parser could not be reset.
     */
    

