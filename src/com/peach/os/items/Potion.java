package com.peach.os.items;

public enum Potion {
	
	STAMINA_ONE(12631, 1),
	STAMINA_TWO(12629, 2),
	STAMINA_THREE(12627, 3),
	STAMINA_FOUR(12625, 4);
	
	private int id, doses;
	
	Potion(int id, int doses) {
		this.id = id;
		this.doses = doses;
	}

	
	public int id() {
		return this.id;
	}
	
	public int doses() {
		return this.doses;
	}
	
	public static int[] ids() {
		int[] ids = new int[Potion.values().length];
		for (Potion p : Potion.values()) {
			ids[p.ordinal()] = p.id();
		}
		return ids;
	}
	
	public static int stamVarpIndex = 638;
}