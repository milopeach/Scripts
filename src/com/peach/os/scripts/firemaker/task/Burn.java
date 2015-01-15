package com.peach.os.scripts.firemaker.task;

import java.util.List;
import java.util.Queue;

import org.powerbot.script.rt4.ClientContext;

import com.peach.os.items.Log;
import com.peach.os.scripts.firemaker.*;
import com.peach.os.scripts.firemaker.trail.Trail;
import com.peach.os.scripts.firemaker.util.Task;

public class Burn extends Task {
	
	private int logId;
	private Queue<Log.Normal> logQueue;
	
	public Burn(ClientContext ctx) {
		super(ctx);
		this.logQueue = ((TrailBlazer) ctx.controller.script()).getLogs();
		this.logId = this.logQueue.peek().id();
	}
	
	@Override
	public boolean activate() {
		return !this.logQueue.isEmpty() && ctx.inventory.select().count() == 28;
	}

	@Override
	public void execute() {
		Trail currentTrail = ((TrailBlazer) ctx.controller.script()).getCurrentTrail();
		int logId = this.logQueue.peek().id();
		
		
	}
	
	

}
