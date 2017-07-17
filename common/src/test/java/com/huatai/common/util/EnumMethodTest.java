package com.huatai.common.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.huatai.common.type.StrategyState;

public class EnumMethodTest {
	@Test
	public void test() {
		Object state = StrategyState.Paused;
		assertTrue(ReflectionUtil.isEnum(state));
		String[] fields = ReflectionUtil.getEnumStringValues(state);
		StrategyState[] states = StrategyState.values();
		assertTrue(states.length == fields.length);
		for (int i = 0; i < states.length; i++) {
			assertTrue(states[i].toString().equals(fields[i]));
		}
		Object obj = ReflectionUtil.callStaticMethod(state.getClass(), "valueOf", new String[] { "Paused" });
		assertTrue(obj.equals(StrategyState.Paused));
	}
}
