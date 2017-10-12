package com.inspiration.hbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.io.encoding.DataBlockEncoding;
import org.apache.hadoop.hbase.regionserver.BloomType;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * 
 * 说明:Hbase操作类
 *
 * @version 1.0
 *
 * @author yf
 *
 * @update 2016年3月3日
 */
public class HbaseOperator {

	private static Logger logger = LoggerFactory.getLogger(HbaseOperator.class);

	private static Configuration config = null;

	private static int connectionThreadNumbers = 8;

	private Connection conn = null;

	/**
	 * 
	 * 说明:权限认证
	 *
	 * @param user
	 * @param keytabPath
	 * @return
	 */
	private static boolean auth(final String user, final String keytabPath, final String krb5Path) {
		boolean retValue = false;
		try {
			System.setProperty("java.security.krb5.conf", krb5Path);
			// org.apache.hadoop.conf.Configuration conf = new
			// org.apache.hadoop.conf.Configuration();
			config.set("hadoop.security.authentication", "Kerberos");
			config.set("keytab.file", keytabPath);
			config.set("kerberos.principal", user);
			UserGroupInformation.setConfiguration(config);
			UserGroupInformation.loginUserFromKeytab(user, keytabPath);
			retValue = true;
		} catch (Exception e) {
			logger.error("Hadoop auth error.User is {}.KeytabPath is {}", user, keytabPath, e);
		}
		return retValue;
	}

	/**
	 * 初始化配置
	 * 
	 * @param zkQuorum
	 * @param zkPort
	 * @param hbaseDir
	 * @return
	 */
	public static Configuration initConfig(final String zkQuorum, final int zkPort, final String hbaseDir) {
		if (config == null)
			config = HBaseConfiguration.create();

		config.set("hbase.zookeeper.quorum", zkQuorum);
		config.set("hbase.zookeeper.property.clientPort", String.valueOf(zkPort));
		config.set("zookeeper.znode.parent", hbaseDir);

		return config;
	}

