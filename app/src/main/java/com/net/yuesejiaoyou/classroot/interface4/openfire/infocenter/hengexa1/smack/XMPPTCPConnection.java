package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import android.util.Log;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect.DataType;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Packet;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.XMPPConnection;


public class XMPPTCPConnection extends XMPPConnection {


    public XMPPTCPConnection(ConnectionConfiguration configuration) {
        super(configuration);
        // TODO Auto-generated constructor stub
    }

    Socket socket;

    String connectionID = null;
    private String user = null;
    private boolean connected = false;
    // socketClosed is used concurrent
    // by XMPPTCPConnection, PacketReader, PacketWriter
    private volatile boolean socketClosed = false;
    protected Reader reader;
    protected Writer writer;
    protected boolean authenticated;
    private boolean anonymous = false;
    private boolean usingTLS = false;
    private boolean serverAckdCompression = false;
    // private ParsingExceptionCallback parsingExceptionCallback =
    // SmackConfiguration.getDefaultParsingExceptionCallback();

    PacketWriter packetWriter;
    PacketReader packetReader;

    protected XMPPInputOutputStream compressionHandler;

    //ConnectionConfiguration config;
    int i = 0;

    public void connectUsingConfiguration(ConnectionConfiguration config)
            throws IOException, SmackException {
        //this.config = config;
        Exception exception = null;
        ////////////////////------------------>>>>>>>>
        LogDetect.send(DataType.specialType, "xmpp连接内部:", "8");
        ////////////////////------------------>>>>>>>>
        /*
		 * try { maybeResolveDns(); } catch (Exception e) { throw new
		 * SmackException(e); }
		 */
        Iterator<HostAddress> it = config.getHostAddresses().iterator();
        List<HostAddress> failedAddresses = new LinkedList<HostAddress>();

        while (it.hasNext()) {
            exception = null;
            // 获取端口号 IP链接socket
            HostAddress hostAddress = it.next();
            String host = hostAddress.getFQDN();
            int port = hostAddress.getPort();
            Log.v("PAOPAO", "connectUsingConfiguration():" + host + "," + port);
            ////////////////////------------------>>>>>>>>
            LogDetect.send(DataType.specialType, "xmpp连接内部:", "1");
            ////////////////////------------------>>>>>>>>
            try {
                if (config.getSocketFactory() == null) {
                    this.socket = new Socket(host, port);
                    //Log.v("PAOPAO","socket-:"+this.socket+",host:"+host+",port:"+port);
                    ////////////////////------------------>>>>>>>>
                    LogDetect.send(DataType.specialType, "xmpp连接内部:", "2");
                    ////////////////////------------------>>>>>>>>
                } else {
                    this.socket = config.getSocketFactory().createSocket(host,
                            port);
                    //Log.v("PAOPAO","socket+:"+this.socket+",host:"+host+",port:"+port);
                }
            } catch (Exception e) {
                ////////////////////------------------>>>>>>>>
                LogDetect.send(DataType.specialType, "xmpp连接内部:", "3");
                ////////////////////------------------>>>>>>>>
				/*socketClosed=true;
				if(i<5){
					try {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						i++;
						connectUsingConfiguration(config);
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SmackException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    }*/
                exception = e;
                Log.v("PAOPAO", "exception:" + e);
                //LogDetect.send(DataType.basicType,Utils.seller_id+"=phone="+Utils.android,exception.toString());
            }
            if (exception == null) {
                //socketClosed=false;
                // We found a host to connect to, break here
                host = hostAddress.getFQDN();
                port = hostAddress.getPort();
                break;
            }
            hostAddress.setException(exception);
            failedAddresses.add(hostAddress);
            if (!it.hasNext()) {
                // There are no more host addresses to try
                // throw an exception and report all tried
                // HostAddresses in the exception
                // throw new ConnectionException(failedAddresses);
            }
        }
		/*if(!socketClosed){
            i=0;*/
        socketClosed = false;
        initConnection();
        //}


    }

    public boolean isSocketClosed() {
        return socketClosed;
    }

