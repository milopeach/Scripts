package com.peach.os.scripts.firemaker.task;

import java.util.Queue;
import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Bank.Amount;

import com.peach.os.items.Log;
import com.peach.os.scripts.firemaker.*;

public class Bank extends Task {

	private static final int TINDER_BOX_ID = 590;
	//Edgevill, Varrock
	private static final int[] BANK_IDS = new int[] {11744, 11748}; 
	
	private static GameObject bank;
	
	public Bank(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return !this.logQueue.isEmpty() &&
				LogManager.getInstance().getCurrentBankCount() == -1 ||
				(ctx.inventory.select().id(Log.Normal.logIds(), new int[] {TINDER_BOX_ID}).count() != ctx.inventory.count() ||
				ctx.inventory.count() < 28);
		//if (ctx.players.local().tile().distanceTo(bank.tile()) < 8 && bank.inViewport()) {
		//return !this.logQueue.isEmpty() && ctx.inventory.select().id(TINDER_BOX_ID, logId).count() <= 1; 
	}

	@Override
	public void execute() {
		if (!ctx.bank.opened()) {
		logId = logQueue.peek().id();
		bank = ctx.objects.select().id(BANK_IDS).nearest().poll();
		
			bank.interact("Bank");
			if (!Condition.wait(new Callable<Boolean>() {
	
				@Override
				public Boolean call() throws Exception {
					return ctx.bank.opened();
				}
				
			}, 500, 8))
				return;
		}
	

		if (ctx.inventory.select().id(logId, TINDER_BOX_ID).count() != 28) {
			for (Item i : ctx.inventory.items()) {
				if (i.id() != logId && i.id() != TINDER_BOX_ID) {
					ctx.bank.deposit(i.id(), Amount.ALL);
				}
			}
		}
		
		if (ctx.inventory.select().id(TINDER_BOX_ID).isEmpty()) {
			ctx.bank.withdraw(TINDER_BOX_ID, 1);
		}
		
		//Determine if there are no more logs of the current id/type.
		if (!ctx.bank.withdraw(logId, Amount.ALL) || ctx.bank.select().id(logId).count() == 0) {
			((TrailBlazer) ctx.controller.script()).getLogs().remove(); 
			this.logQueue = ((TrailBlazer) ctx.controller.script()).getLogs(); //update the log queue
			if (ctx.inventory.select().count() != 28) {
				execute(); //withdraw the next set of logs
			}
		}
		
		ctx.bank.close();
	}
}
