package com.peach.os.items;

public class Log {
	
	public enum Normal {
		NORMAL(1511, -1, "Normal"),
		OAK(1521, -1, "Oak"),
		WILLOW(1519, -1, "Willow"),
		MAPLE(1517, -1, "Maple"),
		TEAK(6333, -1, "Teak"),
		MAHOGANY(6332, -1, "Mahogany"),
		ACHEY(2862, -1, "Achey"),
		YEW(1515, -1, "Yew"),
		MAGIC(1513, -1, "Magic");
		
		//TODO: add exp for each log
		private final int id, exp;
		private String name;
		
		Normal(int id, int exp, String name) {
			this.id = id;
			this.exp = exp;
			this.name = name;
		}
		
		public int id() {
			return this.id; 
		}
		
		public int exp() {
			return this.exp;
		}
		
		public final String logName() {
			return this.name;
		}
		
		public static int[] logIds() {
			return new int[] {NORMAL.id(), OAK.id(), WILLOW.id(), MAPLE.id(), TEAK.id(), MAHOGANY.id(), ACHEY.id(), YEW.id(), MAGIC.id()};
		}
	}
	
	public enum Pyre {
		OAK(3440, -1, "Pyre - Oak"),
		WILLOW(3442, -1, "Pyre - Willow"),
		MAPLE(3444, -1, "Pyre - Maple"),
		TEAK(6211, -1, "Pyre - Teak"),
		MAHOGANY(6213, -1, "Pyre - Mahogany"),
		YEW(3446, -1, "Pyre - Yew"),
		MAGIC(3448, -1, "Pyre - Magic");
		
		private final int id, exp;
		private String name;
		
		Pyre(int id, int exp, String name) {
			this.id = id;
			this.exp = exp;
			this.name = name;
		}
		
		public int id() {
			return this.id; 
		}
		
		public int exp() {
			return this.exp;
		}
		
		public final String logName() {
			return this.name;
		}
		
		public static int[] logIds() {
			return new int[] {OAK.id(), WILLOW.id(), MAPLE.id(), TEAK.id(), MAHOGANY.id(), YEW.id(), MAGIC.id()};
		}
	}
	
	public enum Special {
		RED(7404, -1, "Special - Red"),
		GREEN(7405, -1, "Special - Green"),
		BLUE(7406, -1, "Special - Blue"),
		WHITE(10328, -1, "Special - White"),
		PURPLE(10329, -1, "Special - Purple");
		
		private final int id, exp;
		private String name;
		
		Special(int id, int exp, String name) {
			this.id = id;
			this.exp = exp;
			this.name = name;
		}
		
		public final int id() {
			return this.id; 
		}
		
		public final int exp() {
			return this.exp;
		}
		
		public final String logName() {
			return this.name;
		}
		
		public static int[] logIds() {
			return new int[] {RED.id(), GREEN.id(), BLUE.id(), WHITE.id(), PURPLE.id()};
		}
	}
	
	public static int getLogIdByName(String name) {
		for (Normal n : Normal.values()) {
			if (n.logName().equals(name)) {
				return n.id();
			}
		}
		for (Pyre p : Pyre.values()) {
			if (p.logName().equals(name)) {
				return p.id();
			}
		}
		for (Special s : Special.values()) {
			if (s.logName().equals(name)) {
				return s.id();
			}
		}
		return -1;
	}

}
