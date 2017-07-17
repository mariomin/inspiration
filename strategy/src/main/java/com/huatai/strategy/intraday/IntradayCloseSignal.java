package com.huatai.strategy.intraday;

import com.huatai.strategy.intraday.breakout.BreakoutCloseSignal;
import com.huatai.strategy.intraday.reverse.ReverseCloseSignal;

public class IntradayCloseSignal {
	public final BreakoutCloseSignal breakoutSignal = new BreakoutCloseSignal();
	public final ReverseCloseSignal reverseSignal = new ReverseCloseSignal();
}
