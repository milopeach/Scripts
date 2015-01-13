package com.peach.os.scripts.firemaker.trail;

public interface TrailBase {

	public int size();
	public TrailNode nodeAt(int i);
	public TrailNode getEnd();
	public TrailNode getStart();
	public TrailNode[] getNodes();
	public void onFind(TrailNode n);
	public boolean addNode(TrailNode n);
	public boolean removeNode(TrailNode n);
	
}
