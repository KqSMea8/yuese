/**
 *
 * Copyright (c) 2009 Jonas Adahl, 2011-2014 Florian Schmaus
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
package com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smackx.caps;



import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.ConnectionCreationListener;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.IQ;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Manager;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.Presence;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.SmackException.NotConnectedException;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smack.XMPPConnection;
import com.net.yuesejiaoyou.classroot.interface4.openfire.infocenter.hengexa2.smackx.disco.packet.DiscoverInfo;


/**
 * Keeps track of entity capabilities.
 * 
 * @author Florian Schmaus
 * @see <a href="http://www.xmpp.org/extensions/xep-0115.html">XEP-0115: Entity Capabilities</a>
 */
public class EntityCapsManager extends Manager {
    public EntityCapsManager(XMPPConnection connection) {
		super(connection);
		// TODO Auto-generated constructor stub
	}


	private static final Logger LOGGER = Logger.getLogger(EntityCapsManager.class.getName());
	  private boolean presenceSend = false;
    public static final String NAMESPACE = "http://jabber.org/protocol/caps";
    public static final String ELEMENT = "c";
    private boolean entityCapsEnabled;
    private static final Map<String, MessageDigest> SUPPORTED_HASHES = new HashMap<String, MessageDigest>();
    private static String DEFAULT_ENTITY_NODE = "http://www.igniterealtime.org/projects/smack";

    //protected static EntityCapsPersistentCache persistentCache;

    private static boolean autoEnableEntityCaps = true;

    private static Map<XMPPConnection, EntityCapsManager> instances = Collections
            .synchronizedMap(new WeakHashMap<XMPPConnection, EntityCapsManager>());

    /*private static final PacketFilter PRESENCES_WITH_CAPS = new AndFilter(new PacketTypeFilter(Presence.class), new PacketExtensionFilter(
                    ELEMENT, NAMESPACE));
    private static final PacketFilter PRESENCES_WITHOUT_CAPS = new AndFilter(new PacketTypeFilter(Presence.class), new NotFilter(new PacketExtensionFilter(
                    ELEMENT, NAMESPACE)));
    private static final PacketFilter PRESENCES = new PacketTypeFilter(Presence.class);
*/
    /**
     * Map of (node + '#" + hash algorithm) to DiscoverInfo data
     */
    //protected static Map<String, DiscoverInfo> caps = new Cache<String, DiscoverInfo>(1000, -1);

    /**
     * Map of Full JID -&gt; DiscoverInfo/null. In case of c2s connection the
     * key is formed as user@server/resource (resource is required) In case of
     * link-local connection the key is formed as user@host (no resource) In
     * case of a server or component the key is formed as domain
     */
    //protected static Map<String, NodeVerHash> jidCaps = new Cache<String, NodeVerHash>(10000, -1);

    static {
        XMPPConnection.addConnectionCreationListener(new ConnectionCreationListener() {
            public void connectionCreated(XMPPConnection connection) {
                //getInstanceFor(connection);
            }
        });

        try {
            MessageDigest sha1MessageDigest = MessageDigest.getInstance("SHA-1");
            SUPPORTED_HASHES.put("sha-1", sha1MessageDigest);
        } catch (NoSuchAlgorithmException e) {
            // Ignore
        }
    }

    /**
     * Set the default entity node that will be used for new EntityCapsManagers
     *
     * @param entityNode
     */
    public static void setDefaultEntityNode(String entityNode) {
        DEFAULT_ENTITY_NODE = entityNode;
    }

    public boolean entityCapsEnabled() {
        return entityCapsEnabled;
    }
    private String entityNode = DEFAULT_ENTITY_NODE;

