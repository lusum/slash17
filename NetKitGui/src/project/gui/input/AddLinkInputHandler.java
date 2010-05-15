package project.gui.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import project.gui.GCanvas;
import project.gui.GCollisionDomain;
import project.gui.GHost;
import project.gui.GNode;
import project.gui.GuiManager;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PPath;

public class AddLinkInputHandler extends PBasicInputEventHandler {
	
	GHost host;
	GCollisionDomain collisionDomain;
	
	GCanvas canvas;
	
	PPath link;	
	
	public AddLinkInputHandler( GCanvas canvas ) {
		this.canvas = canvas;
	}
	
	@Override
	public void mouseClicked( PInputEvent event ) {
		super.mousePressed(event);
		
		if( event.getButton() == MouseEvent.BUTTON1 ) {
			if( !( event.getPickedNode() instanceof GNode) )
				return;
			
			GNode node = (GNode) event.getPickedNode();
			
			if( node.getType() == GNode.host ) {
				host = (GHost) node;
				createLink();
			} else if( node.getType() == GNode.domain ) {
				collisionDomain = (GCollisionDomain) node;
				createLink();
			}
			
			if( host != null && collisionDomain != null ) {
				canvas.addLink( host, collisionDomain );
				GuiManager.getInstance().update();
				reset();
			} else {
				update(event);
			}
		} else if( event.getButton() == MouseEvent.BUTTON3 ) {
			reset();
		}
	}
	
	@Override
	public void keyPressed(PInputEvent event) {
		System.out.println("key pressed");
		if( event.getKeyCode() == KeyEvent.VK_DELETE ||
			event.getKeyCode() == KeyEvent.VK_CANCEL ) {
			reset();
		}
	}
	
	private void createLink() {
		if( link == null ) {
			link = PPath.createLine(0, 0, 0, 0);
			canvas.addLine(link);
		}
	}

	public void reset() {
		host = null;
		collisionDomain = null;
		link.removeFromParent();
		link = null;
	}
	
	@Override
	public void mouseMoved(PInputEvent event) {
		update( event );
	}
	
	private void update( PInputEvent e ) {
		Point2D start = null;
		Point2D end = e.getPosition();
		
		if( host != null ) {
			start = host.getFullBoundsReference().getCenter2D();
			if( e.getPickedNode() instanceof GNode && ((GNode) e.getPickedNode()).getType() == GNode.domain )
				end = e.getPickedNode().getGlobalBounds().getCenter2D();
			
		} else if( collisionDomain != null ) {
			start = collisionDomain.getFullBoundsReference().getCenter2D();
			if( e.getPickedNode() instanceof GNode && ((GNode) e.getPickedNode()).getType() == GNode.host )
				end = e.getPickedNode().getGlobalBounds().getCenter2D();
		}
		if( start != null ) {
			link.reset();
			link.moveTo((float)start.getX(), (float)start.getY());
			link.lineTo((float)end.getX(), (float)end.getY());
		}
	}
	
	@Override
	public void mouseEntered(PInputEvent event) {
		super.mouseEntered(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setSelected(true);
		}
	}
	
	@Override
	public void mouseExited(PInputEvent event) {
		super.mouseExited(event);
		
		if( event.getPickedNode() instanceof GNode ) {
			GNode node = (GNode) event.getPickedNode();
			node.setSelected(false);
		}
	}
}