    /**
     * Sends out a notification that there was an error with the connection
     * and closes the connection. Also prints the stack trace of the given exception
     *
     * @param e the exception that causes the connection close event.
     */
    synchronized void notifyConnectionError(Exception e) {
        // Listeners were already notified of the exception, return right here.
        if ((packetReader == null || packetReader.done) &&
                (packetWriter == null || packetWriter.done)) return;

        // Closes the connection temporary. A reconnection is possible
        shutdown();

        // Notify connection listeners of the error.
        //callConnectionClosedOnErrorListener(e);
//        if (e instanceof StreamErrorException) {
//            StreamErrorException xmppEx = (StreamErrorException) e;
//            StreamError error = xmppEx.getStreamError();
//            String reason = error.getCode();
//
//            if ("conflict".equals(reason)) {
//                return;
//            }
//        }
        callConnectionClosedOnErrorListener(e);
        //if (this.isReconnectionAllowed()) {
        /*try {
			connectUsingConfiguration(config);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SmackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        //}
    }


    @Override
    protected void processPacket(Packet packet) {
        super.processPacket(packet);
    }

    protected void sendPacketInternal(Packet packet)
            throws NotConnectedException {

        packetWriter.sendPacket(packet);
    }

    public void sendPacket(Packet packet) throws NotConnectedException /*
																		 * throw
																		 * NotConnectedException
																		 * ()
																		 */ {
		/*
		 * if (!isConnected()) { //throw new NotConnectedException(); } if
		 * (packet == null) { //throw new
		 * NullPointerException("Packet is null."); } switch (fromMode) { case
		 * OMITTED: packet.setFrom(null); break; case USER:
		 * //packet.setFrom(getUser());
		 * 
		 * break; case UNCHANGED: default: break; }
		 */
        //packet.setFrom("123@192.168.0.3");
        // Invoke interceptors for the new packet that is about to be sent.
        // Interceptors may modify
        // the content of the packet.
        // firePacketInterceptors(packet);
        // 发送消息
        sendPacketInternal(packet);
        // Process packet writer listeners. Note that we're using the sending
        // thread so it's
        // expected that listeners are fast.
        // firePacketSendingListeners(packet);
    }

    // 初始化链接
    private void initConnection() throws IOException, SmackException {
        boolean isFirstInitialization = packetReader == null
                || packetWriter == null;
        compressionHandler = null;
        serverAckdCompression = false;

        // Set the reader and writer instance variables
        initReaderAndWriter();
        // writer.write("你好");
        try {
            if (isFirstInitialization) {
                packetWriter = new PacketWriter(this);
                packetReader = new PacketReader(this);

                // If debugging is enabled, we should start the thread that will
                // listen for
                // all packets and then log them.
			/*
			 * if (config.isDebuggerEnabled()) {
			 * addPacketListener(debugger.getReaderListener(), null); if
			 * (debugger.getWriterListener() != null) {
			 * addPacketSendingListener(debugger.getWriterListener(), null); } }
			 */
            } else {
                //packetWriter.init();
                //packetReader.init();
                packetWriter = new PacketWriter(this);
                packetReader = new PacketReader(this);
            }

            // Start the packet writer. This will open a XMPP stream to the server
            packetWriter.startup();
            // Start the packet reader. The startup() method will block until we
            // get an opening stream packet back from server.
            packetReader.startup();

            // Make note of the fact that we're now connected.
            connected = true;

		/*
		 * if (isFirstInitialization) { // Notify listeners that a new
		 * connection has been established for (ConnectionCreationListener
		 * listener : getConnectionCreationListeners()) {
		 * listener.connectionCreated(this); } }
		 */
        } catch (SmackException ex) {
            // An exception occurred in setting up the connection.
            shutdown();
            // Everything stoppped. Now throw the exception.
            throw ex;
        }

    }

    public synchronized void login(String username, String password,
                                   String resource) throws SmackException, IOException {
		/*if (!isConnected()) {
            throw new NotConnectedException();
        }
        if (authenticated) {
            throw new AlreadyLoggedInException();
        }*/
        // Do partial version of nameprep on the username.
        /*username = username.toLowerCase(Locale.US).trim();

        if (saslAuthentication.hasNonAnonymousAuthentication()) {
            // Authenticate using SASL
            if (password != null) {
                saslAuthentication.authenticate(username, password, resource);
            }
            else {
                saslAuthentication.authenticate(resource, config.getCallbackHandler());
            }
        } else {
            //throw new SaslException("No non-anonymous SASL authentication mechanism available");
        }

        // If compression is enabled then request the server to use stream compression. XEP-170
        // recommends to perform stream compression before resource binding.
        //if (config.isCompressionEnabled()) {
        //    useCompression();
        //}

        // Set the user.
        String response = bindResourceAndEstablishSession(resource);
        if (response != null) {
            this.user = response;
            // Update the serviceName with the one returned by the server
            //setServiceName(StringUtils.parseServer(response));
        }
        else {
            this.user = username + "@" + getServiceName();
            if (resource != null) {
                this.user += "/" + resource;
            }
        }

        // Indicate that we're now authenticated.
        authenticated = true;
        anonymous = false;

        // Stores the authentication for future reconnection
        //setLoginInfo(username, password, resource);

        // If debugging is enabled, change the the debug window title to include the
        // name we are now logged-in as.
        // If DEBUG_ENABLED was set to true AFTER the connection was created the debugger
        // will be null
        if (config.isDebuggerEnabled() && debugger != null) {
            debugger.userHasLogged(user);
        }
        callConnectionAuthenticatedListener();

        // Set presence to online. It is important that this is done after
        // callConnectionAuthenticatedListener(), as this call will also
        // eventually load the roster. And we should load the roster before we
        // send the initial presence.
        if (config.isSendPresence()) {
            //sendPacket(new Presence(Presence.Type.available));
        }*/
    }

    public String getServiceName() {
        return config.getServiceName();
    }

    // 初始化BufferedWriter 写 和 BufferedReader 读 writer 用于发送信息
    private void initReaderAndWriter() throws IOException {

        if (socket == null) {
            return;
        }
        try {
            if (compressionHandler == null) {
                reader = new BufferedReader(new InputStreamReader(
                        socket.getInputStream(), "UTF-8"));
                writer = new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "UTF-8"));
            } else {
                try {
                    OutputStream os = compressionHandler.getOutputStream(socket
                            .getOutputStream());
                    writer = new BufferedWriter(new OutputStreamWriter(os,
                            "UTF-8"));

                    InputStream is = compressionHandler.getInputStream(socket
                            .getInputStream());
                    reader = new BufferedReader(new InputStreamReader(is,
                            "UTF-8"));
                } catch (Exception e) {
                    // LOGGER.log(Level.WARNING, "initReaderAndWriter()", e);
                    compressionHandler = null;
                    reader = new BufferedReader(new InputStreamReader(
                            socket.getInputStream(), "UTF-8"));
                    writer = new BufferedWriter(new OutputStreamWriter(
                            socket.getOutputStream(), "UTF-8"));
                }
            }
        } catch (UnsupportedEncodingException ioe) {
            throw new IllegalStateException(ioe);
        }

