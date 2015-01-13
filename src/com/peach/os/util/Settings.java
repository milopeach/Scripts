package com.peach.os.util;

import org.powerbot.script.rt4.ClientContext;

public class Settings {

	public final static int get(ClientContext ctx, int index, int mask) {
		return ctx.varpbits.varpbit(index) & mask;
	}
	
	public final static int get(ClientContext ctx, int index, int shift, boolean rightShift) {
		return rightShift ? (ctx.varpbits.varpbit(index) >> shift) : (ctx.varpbits.varpbit(index) << shift);
	}
	
	public final static int get(ClientContext ctx, int index, int shift, int mask, boolean rightShift) {
		return rightShift ? (ctx.varpbits.varpbit(index) >> shift) & mask : (ctx.varpbits.varpbit(index) >> shift) & mask;
	}
}
