package com.peach.os.scripts.firemaker.task;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.Bank.Amount;

import com.peach.os.items.Log;
import com.peach.os.scripts.firemaker.util.LogManager;
import com.peach.os.scripts.firemaker.util.Task;

public class Bank extends Task {

	private LogManager lm = null;
	private static GameObject bank;
	
	private static final int TINDER_BOX_ID = 590;
	private static final int[] BANK_IDS = new int[] {11744, 11748}; 	//Edgeville, Varrock
	
	public Bank(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		lm = LogManager.getInstance();
				//Check if there are logs to burn
		return !lm.getLogs().isEmpty() &&
				//Open the bank for the first time
				((lm.getCurrentBankCount() == -1 || lm.getNextBankCount() == -1) ||
				//Check if the inventory has unwanted items
				ctx.inventory.select().id(Log.Normal.logIds(), new int[] {TINDER_BOX_ID}).count() != ctx.inventory.count() ||
				//Check if the inventory has free spaces and we have logs to withdraw
				(ctx.inventory.select().count() < 28 && (lm.getCurrentBankCount() > 0 || lm.getNextBankCount() > 0)));
	}

	@Override
	public void execute() {
		if (!ctx.bank.opened()) {
			bank = ctx.objects.select().id(BANK_IDS).nearest().poll();
			
			if (ctx.players.local().tile().distanceTo(bank) > 10) {
				if (!ctx.movement.step(bank)) {
					Condition.sleep(500);
					return;
				}
			} else if (!bank.inViewport()) {
				ctx.camera.turnTo(bank, 5); //TODO Provide more randomization
			} else {
				bank.interact("Bank");
				if (!Condition.wait(new Callable<Boolean>() {
		
					@Override
					public Boolean call() throws Exception {
						return ctx.bank.opened();
					}
					
				}, 500, 8))
					return;
			}
		} else {
			if (ctx.bank.withdrawModeNoted())
				ctx.bank.withdrawModeNoted(false);
			
		//Unwanted items
			lm.update(ctx); //Update log count
			
			for (Item i : ctx.inventory.select(new Filter<Item>() {

				@Override
				public boolean accept(Item i) {
					return i.id() != TINDER_BOX_ID && !contains(Log.Normal.logIds(), i.id());
				}
				
			})) {
				ctx.bank.deposit(i.id(), Amount.ALL);
			}
			
		//Relative items
			if (ctx.inventory.select().id(TINDER_BOX_ID).isEmpty()) {
				ctx.bank.withdraw(TINDER_BOX_ID, 1);
			}
			
			if (!ctx.bank.withdraw(lm.getCurrentLog().id(), Amount.ALL) || lm.getCurrentBankCount() == 0) {
				lm.logDepleted();
				if (ctx.inventory.select().count() != 28)
					return;
			} else {
				
			}
			
			ctx.bank.close();
		}
	}
	
	private boolean contains(int[] a, int i) {
		for (int j : a) {
			if (j == i)
				return true;
		}
		return false;
	}
}
