package com.peach.os.items;

public enum Tiara {
	AIR (5527),
	MIND (5529),
	WATER (5531),
	BODY (5533),
	EARTH (5535),
	FIRE (5537),
	COSMIC (5539),
	NATURE (5541),
	CHAOS (5543),
	LAW (5545),
	DEATH (5547),
	BLOOD (5549),
	SOUL (5551),
	ASTRAL (9106);
	
	private int itemId;
	
	Tiara(int itemId) {
		this.itemId = itemId;
	}
	
	public int itemId() {
		return this.itemId;
	}
	
	public int notedItemId() {
		return this.itemId == 9106 ? 9106 : this.itemId + 1;
	}
}