	/**
	 * 
	 * Note: 初始化配置文件
	 *
	 * @param configPath
	 *            配置文件地址
	 * @param threadPoolSize
	 *            线程池大小
	 * @param isAuth
	 *            是否授权
	 * @param user
	 *            用户
	 * @param keytabPath
	 *            授权文件地址
	 * @param krb5Path
	 * @return
	 */
	public synchronized static Configuration initConfig(final String configPath, int threadPoolSize, boolean isAuth,
			final String user, final String keytabPath, final String krb5Path) {
		if (config != null) {
			return config;
		}
		logger.info("Hadoop auth.User is {}.KeytabPath is {}.krb5Path is {}.configPath is {}.", user, keytabPath,
				krb5Path, configPath);
		config = HBaseConfiguration.create();
		if (isAuth && !auth(user, keytabPath, krb5Path)) {
			logger.error("Hbase auth error.Shutdown the program.");
			System.exit(0);
		}
		InputStream is = null;
		try {
			if (!Strings.isNullOrEmpty(configPath)) {
				File file = new File(configPath);
				is = new FileInputStream(file);
				config.addResource(is);
			}
			logger.info("hbase.rootdir is " + config.get("hbase.rootdir"));
		} catch (Exception e) {
			logger.error("ConfigPath is {}", configPath, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Close InputStream error", e);
				}
			}
		}
		connectionThreadNumbers = threadPoolSize;
		return config;
	}

	private static HbaseOperator instance = null;

	private HbaseOperator() {
		;
	}

	/**
	 * 
	 * @description 单例初始化HbaseOperator操作类
	 * 
	 * @return
	 */
	public synchronized static HbaseOperator init() {
		if (instance == null) {
			instance = new HbaseOperator();
		}
		return instance;
	}

	/**
	 * 
	 * 说明:获取连接
	 *
	 * @return
	 * @throws IOException
	 */
	public synchronized Connection getConnection() throws IOException {
		if (conn != null && !conn.isClosed()) {
			return conn;
		} else {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			conn = ConnectionFactory.createConnection(config, Executors.newFixedThreadPool(connectionThreadNumbers));
			logger.info("Connect hbase,config {}", config.toString());
		}
		if (conn == null) {
			throw new NullPointerException("Connection is null");
		}
		return conn;

	}

	/**
	 * 
	 * 说明:创建表
	 *
	 * @param tableName
	 * @param familyName
	 * @param ttl
	 * @return
	 * @throws IOException
	 */
	public boolean creatTable(String tableName, String familyName, int ttl) throws IOException {
		boolean retValue = false;
		Admin admin = this.getConnection().getAdmin();
		TableName table = TableName.valueOf(tableName);

		if (admin.tableExists(table)) {
			retValue = false;
			logger.error("table {} is exists!!!", tableName);
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(table);
			HColumnDescriptor clu = new HColumnDescriptor(familyName);
			clu.setBloomFilterType(BloomType.ROWCOL);
			clu.setCompactionCompressionType(Compression.Algorithm.LZO);
			clu.setCompressionType(Compression.Algorithm.LZO);
			clu.setBlocksize(6144000);
			clu.setMaxVersions(3);
			clu.setInMemory(true);
			clu.setBlockCacheEnabled(true);
			clu.setDataBlockEncoding(DataBlockEncoding.FAST_DIFF);
			clu.setTimeToLive(ttl);
			tableDesc.addFamily(clu);

			admin.createTable(tableDesc);
			retValue = true;
			logger.info("Creat table {},family name {}", tableName, familyName);
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:创建表
	 *
	 * @param tableName
	 * @param familyName
	 * @param ttl
	 * @param splitKeys
	 * @return
	 * @throws IOException
	 */
	public boolean creatTable(String tableName, String familyName, int ttl, byte[][] splitKeys) throws IOException {
		boolean retValue = false;
		Admin admin = this.getConnection().getAdmin();
		TableName table = TableName.valueOf(tableName);

		if (admin.tableExists(table)) {
			retValue = false;
			logger.error("table {} is exists!!!", tableName);
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(table);
			HColumnDescriptor clu = new HColumnDescriptor(familyName);
			clu.setBloomFilterType(BloomType.ROWCOL);
			clu.setCompactionCompressionType(Compression.Algorithm.LZO);
			clu.setCompressionType(Compression.Algorithm.LZO);
			clu.setBlocksize(6144000);
			clu.setMaxVersions(3);
			clu.setInMemory(true);
			clu.setBlockCacheEnabled(true);
			clu.setDataBlockEncoding(DataBlockEncoding.FAST_DIFF);
			clu.setTimeToLive(ttl);
			tableDesc.addFamily(clu);

			admin.createTable(tableDesc, splitKeys);
			retValue = true;
			logger.info("Creat table {},family name {}", tableName, familyName);
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:删除表
	 *
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public boolean deleteTable(String tableName) throws IOException {
		boolean retValue = false;
		Admin admin = this.getConnection().getAdmin();
		TableName cTableName = TableName.valueOf(tableName);

		if (admin.tableExists(cTableName)) {
			admin.disableTable(cTableName);
			admin.deleteTable(cTableName);
			logger.info("delete table {}", tableName);
		} else {
			logger.info("table " + tableName + " is not exist");
			retValue = true;
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:得到表
	 *
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	public Table getTable(String tableName) throws IOException {
		Connection tempConn = this.getConnection();
		Table table = null;
		if (tempConn == null) {
			return table;
		}
		table = this.getConnection().getTable(TableName.valueOf(tableName));
		return table;
	}

	/**
	 * 
	 * 说明:根据开始rowkey和结束rowkey获取结果集
	 *
	 * @param tableName
	 * @param startRowKey
	 * @param endRowKey
	 * @return
	 * @throws IOException
	 */
	public ResultScanner searchByRangeRowkeys(String tableName, String startRowKey, String endRowKey)
			throws IOException {
		Table table = this.getTable(tableName);

		if (table == null) {
			return null;
		}
		Scan scan = new Scan();
		scan.setCaching(1000);

		scan.setStartRow(startRowKey.getBytes());
		scan.setStopRow(endRowKey.getBytes());

		ResultScanner scanner = table.getScanner(scan);
		if (table != null) {
			table.close();
		}
		return scanner;
	}

	/**
	 * 
	 * 说明:根据表名和过滤器获取结果集
	 *
	 * @param tableName
	 * @param scan
	 * @return
	 * @throws IOException
	 */
	public ResultScanner searchByScan(String tableName, Scan scan) throws IOException {
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return null;
		}
		ResultScanner scanner = table.getScanner(scan);

		if (table != null) {
			table.close();
		}
		return scanner;
	}

	/**
	 * 
	 * 说明:根据rowkey获取结果
	 *
	 * @param tableName
	 * @param rowkey
	 * @return
	 * @throws IOException
	 */
	public Result getByRowKey(String tableName, String rowkey) throws IOException {
		Result retValue = null;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		Get get = new Get(rowkey.getBytes());
		retValue = table.get(get);
		if (table != null) {
			table.close();
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:根据rokey删除数据
	 *
	 * @param tableName
	 * @param rowkey
	 * @return
	 * @throws IOException
	 */
	public boolean deleteByRowkey(String tableName, String rowkey) throws IOException {
		boolean retValue = false;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		Delete delete = new Delete(rowkey.getBytes());
		table.delete(delete);
		retValue = true;
		if (table != null) {
			table.close();
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:计数器
	 *
	 * @param tableName
	 * @param increment
	 * @return
	 * @throws IOException
	 */
	public boolean increment(String tableName, Increment increment) throws IOException {
		boolean retValue = false;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}

		retValue = (table.increment(increment) != null);

		if (table != null) {
			table.close();
		}
		return retValue;
	}

	/**
	 * 
	 * 说明:计数器
	 *
	 * @param tableName
	 * @param rowkey
	 * @param family
	 * @param qualifier
	 * @param amount
	 * @return
	 * @throws IOException
	 */
	public long incrementColumn(String tableName, String rowkey, String family, String qualifier, long amount)
			throws IOException {
		long retValue = -1;

		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		retValue = table.incrementColumnValue(Bytes.toBytes(rowkey), Bytes.toBytes(family), Bytes.toBytes(qualifier),
				amount);
		if (table != null) {
			table.close();
		}
		return retValue;
	}

	/**
	 * 
	 * 同步写数据
	 * 
	 * @param tableName
	 * @param put
	 * @return
	 * @throws IOException
	 */
	public boolean insertData(String tableName, Put put) throws IOException {
		boolean retValue = false;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		// table.setWriteBufferSize(12582912L);
		table.put(put);
		retValue = true;
		if (table != null) {
			table.close();
		}
		return retValue;
	}

	/**
	 * 
	 * 同步写数据
	 * 
	 * @param tableName
	 * @param puts
	 * @return
	 * @throws IOException
	 */
	public boolean insertData(String tableName, List<Put> puts) throws IOException {
		boolean retValue = false;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		// table.setWriteBufferSize(12582912L);
		table.put(puts);
		retValue = true;
		if (table != null) {
			table.close();
		}
		return retValue;
	}

	public boolean insertData(String tableName, String familyName, String qualifierName, String rowKey, String value)
			throws IOException {
		boolean retValue = false;
		Table table = this.getTable(tableName);

		if (table == null) {
			logger.warn("The table is null");
			return retValue;
		}
		// table.setWriteBufferSize(12582912L);
		Put pt = new Put(rowKey.getBytes());
		pt.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(qualifierName), Bytes.toBytes(value));
		table.put(pt);
		retValue = true;
		if (table != null) {
			table.close();
		}
		return retValue;
	}
}
