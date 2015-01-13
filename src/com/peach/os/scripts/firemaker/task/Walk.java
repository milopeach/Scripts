package com.peach.os.scripts.firemaker.task;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.TilePath;

import com.peach.os.scripts.firemaker.Task;
import com.peach.os.scripts.firemaker.TrailBlazer;
import com.peach.os.scripts.firemaker.trail.Trail;

public class Walk extends Task {

	public Walk(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		int logId = ((TrailBlazer) ctx.controller.script()).getLogs().peek().id();
		return ((TrailBlazer) ctx.controller.script()).getCurrentTrail() != null &&
				ctx.inventory.select().id(arg0)
				//(ctx.inventory.select().count() <= 1 || ctx.inventory.select().count() == 28);
	}
	
	@Override
	public void execute() {
		
	}

}


/*
 * 		//TODO: Modify the walking method to the current trail.
		//Possibly implement a star or dijkstra's algorithm to find closest trail node and expand to determine the most efficient start/end point. 
		if (ctx.inventory.select().count() == 28) { //Walk to trail
			Trail t = ((TrailBlazer) ctx.controller.script()).getCurrentTrail();
			final TilePath p = ctx.movement.newTilePath(t.getStart().tile());
			//if we aren't at the start of the trail and the next step isnt 
			//moving us to it, continue to the start of the trail
			if (!ctx.players.local().tile().equals(t.getStart().tile())) {// && !p.next().equals(t.getStart().tile())) {
				p.traverse();
				//Condition.sleep(Random.nextInt(150, 250) + 500);
				Condition.wait(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return p.next().distanceTo(ctx.players.local().tile()) <= 6;
					}
					
				});
			}
		} else {
			GameObject bank = ctx.objects.select().id(11744).nearest().poll();
			ctx.movement.step(bank);
			Condition.sleep(Random.nextInt(150, 250) + 500);
		}
 */
