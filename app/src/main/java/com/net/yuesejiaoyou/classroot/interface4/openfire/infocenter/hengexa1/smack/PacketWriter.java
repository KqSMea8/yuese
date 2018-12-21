package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa1.smack;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Packet;

import android.util.Log;


public class PacketWriter {
	private final XMPPTCPConnection connection;
	public static final int QUEUE_SIZE = 500;
	private Thread writerThread;
	private Writer writer;
	volatile boolean done;
	AtomicBoolean shutdownDone = new AtomicBoolean(false);
	int connectionCounterValue;

	private final ArrayBlockingQueueWithShutdown<Packet> queue = new ArrayBlockingQueueWithShutdown<Packet>(
			QUEUE_SIZE, true);

	public PacketWriter(XMPPTCPConnection connection) {
		AtomicInteger connectionCounter = new AtomicInteger(0);
		this.connectionCounterValue = connectionCounter.getAndIncrement();
		this.connection = connection;
		init();
	}

	public void sendPacket(Packet packet) /* throws NotConnectedException */{
		/*
		 * if (done) { throw new NotConnectedException(); }
		 */

		try {

			// 将聊天信息放到queue中，以便线程从中获取内容发送
			queue.put(packet);
		} catch (InterruptedException ie) {
			// throw new NotConnectedException();
		}
	}

	protected void init() {
		// socket链接后，形成的writer
		writer = connection.getWriter();
		done = false;
		shutdownDone.set(false);

		queue.start();
		writerThread = new Thread() {
			public void run() {
				// 发送信息的线程
				writePackets(this);
				////System.out.println("22222");
			}
		};
		writerThread.setName("Smack Packet Writer (" + connectionCounterValue
				+ ")");
		writerThread.setDaemon(true);
	}

	public void startup() {
		// 开启发送信息的线程
		writerThread.start();
		////System.out.println("111");
	}

	private Packet nextPacket() {
		if (done) {
			return null;
		}

		Packet packet = null;
		try {
			// 从queue中取出一个packet信息，以便发送
			packet = queue.take();
			////System.out.println(packet);
		} catch (InterruptedException e) {
			// Do nothing
		}
		return packet;
	}

	private void writePackets(Thread thisThread) {
		try {
			////System.out.println("5555");
			// Open the stream.
			openStream();
			////System.out.println("6666");
			// Write out packets from the queue.
			while (!done && (writerThread == thisThread)) {
				Log.e("jj", "发送"+done);
				Packet packet = nextPacket();
				if (packet != null) {
					
					// 发送信息
					writer.write(packet.toXML().toString());
					////System.out.println(packet.toXML().toString());
					if (queue.isEmpty()) {
						writer.flush();
					}
				}
			}
			// Flush out the rest of the queue. If the queue is extremely large,
			// it's possible
			// we won't have time to entirely flush it before the socket is
			// forced closed
			// by the shutdown process.
			try {
				while (!queue.isEmpty()) {
					Packet packet = queue.remove();
					writer.write(packet.toXML().toString());
				}
				writer.flush();
			} catch (Exception e) {
				// LOGGER.log(Level.WARNING,
				// "Exception flushing queue during shutdown, ignore and continue",
				// e);
			}

			// Delete the queue contents (hopefully nothing is left).
			queue.clear();

			// Close the stream.
			try {
				Log.e("jj", "发送空消息"+"666666666666666");
				writer.write("</stream:stream>");
				writer.flush();
			} catch (Exception e) {
				// LOGGER.log(Level.WARNING,
				// "Exception writing closing stream element", e);

			} finally {
				try {
					writer.close();
				} catch (Exception e) {
					// Do nothing
				}
			}

		    shutdownDone.set(true);
		    synchronized(shutdownDone) {
		       shutdownDone.notify();
		    }
		} catch (IOException ioe) {
			// The exception can be ignored if the the connection is 'done'
			// or if the it was caused because the socket got closed
			 if (!(done || connection.isSocketClosed())) {
			 shutdown();
			 connection.notifyConnectionError(ioe);
			 }
		}
	}

	 /**
     * Shuts down the packet writer. Once this method has been called, no further
     * packets will be written to the server.
     */
    public void shutdown() {
        done = true;
        queue.shutdown();
        synchronized(shutdownDone) {
            if (!shutdownDone.get()) {
                try {
                    shutdownDone.wait(connection.getPacketReplyTimeout());
                }
                catch (InterruptedException e) {
                    //LOGGER.log(Level.WARNING, "shutdown", e);
                }
            }
        }
    }

	
	
	
	
	void openStream() throws IOException {
		////System.out.println("777");
		StringBuilder stream = new StringBuilder();
		stream.append("<stream:stream");
		stream.append(" to=\"").append("139").append("\"");
		stream.append(" xmlns=\"jabber:client\"");
		stream.append(" xmlns:stream=\"http://etherx.jabber.org/streams\"");
		stream.append(" version=\"1.0\">");
		writer.write(stream.toString());
		writer.flush();
	}

}
