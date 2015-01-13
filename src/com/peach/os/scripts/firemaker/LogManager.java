package com.peach.os.scripts.firemaker;

import java.util.LinkedList;
import java.util.Queue;

import com.peach.os.items.Log;

public class LogManager {
	//TEMP TODO Method chaining needs to be fixed with all current and next log related methods.
	
	private int cLogBank = -1, cLogInv = -1;
	private int nLogBank = -1, nLogInv = -1;
	private Log.Normal cLog = null, nLog = null;
	
	private Queue<Log.Normal> logs = new LinkedList<Log.Normal>();
	
	private static LogManager me = null;
	
	public static LogManager getInstance() {
		if (me == null)
			me = new LogManager();
		return me;
	}
	
	public boolean addLog(Log.Normal l) {
		return this.logs.add(l);
	}
	
	public Queue<Log.Normal> getLogs() {
		return this.logs;
	}
	
	public int getCurrentBankCount() {
		return this.cLogBank;
	}
	
	public boolean setCurrentBankCount(int i) {
		if (this.cLog == null || i < 0) {
			logDepleted();
			return false;
		}
		this.cLogBank = i;
		return true;
	}
	
	public int getCurrentInvCount() {
		return this.cLogInv;
	}
	
	public void setCurrentInvCount(int i) {
		this.cLogInv = i;
	}
	
	public int getNextBankCount() {
		return this.nLogBank;
	}
	
	public boolean setNextBankCount(int i) {
		if (this.nLog == null || i < 0) {
			logDepleted();
			return false;
		}
		this.nLogBank = i;
		return true;
	}
	
	public int getNextInvCount() {
		return this.nLogInv;
	}
	
	public void setNextInvCount(int i) {
		this.nLogInv = i;
	}
	
	public Log.Normal getCurrentLog() {
		if (!logs.isEmpty() && this.cLog == null)
			this.cLog = logs.poll();
		return this.cLog;
	}
	
	public Log.Normal getNextLog() {
		if (!logs.isEmpty() && this.nLog == null)
			this.nLog = logs.peek();
		return this.nLog;
	}
	
	//TEMP: Call this when log counts get modified to, or below, 0.
	public void logDepleted() {
		this.cLog = logs.poll();
		this.nLog = logs.peek(); //Could be null.
	}
	
}
