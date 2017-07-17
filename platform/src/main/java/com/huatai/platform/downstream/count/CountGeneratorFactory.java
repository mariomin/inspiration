package com.huatai.platform.downstream.count;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CountGeneratorFactory {
	private static final CountGeneratorFactory instance = new CountGeneratorFactory();
	private static final Map<CountReferenceId, ICountGenerator> generatorMap = new HashMap<CountReferenceId, ICountGenerator>();
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private CountGeneratorFactory() {}
	
	public static CountGeneratorFactory getInstance() {
		return instance;
	}
	
	public ICountGenerator getGenerator(CountReferenceId id) {
		if (generatorMap.containsKey(id))
			return generatorMap.get(id);
		else {
			synchronized (generatorMap) {
				if (generatorMap.containsKey(id))
					return generatorMap.get(id);
				else {
					ICountGenerator generator = new CountGenerator(id, sessionFactory);
					generatorMap.put(id, generator);
					return generator;
				}
			}
		}
	}
}