        // If debugging is enabled, we open a window and write out all network
        // traffic.
        // initDebugger();
    }

    public Writer getWriter() {
        // TODO Auto-generated method stub
        return this.writer;
    }

    public Reader getReader() {
        // TODO Auto-generated method stub
        return this.reader;
    }

    @Override
    public String getUser() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getConnectionID() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return connected;
    }

    @Override
    public boolean isAuthenticated() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAnonymous() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSecureConnection() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isUsingCompression() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void connectInternal() throws SmackException, IOException {
        // TODO Auto-generated method stub
        connectUsingConfiguration(config);

        // TODO is there a case where connectUsing.. does not throw an exception but connected is
        // still false?
        if (connected) {
            callConnectionConnectedListener();
        }
    }

    /**
     * Shuts the current connection down. After this method returns, the connection must be ready
     * for re-use by connect.
     */
    @Override
    protected void shutdown() {
        if (packetReader != null) {
            packetReader.shutdown();
        }
        if (packetWriter != null) {
            packetWriter.shutdown();
        }

        // Set socketClosed to true. This will cause the PacketReader
        // and PacketWriter to ignore any Exceptions that are thrown
        // because of a read/write from/to a closed stream.
        // It is *important* that this is done before socket.close()!
        socketClosed = true;
        try {
            socket.close();
        } catch (Exception e) {
            //LOGGER.log(Level.WARNING, "shutdown", e);
        }
        authenticated = false;
        connected = false;
        usingTLS = false;
        reader = null;
        writer = null;
        //setWasAuthenticated(authenticated);
    }


    public void checkConnection() {
        if (socket != null) {
            try {
                socket.sendUrgentData(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}