   /* private EntityCapsManager(XMPPConnection connection) {
        super(connection);
        this.sdm = ServiceDiscoveryManager.getInstanceFor(connection);
        instances.put(connection, this);

        connection.addConnectionListener(new AbstractConnectionListener() {
            @Override
            public void connectionClosed() {
                presenceSend = false;
            }
            @Override
            public void connectionClosedOnError(Exception e) {
                presenceSend = false;
            }
        });

        // This calculates the local entity caps version
        updateLocalEntityCaps();

        if (autoEnableEntityCaps)
            enableEntityCaps();

        connection.addPacketListener(new PacketListener() {
            // Listen for remote presence stanzas with the caps extension
            // If we receive such a stanza, record the JID and nodeVer
            @Override
            public void processPacket(Packet packet) {
                if (!entityCapsEnabled())
                    return;

                CapsExtension ext = (CapsExtension) packet.getExtension(EntityCapsManager.ELEMENT,
                        EntityCapsManager.NAMESPACE);

                String hash = ext.getHash().toLowerCase(Locale.US);
                if (!SUPPORTED_HASHES.containsKey(hash))
                    return;

                String from = packet.getFrom();
                String node = ext.getNode();
                String ver = ext.getVer();

                jidCaps.put(from, new NodeVerHash(node, ver, hash));
            }

        }, PRESENCES_WITH_CAPS);

        connection.addPacketListener(new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
                // always remove the JID from the map, even if entityCaps are
                // disabled
                String from = packet.getFrom();
                jidCaps.remove(from);
            }
        }, PRESENCES_WITHOUT_CAPS);

        connection.addPacketSendingListener(new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
                presenceSend = true;
            }
        }, PRESENCES);

        // Intercept presence packages and add caps data when intended.
        // XEP-0115 specifies that a client SHOULD include entity capabilities
        // with every presence notification it sends.
        PacketInterceptor packetInterceptor = new PacketInterceptor() {
            public void interceptPacket(Packet packet) {
                if (!entityCapsEnabled)
                    return;

                CapsExtension caps = new CapsExtension(entityNode, getCapsVersion(), "sha-1");
                packet.addExtension(caps);
            }
        };
        connection.addPacketInterceptor(packetInterceptor, PRESENCES);
        // It's important to do this as last action. Since it changes the
        // behavior of the SDM in some ways
        sdm.setEntityCapsManager(this);
    }
*/
    public void updateLocalEntityCaps() {
        XMPPConnection connection = connection();

        DiscoverInfo discoverInfo = new DiscoverInfo();
        discoverInfo.setType(IQ.Type.RESULT);
//        discoverInfo.setNode(getLocalNodeVer());
//        if (connection != null)
//            discoverInfo.setFrom(connection.getUser());
//        sdm.addDiscoverInfoTo(discoverInfo);
//
//        currentCapsVersion = generateVerificationString(discoverInfo, "sha-1");
//        addDiscoverInfoByNode(entityNode + '#' + currentCapsVersion, discoverInfo);
//        if (lastLocalCapsVersions.size() > 10) {
//            String oldCapsVersion = lastLocalCapsVersions.poll();
//            sdm.removeNodeInformationProvider(entityNode + '#' + oldCapsVersion);
//        }
//        lastLocalCapsVersions.add(currentCapsVersion);
//
//        caps.put(currentCapsVersion, discoverInfo);
//        if (connection != null)
//            jidCaps.put(connection.getUser(), new NodeVerHash(entityNode, currentCapsVersion, "sha-1"));
//
//        final List<Identity> identities = new LinkedList<Identity>(ServiceDiscoveryManager.getInstanceFor(connection).getIdentities());
//        sdm.setNodeInformationProvider(entityNode + '#' + currentCapsVersion, new NodeInformationProvider() {
//            List<String> features = sdm.getFeaturesList();
//            List<PacketExtension> packetExtensions = sdm.getExtendedInfoAsList();
//
//            @Override
//            public List<Item> getNodeItems() {
//                return null;
//            }
//
//            @Override
//            public List<String> getNodeFeatures() {
//                return features;
//            }
//
//            @Override
//            public List<Identity> getNodeIdentities() {
//                return identities;
//            }
//
//            @Override
//            public List<PacketExtension> getNodePacketExtensions() {
//                return packetExtensions;
//            }
//        });

        // Send an empty presence, and let the packet intercepter
        // add a <c/> node to it.
        // See http://xmpp.org/extensions/xep-0115.html#advertise
        // We only send a presence packet if there was already one send
        // to respect ConnectionConfiguration.isSendPresence()
        if (connection != null && connection.isAuthenticated() && presenceSend) {
            Presence presence = new Presence(Presence.Type.available);
            try {
                connection.sendPacket(presence);
            }
            catch (NotConnectedException e) {
                LOGGER.log(Level.WARNING, "Could could not update presence with caps info", e);
            }
        }
    }

}
