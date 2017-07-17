package com.huatai.common;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Clock {
	private static final Clock instance = new Clock();
	private final Set<IClockListener> listeners = new HashSet<IClockListener>();
	private Mode mode = Mode.AUTO;
	private Date manualClock = new Date(0);
	
	public enum Mode {
		AUTO, MANUAL
	}
	
	private Clock() {}
	
	public static Clock getInstance() {
		return instance;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public void setManualClock(Date manualClock) {
		this.manualClock = new Date(manualClock.getTime());
		for (IClockListener listener : listeners) {
			listener.onTime(new Date(manualClock.getTime()));
		}
	}
	
	public Date now() {
		if (mode == Mode.MANUAL) return new Date(manualClock.getTime());
		
		return new Date();
	}
	
	public boolean addClockListener(IClockListener listener) {
		return listeners.add(listener);
	}
	
	public boolean removeClockListener(IClockListener listener) {
		return listeners.remove(listener);
	}
	
	public boolean isManual() {
		return mode.equals(Mode.MANUAL);
	}
}
