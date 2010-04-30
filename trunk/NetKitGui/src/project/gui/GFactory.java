package project.gui;

import project.common.ItemType;
import project.core.AbstractFactory;
import project.core.AbstractHost;

public class GFactory {

	protected static final String serverImage = "data/images/images/server.png";
	protected static final String nattedServerImage = "data/images/images/nattedserver.png";
	protected static final String pcImage = "data/images/images/pc.png";
	protected static final String routerImage = "data/images/images/router.png";
	protected static final String firewallImage = "data/images/images/firewall.png";
	protected static final String tapImage = "data/images/images/tap.png";
	protected static final String collisionDomainImage = "data/images/images/collisionDomain.png";
	
	protected static int serverCounter = 0;
	protected static int nattedServerCounter = 0;
	protected static int pcCounter = 0;
	protected static int routerCounter = 0;
	protected static int firewallCounter = 0;
	protected static int tapCounter = 0;
	protected static int collisionDomainCounter = 0;
	
	protected AbstractFactory factory;
	
	public GFactory( AbstractFactory factory ) {
		this.factory = factory;
	}
	
	public GHost createGHost( ItemType type, double x, double y ) {
		GHost host = null;
		
		AbstractHost absHost = factory.createHost(type);
		
		switch (type) {
		case SERVER:
			host = new GHost( x, y, serverImage, absHost );
			break;
		case FIREWALL:
			host = new GHost( x, y, firewallImage, absHost );
			break;
		case NATTEDSERVER:
			host = new GHost( x, y, nattedServerImage, absHost );
			break;
		case PC:
			host = new GHost( x, y, pcImage, absHost );
			break;
		case ROUTER:
			host = new GHost( x, y, routerImage, absHost );
			break;
		case TAP:
			host = new GHost( x, y, tapImage, absHost );
			break;
		}
		
		return host;
	}
	
	public GCollisionDomain createCollisionDomain( double x, double y ) {
		return new GCollisionDomain(x, y, factory.createCollisionDomain());
	}
	
	public GLink createLink( GHost host, GCollisionDomain collisionDomain ) {
		return new GLink(host, collisionDomain);
	}
}
