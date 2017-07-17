package com.huatai.platform.algo;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.huatai.common.algo.AtsStrategy;
import com.huatai.common.strategy.IStrategyFactory;
import com.huatai.common.strategy.StrategyException;

public class StrategyFactory implements IStrategyFactory {
	private static final Logger log = LoggerFactory.getLogger(StrategyFactory.class);
	
	// key=: class id, value=: class
	private final Map<String, Class<?>> clazzs = new HashMap<String, Class<?>>();
	
	private final Set<String> strategyNames = new HashSet<String>();
	
	// key=: class id, value=: class name;
	private final Map<String, String> clazzMaps;
	
	private final Map<String, String> cnNameMap = new HashMap<>();
	
	private final Map<String, Class<?>> params = new HashMap<>();
	
	public StrategyFactory() {
		clazzMaps = new HashMap<>();
		
		//Find all strategies and algorithms with respective annotations
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(AtsStrategy.class));
		
		for (BeanDefinition bd : scanner.findCandidateComponents("com.huatai")) {
			clazzMaps.put(bd.getBeanClassName(), bd.getBeanClassName());
			try {
				Class<?> clazz = Class.forName(clazzMaps.get(bd.getBeanClassName()));
				
				AtsStrategy strategy = clazz.getAnnotation(AtsStrategy.class);
				if (strategy != null) {
					if ((boolean) AnnotationUtils.getValue(strategy, "available")) {
						loadAnnotation(strategy, clazz, strategyNames);
					}
				}
			} catch (ClassNotFoundException e) {
				log.error("Cannot find class : {}", clazzMaps.get(bd.getBeanClassName()));
			} catch (Exception e) {
				log.error("Cannot create class: {}", e.getMessage());
			}
		}
	}
	
	@Override
	public void init() throws StrategyException {}
	
	@Override
	public void uninit() {}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Strategy<?, ?> createStrategy(String name, Object strategyParams) throws StrategyException {
		Class<?> clazz = clazzs.get(name);
		if (clazz == null) throw new StrategyException("no corresponding strategy class found " + name);
		
		try {
			Strategy strategy = (Strategy) clazz.newInstance();
			strategy.setParams(strategyParams);
			strategy.setName(name);
			return strategy;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new StrategyException(e.getMessage());
		}
	}
	
	private void loadAnnotation(Annotation annotation, Class<?> clazz, Set<String> nameSet) throws Exception {
		String name = (String) AnnotationUtils.getValue(annotation, "name");
		
		if (clazzs.containsKey(name)) throw new Exception("Duplicate trade model name : " + name);
		
		String chineseName = (String) AnnotationUtils.getValue(annotation, "chineseName");
		
		name = StringUtils.isBlank(name) ? clazz.getSimpleName() : name;
		
		clazzMaps.put(name, clazz.getName());
		clazzs.put(name, clazz);
		cnNameMap.put(name, StringUtils.isBlank(chineseName) ? name : chineseName);
		nameSet.add(name);
		
		params.put(name, (Class<?>) AnnotationUtils.getValue(annotation, "param"));
	}
	
	public Set<String> getStrategies() {
		return strategyNames;
	}
	
	public Class<?> getParam(String name) {
		return params.get(name);
	}
}
