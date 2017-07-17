package com.huatai.platform.downstream.adaptor.o32.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.InvalidFieldLengthException;
import nl.knaw.dans.common.dbflib.InvalidFieldTypeException;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.StringValue;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.Type;
import nl.knaw.dans.common.dbflib.Value;
import nl.knaw.dans.common.dbflib.Version;

public class ExecutionPositionDao {
	
	private static final String POSITION_STR = "positionStr";
	private static final String OPERATOR_NO = "operatorNo";
	private static final String ASSET_NO = "assetNo";
	private final Table table;
	
	/**
	 *
	 * @param file 文件必须已存在
	 * @throws InvalidFieldLengthException
	 * @throws InvalidFieldTypeException
	 * @throws IOException
	 * @throws CorruptedTableException
	 */
	public ExecutionPositionDao(File file) throws InvalidFieldTypeException, InvalidFieldLengthException, CorruptedTableException, IOException {
		List<Field> fields = new ArrayList<Field>();
		fields.add(new Field(ASSET_NO, Type.CHARACTER, 100));
		fields.add(new Field(OPERATOR_NO, Type.CHARACTER, 100));
		fields.add(new Field(POSITION_STR, Type.CHARACTER, 100));
		table = new Table(file, Version.DBASE_5, fields);
		if (file.exists()) {
			table.open();
		} else {
			table.open(IfNonExistent.CREATE);
			
			// 由于dans dbf 的特性，需要重新打开一次
			table.close();
			table.open();
		}
		
	}
	
	public int insert(ExecutionPosition executionPosition) throws IOException, DbfLibException {
		Map<String, Value> valueMap = new HashMap<String, Value>();
		valueMap.put(ASSET_NO, new StringValue(executionPosition.getAssetNo()));
		valueMap.put(OPERATOR_NO, new StringValue(executionPosition.getOperatorNo()));
		valueMap.put(POSITION_STR, new StringValue(executionPosition.getPositionStr()));
		Record record = new Record(valueMap);
		table.addRecord(record);
		return table.getRecordCount() - 1;
	}
	
	public void update(int index, ExecutionPosition executionPosition) throws IOException, DbfLibException {
		Map<String, Value> valueMap = new HashMap<String, Value>();
		valueMap.put(ASSET_NO, new StringValue(executionPosition.getAssetNo()));
		valueMap.put(OPERATOR_NO, new StringValue(executionPosition.getOperatorNo()));
		valueMap.put(POSITION_STR, new StringValue(executionPosition.getPositionStr()));
		Record record = new Record(valueMap);
		table.updateRecordAt(index, record);
	}
	
	public Collection<ExecutionPosition> loadAll() throws CorruptedTableException, IOException {
		
		int count = table.getRecordCount();
		Collection<ExecutionPosition> executionPositions = new ArrayList<ExecutionPosition>();
		
		for (int i = 0; i < count; i++) {
			Record record = table.getRecordAt(i);
			
			String assetNo = record.getStringValue(ASSET_NO);
			String operatorNo = record.getStringValue(OPERATOR_NO);
			String positionStr = record.getStringValue(POSITION_STR);
			ExecutionPosition executionPosition = new ExecutionPosition(assetNo, operatorNo, positionStr);
			executionPosition.setIndex(i);
			executionPositions.add(executionPosition);
		}
		return executionPositions;
	}
	
	public void close() throws IOException {
		table.close();
	}
}
