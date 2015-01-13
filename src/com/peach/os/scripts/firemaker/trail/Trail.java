package com.peach.os.scripts.firemaker.trail;

import java.util.ArrayList;
import java.util.List;

import org.powerbot.script.Tile;

import com.peach.os.scripts.firemaker.trail.TrailNode;

public class Trail implements TrailBase {

	List<TrailNode> trail;
	
	public Trail() {
		this.trail = new ArrayList<TrailNode>(); 
	}
	
	Trail(TrailNode[] t) {
		super();
		for (TrailNode n : t) {
			this.trail.add(n);
		}
	}
	
	@Override
	public TrailNode getStart() {
		return this.trail.get(0);
	}
	
	@Override
	public TrailNode getEnd() {
		return this.trail.get(this.trail.size() - 1);
	}
	
	@Override
	public TrailNode nodeAt(int i) {
		return this.trail.get(i);
	}
	
	@Override
	public TrailNode[] getNodes() {
		return this.trail.toArray(new TrailNode[this.trail.size()]);
	}
	
	@Override
	public int size() {
		return this.trail.size();
	}
	
	@Override
	public boolean addNode(TrailNode n) {
		return this.trail.add(n);
	}
	
	@Override
	public boolean removeNode(TrailNode n) {
		return this.trail.remove(n);
	}
	
	@Override
	public void onFind(TrailNode n) {
		addNode(n);
	}
	
	public int obstructed() {
		int i = 0;
		for (TrailNode n : trail) {
			if (!n.valid())
				i++;
		}
		return i;
	}
	
	public boolean containsTile(Tile t) {
		for (TrailNode n : trail) {
			if (n.tile().equals(t)) 
				return true;
		}
		return false;
	}
	
	public boolean containsNode(TrailNode n) {
		return trail.contains(n);
	}
	
	@Override
	public String toString() {
		return "Trail(" + this.trail.size() + ") " + this.getStart() + ", " + this.getEnd();
	}

}