package com.peach.os.items;

import java.util.LinkedHashSet;
import java.util.Set;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.ItemQuery;

import com.peach.os.util.Settings;

/*
 * Author: ThePeach
 * Original work: Arcane Sanity
 * Description: Combined my original pouch handler with Arcane Sanity's for RSBot 6048+
 * Version: 1.0
 */

public class PouchHandler {
	public static final int[] DEGRADED_POUCHES =  {5511, 5513, 5515};
	public static final int[] POUCH_IDS = {5509, 5510, 5512, 5514};

	public enum Pouch {
		//Modify pShift and pMask to appropriate values
		SMALL (5509, -1, 1, 3, 0, 0x3, -1, -1),
		MEDIUM (5510, 5511, 25, 6, 3, 0x7, -1, -1),
		LARGE (5512, 5513, 50, 9, 9, 0xD, -1, -1),
		GIANT (5514, 5515, 75, 12, 18, 0xF, -1, -1); //Assume emask is 0xF and eshift is 18.
		
		private final int id, degId, lvl, maxEss, eShift, eMask, pShift, pMask;
		
		Pouch(int id, int degId, int lvl, int maxEss, int eShift, int eMask, int pShift, int pMask) {
			this.id = id;
			this.degId = degId;
			this.lvl  = lvl;
			this.maxEss = maxEss;
			this.eShift = eShift;
			this.eMask = eMask;
			this.pShift = pShift;
			this.pMask = pMask;
		}
		
		public int id() {
			return this.id;
		}
		
		public int degId() {
			return this.degId;
		}
		
		public int lvl() {
			return this.lvl ;
		}
		
		public int maxEss() {
			return this.maxEss;
		}
		
		public Item getPouch(ClientContext ctx) throws NullPointerException {
			return ctx.inventory.select().id(this.id).poll();
		}

		public boolean isFull(ClientContext ctx) {
			return essCount(ctx) == this.maxEss;
		}

		public boolean isEmpty(ClientContext ctx) {
			return Settings.get(ctx, 720, this.pShift, this.pMask, true) == 0;
		}
			
		public int essCount(ClientContext ctx) {
			return Settings.get(ctx, 486, this.eShift, this.eMask, true);
		}
		
		public boolean canUse(int lvl) {
			return lvl >= this.lvl();
		}

	}

	public static boolean fillPouch(ClientContext ctx, final Pouch pouch) {
		final Item item = pouch.getPouch(ctx);
		if (item != null) {
			return item.interact("Fill");
		}
		return false;
	}

	public static boolean emptyPouch(ClientContext ctx, final Pouch pouch) {
		final Item item = pouch.getPouch(ctx);
		if (item != null) {
			return item.interact("Empty");
		}
		return false;
	}

	/**
	 * Returns the pouches currently inside the local players inventory as PouchHandler.Pouch[]
	 * @return the pouches currently inside the local players inventory.
	 */
	public static Pouch[] getPouchesAsPouch(ClientContext ctx) {
		Set<Pouch> pouchSet = new LinkedHashSet<Pouch>();
		for (Pouch p : Pouch.values()) {
			if (!ctx.inventory.select().id(p.id()).isEmpty()) {
				pouchSet.add(p);
			}
		}
		return pouchSet.toArray(new Pouch[pouchSet.size()]);
	}
	
	/**
	 * Returns the pouches currently inside the local players inventory as org.powerbot.script.rt4.Item[]
	 * @return the pouches currently inside the local players inventory.
	 */
	public static Item[] getPouchesAsItem(ClientContext ctx) {
		final Set<Item> pouches = new LinkedHashSet<Item>();
		ctx.inventory.select().id(POUCH_IDS).addTo(pouches);
		return pouches.toArray(new Item[pouches.size()]);
	}
	
	public static ItemQuery<Item> getDegradedPouches(ClientContext ctx) {
		return ctx.inventory.select().id(DEGRADED_POUCHES);
	}

	public static boolean allFull(ClientContext ctx) {
		for (Pouch p : getPouchesAsPouch(ctx)) {
			if (!p.isFull(ctx)) {
				return false;
			}
		}
		return true;
	}

	public static boolean allEmpty(ClientContext ctx) {
		for (Pouch p : getPouchesAsPouch(ctx)) {
			if (!p.isEmpty(ctx)) {
				return false;
			}
		}
		return true;
	}

	public static boolean haveDegraded(ClientContext ctx) {
		return !getDegradedPouches(ctx).isEmpty();
	}
	
}

/*
 * Pouch varpbit data
 * Small pouch:
 * 	Empty to full:	[22:58:42] 486 - 1073741824 (0x40000000) -> 1073741827 (0x40000003)
 * 	Full to empty:	[22:58:45] 486 - 1073741827 (0x40000003) -> 1073741824 (0x40000000) 
	Empty to full:	[22:58:42] 720 - 0 (0x0) -> 2 (0x2)
	Full to empty:	[22:58:45] 720 - 2 (0x2) -> 0 (0x0)
 * 
 *Medium pouch:
 *	Empty to full: [15:55:47] 486 - 1073741824 (0x40000000) -> 1073741872 (0x40000030)
 *	Full to empty: [15:55:50] 486 - 1073741872 (0x40000030) -> 1073741824 (0x40000000)
	Empty to full: [15:55:47] 720 - 0 (0x0) -> 8 (0x8)
	Full to empty: [15:55:50] 720 - 8 (0x8) -> 0 (0x0)
 *
 *Large pouch:
 *
 *	Empty to full: [15:56:36] 486 - 1073741824 (0x40000000) -> 1073746432 (0x40001200)
	Full to empty: [15:56:37] 486 - 1073746432 (0x40001200) -> 1073741824 (0x40000000) 
	Empty to full: [15:56:36] 720 - 0 (0x0) -> 32 (0x20)
	Full to empty: [15:56:37] 720 - 32 (0x20) -> 0 (0x0) 
*/