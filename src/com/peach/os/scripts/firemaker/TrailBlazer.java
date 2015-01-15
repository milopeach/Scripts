package com.peach.os.scripts.firemaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.powerbot.script.Locatable;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.TileMatrix;

import com.peach.os.items.Log;
import com.peach.os.scripts.firemaker.task.*;
import com.peach.os.scripts.firemaker.trail.Trail;
import com.peach.os.scripts.firemaker.trail.TrailNode;
import com.peach.os.scripts.firemaker.util.LogManager;
import com.peach.os.scripts.firemaker.util.Task;

/*
 * Trail Blazer for Oldschool
 * 
 * @author Reece
 * @version 1.0
 * Date: 26/12/14
 */

@Script.Manifest(name="Trail Blazer", description="Blazin' trails around Runescape, Oldschool style.", properties="client=4")

public class TrailBlazer extends PollingScript<ClientContext> implements PaintListener, MouseListener {

	private LogManager logManager = null;
	private boolean manualTrails = false;
	
	private List<Task> tasks = new ArrayList<Task>();
	private List<Trail> trails = new ArrayList<Trail>();
	
	private Trail currentTrail = null;
	private Trail bestTrail = null;
	
	@Override
	public void start() {
		logManager = LogManager.getInstance();
		new Gui(ctx);
		ctx.controller.suspend();
	}
	
	@Override
	public void resume() {
		if (!manualTrails) {
			this.trails = load(27); 
			this.currentTrail = findClosestTrail(ctx.players.local().tile());
		}
		if (this.tasks.size() == 0)
			this.tasks.addAll(Arrays.asList(new Bank(ctx)));//new Walk(ctx), new Bank(ctx), new Burn(ctx)));
	}
	
	@Override
	public void poll() {
		for (Task t : tasks) {
			if (t.activate()) {
				t.execute();
			}
		}
	}
	
	@Override
	public void repaint(Graphics paint) {
		Graphics2D g = (Graphics2D) paint;
		g.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF));
		
	     g.setColor(new Color(0, 0, 0, 195));
	     g.fillRect(4, 5, 185, 199);
	     //g.fillRect(6, 344, 507, 114);
		
	     g.setStroke(new BasicStroke(1));
	     g.setColor(new Color(255, 255, 255));
	     g.drawRect(4, 5, 185, 199);
	     //g.drawRect(6, 344, 507, 114);

		
		if (logManager != null) {
			g.drawString("Log Count(current/bank): " + logManager.getCurrentBankCount(), 5, 20);
			g.drawString("Log Count(current/inv): " + logManager.getCurrentInvCount(), 5, 30);
			g.drawString("Log Count(next/bank): " + logManager.getNextBankCount(), 5, 40);
			g.drawString("Log Count(next/inv): " + logManager.getNextInvCount(), 5, 50);
		}
		
		if (currentTrail != null) {
			g.setColor(Color.ORANGE);
			for (TrailNode n : currentTrail.getNodes()) {
				TileMatrix m = n.tile().matrix(ctx);
				if (m.inViewport()) {
					g.drawPolygon(m.getBounds());
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (!manualTrails)
			return;
		switch(e.getButton()) {
			case 1: //Left Mouse - Add
				Tile t = getTileAt(e.getPoint());
				if (t.floor() == 0) {}
				break;
			case 2: //Middle Mouse - Set as blocked
				
				break;
			case 3: //Right Mouse - Remove
				
				break;
		}
	}
	
	/** Returns a list of all possible <code>Trail</code>'s in the loaded region\n
	 *  with a specified minimum length.
	 * 
	 * @param minTrailLength the minimum length for a valid trail.
	 * @return a list of <code>Trail</code>'s in the loaded region.
	 */
	private List<Trail> load(int minTrailLength) {
		List<Trail> l = new ArrayList<Trail>();
		for (int y = 0; y < 104; y++) {
			Trail t = new Trail();
			for (int x = 0; x < 104; x++) {
				TrailNode n = new TrailNode(ctx, x, y);
				if (!n.valid()) {
					if (t.size() >= minTrailLength) {
						l.add(t);
					}
					if ((104 - x) < minTrailLength)
						break;
					t = new Trail();
				} else {
					t.onFind(n);
				}
			}
		}
	
		return l;
	}
	
	private Trail findClosestTrail(Tile t) {
		Tile b = ctx.game.mapOffset();
		return findClosestTrail(new TrailNode(ctx, t.x() - b.x(), t.y() - b.y()));
	}
	
	//TODO Add a method that gets the next closest trail that isnt 't'. closestTrail(TrailNode cn, Trail t)
	private Trail findClosestTrail(TrailNode cn) {
		Set<TrailNode> c = new HashSet<TrailNode>();
		Queue<TrailNode> u = new LinkedList<TrailNode>();
		c.add(cn);
		u.add(cn);
		while(!u.isEmpty()) {
			cn = u.poll();
			//If t contains a trail, return the trail.
			for (Trail t1 : this.trails) {
				if (t1.containsNode(cn))
					return t1; //Return closest trail
			}
			//Each node has only 4 possible neighbouring nodes (on the x and y planes).			
			for (TrailNode n : getValidNeighbours(cn, c)) {
				if (!n.blocked() && n.walkable()) {
					c.add(n);
					u.add(n);
				}	
			}
		}
		return null;
	}
	
	private TrailNode[] getValidNeighbours(TrailNode n, Set<TrailNode> c) {
		List<TrailNode> neighbours = new ArrayList<TrailNode>();
		
		TrailNode t = new TrailNode(ctx, n.xOffset(), n.yOffset() + 1);
		if (t != null && !c.contains(t))
			neighbours.add(t);
		
		t = new TrailNode(ctx, n.xOffset() + 1, n.yOffset());
		if (t != null && !c.contains(t))
			neighbours.add(t);
		
		t = new TrailNode(ctx, n.xOffset(), n.yOffset() - 1);
		if (t != null && !c.contains(t))
			neighbours.add(t);
		
		t = new TrailNode(ctx, n.xOffset() - 1, n.yOffset());
		if (t != null && !c.contains(t))
			neighbours.add(t);
		
		return neighbours.toArray(new TrailNode[neighbours.size()]);
	}
	
	public Trail getCurrentTrail() {
		if (this.currentTrail == null)
			this.currentTrail = getNewTrail();
		return this.currentTrail;
	}
	
	public Trail getNewTrail() {
		return this.findClosestTrail(ctx.players.local().tile());
	}
	
	public void setManual(boolean b) {
		this.manualTrails = b;
	}
	
	public void addLogs(Log.Normal... logs) {
		for (Log.Normal l : logs) {
			addLog(l);
		}
	}
	
	public void addLog(Log.Normal l) {
		this.logManager.addLog(l);
	}
	
	public List<Trail> getTrails() {
		return this.trails;
	}
	
	private Tile getTileAt(Point p) {
		Tile b = ctx.players.local().tile();
		for (int x = -52; x < 52; x++) {
			for (int y = -52; y < 52; y++) {
				if (b.derive(x, y, b.floor()).matrix(ctx).contains(p))
					return b.derive(x, y, b.floor());
			}
		}
		return null;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

