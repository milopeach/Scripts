package com.peach.os.scripts.firemaker.trail;

import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.bot.rt4.client.FloorObject;
import org.powerbot.bot.rt4.client.ItemPile;
import org.powerbot.bot.rt4.client.GameObject;
import org.powerbot.bot.rt4.client.CollisionMap;

import com.peach.os.util.Collision;

public class TrailNode {
	
	private Tile t;
	private ItemPile p;
	private FloorObject fo;
	private GameObject[] o;
	private byte m;
	private CollisionMap c;
	private int f;
	
	private int xo, yo, x, y;
	
	/**
	 * Initializes a <code>TrailNode</code> object from a specific location in the Runescape game.
	 * 
	 * @param x The x offset of the <code>TrailNode</code> script tile/location.
	 * @param y The y offset of the <code>TrailNode</code> script tile/location.
	 */
	
	public TrailNode(ClientContext ctx, int x, int y) {
		Tile b = ctx.game.mapOffset();
		this.t = b.derive(x, y);
		this.xo = x;
		this.yo = y;
		this.x = t.x();
		this.y = t.y();
		
		
		org.powerbot.bot.rt4.client.Tile ct = ctx.client().getLandscape().getTiles()[t.floor()][xo][yo];
		this.p = ct.getItemPile();
		this.fo = ct.getFloorObject();
		this.o = ct.getGameObjects();
		this.m = ctx.client().getLandscapeMeta()[t.floor()][xo][yo];
		this.c = ctx.client().getCollisionMaps()[t.floor()];
		this.f = c.getFlags()[xo][yo];
	}
	
	public int x() {
		return this.x;
	}
	
	public int xOffset() {
		return xo;
	}
	
	public int y() {
		return this.y;
	}
	
	public int yOffset() {
		return yo;
	}
	
	public int flag() {
		return this.f;
	}
	
	public byte meta() {
		return this.m;
	}
	
	public Tile tile() {
		return this.t;
	}
	
	public ItemPile items() throws NullPointerException {
		return this.p;
	}
	
	public FloorObject groundObject() throws NullPointerException {
		return this.fo;
	}
	
	public GameObject[] itemObjects() throws NullPointerException {
		return this.o;
	}
	
	public boolean blocked() {
		return this.meta() != Collision.LANDSCAPE_ALL_BLOCKED && (this.flag() & Collision.ALL_BLOCKED) != 0;
	}
	
	public boolean valid() {
		return this.meta() != Collision.LANDSCAPE_FLOOR && !this.blocked();
	}
	
	public boolean walkable() {
		return (this.meta() == Collision.LANDSCAPE_FLOOR || this.meta() == 0); 
	}

	@Override
	public String toString() {
		return "{Tile:" + this.t + ", xo: " + this.xo + ", yo: " + this.yo + ", flag: " + this.flag() + ", meta: " + this.meta() + "}";
	}
	
	@Override
	public boolean equals(Object o) {
		if (!o.getClass().equals(TrailNode.class))
			return false;
		TrailNode n = (TrailNode) o;
		return this.t.floor() == n.tile().floor() && this.xo == n.xOffset() && this.yo == n.yOffset() && this.x == n.x() && this.y == n.y(); 
	}

}
