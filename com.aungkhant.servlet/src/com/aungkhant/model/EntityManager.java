package com.aungkhant.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EntityManager<T> {
	private static ConnectionParameter connectionParameter;
	private static Connection connection;
	//private 
	
	
	
	private Connection getConnection() throws Exception {
		Connection conn = null;
		if(connection!=null) {
			conn = connection;
		}else {
		if(connectionParameter!=null) {
			Class.forName(connectionParameter.getDriver());
		    conn = DriverManager.getConnection(connectionParameter.getUrl()+"/"+connectionParameter.getDataBase(), connectionParameter.getUserName(), connectionParameter.getPassword());
		    connection = conn;
		}
		}
		return conn;
	}
	
	public ConnectionParameter getConnectionParameter() {
		return connectionParameter;
	}

	public void setConnectionParameter(ConnectionParameter connectionParameter) {
		EntityManager.connectionParameter = connectionParameter;
	}

	private String getTableName(T t) {
		Class<?> type = t.getClass();
		Annotation annotation = type.getAnnotation(Table.class);
		Table table = (Table)annotation;
		return table.value();
	}
	
	private String getTableName(Class<T> type) {
		Annotation annotation = type.getAnnotation(Table.class);
		Table table = (Table)annotation;
		return table.value();
	}
	
	private Map<String, String> getColumnNameMapForTableCreation(T t) throws Exception {
		Class<?> type = t.getClass();
		Map<String , String> map = new HashMap<String, String>();
		for(Field field: type.getDeclaredFields()) {
			
			String columnName = null;
			String variableName = null;
			String queryString = null;
			if(field.isAnnotationPresent(Id.class)) {
				Annotation annotation = field.getAnnotation(Id.class);
				Id id = (Id)annotation;
				columnName = id.value();
				variableName = field.getName();
				queryString = columnName +" INT NOT NULL PRIMARY KEY AUTO_INCREMENT";
				map.put(columnName, queryString);
			}
			else if(field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column column = (Column)annotation;
				columnName = column.value();
				variableName = field.getName();
				
				if(field.getType().isAssignableFrom(String.class)) {
					queryString  = columnName+" varchar(255)";
				}else if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(long.class)
						||field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class)
						) {
					queryString = columnName + " INT";
				}else if(field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(double.class) || 
						field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(Double.class)
						){
					queryString = columnName + " DECIMAL";
				}else if(field.getType().isAssignableFrom(Boolean.class) || field.getType().isAssignableFrom(boolean.class)) {
					queryString  = columnName+" BOOLEAN";
				}else if(field.getType().isAssignableFrom(Date.class)) {
					queryString = columnName + " datetime";
				}
				map.put(columnName, queryString);
			}
		}
		 
		return map;
	}
	private Map<String, String> getColumnNameMapForInsert(T t) throws Exception {
		Class<?> type = t.getClass();
		Map<String , String> map = new HashMap<String, String>();
		for(Field field: type.getDeclaredFields()) {
			field.setAccessible(true);
			String columnName = null;
			String variableName = null;
			String queryString = null;
			if(field.isAnnotationPresent(Id.class)) {

			}
			else if(field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column column = (Column)annotation;
				columnName = column.value();
				variableName = field.getName();
				
				if(field.getType().isAssignableFrom(String.class)) {
					String value = (String)field.get(t);
					queryString  = "\'"+value+"\'";
				}else if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(long.class)
						||field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class)
						|| field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(double.class) || 
						field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(Double.class)
						) {
					String value = (String)field.get(t).toString();
					queryString  = value;
				}else if(field.getType().isAssignableFrom(Boolean.class) || field.getType().isAssignableFrom(boolean.class)
						||field.getType().isAssignableFrom(Date.class)){
					
				}
				map.put(columnName, queryString);
			}
		}
		 
		return map;
	}
	
	private String getIdConditionStatement(T t) throws Exception{
		String statement = "WHERE ";
		Class<?> type = t.getClass();
		Field field = type.getDeclaredField("id");
		String columnName = ((Id)field.getAnnotation(Id.class)).value();
		field.setAccessible(true);
		Integer id = (Integer)field.get(t);
		statement = statement+columnName+"="+id;
		return statement;
		
	}
	private Map<String, String> getColumnNameMapForUpdate(T t) throws Exception {
		Class<?> type = t.getClass();
		Map<String , String> map = new HashMap<String, String>();
		String whereCondition = "WHERE";
		for(Field field: type.getDeclaredFields()) {
			field.setAccessible(true);
			String columnName = null;
			String variableName = null;
			String queryString = null;
			if(field.isAnnotationPresent(Id.class)) {
				Annotation annotation = field.getAnnotation(Id.class);
				Id id = (Id)annotation;
				columnName = id.value();
				variableName = field.getName();
				Integer value = (Integer)field.get(t);
				whereCondition = whereCondition+" "+columnName+"="+value;
				System.out.println(whereCondition);
			}
			else if(field.isAnnotationPresent(Column.class)) {
				Annotation annotation = field.getAnnotation(Column.class);
				Column column = (Column)annotation;
				columnName = column.value();
				variableName = field.getName();
				
				if(field.getType().isAssignableFrom(String.class)) {
					String value = (String)field.get(t);
					queryString  = columnName+"=\'"+value+"\'";
				}else if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(long.class)
						||field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class)
						|| field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(double.class) || 
						field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(Double.class)
						) {
					String value = (String)field.get(t).toString();
					queryString  = columnName+"="+value;
				}else if(field.getType().isAssignableFrom(Boolean.class) || field.getType().isAssignableFrom(boolean.class)){
					Boolean value = (Boolean)field.get(t);
					if(value==null || value.equals(false)){
						queryString  = columnName+"="+0;
					}
					else{
						queryString  = columnName+"="+1;
					}
				}
				map.put(columnName, queryString);
			}
		}
		map.put("where", whereCondition);
		 
		return map;
	}
	
	public void createTable(T t) throws Exception {
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		String sql = "CREATE TABLE  IF NOT EXISTS "+tableName+"(";
		Map<String , String> columMap = getColumnNameMapForTableCreation(t);
		int i = 0;
		for(String subQuery: columMap.values()) {
			if(i>0) {
			sql = sql+","+subQuery;
			}else {
				sql = sql+subQuery;
			}
			i++;
		}
		sql  = sql+");";
		
		stmt.executeUpdate(sql);
		
	}
	
	
	
	public void create(T t) throws Exception {
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		String columnStatement = "(";
		String valueStatement = "(";
		String sql = "INSERT INTO "+tableName+" ";
		Map<String, String> columnValueMap = getColumnNameMapForInsert(t);
		int i = 0;
		for(Entry<String, String> entry: columnValueMap.entrySet()){
			if(i>0){
				columnStatement = columnStatement+","+entry.getKey();
				valueStatement = valueStatement+","+entry.getValue();
			}else{
				columnStatement = columnStatement+entry.getKey();
				valueStatement = valueStatement+entry.getValue();
			}
			i++;
		}
		columnStatement = columnStatement+")";
		valueStatement = valueStatement+")";
		sql = sql+columnStatement+" VALUES "+valueStatement+";";
		stmt.executeUpdate(sql);
	}
	
	public void update(T t) throws Exception {
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		
		
		String sql = "UPDATE "+tableName+" SET ";
		Map<String, String> columnValueMap = getColumnNameMapForUpdate(t);
		int i = 0;
		for(Entry<String, String> entry: columnValueMap.entrySet()){
			if(!entry.getKey().equals("where")){
				if(i>0){
					sql = sql+","+entry.getValue();
				}else{
					sql = sql+entry.getValue();
				}
			}
			i++;
		}
		sql = sql+" "+columnValueMap.get("where")+";";
		stmt.executeUpdate(sql);
	}
	public void delete(T t) throws Exception {
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		String sql = "DELETE FROM "+tableName+" "+getIdConditionStatement(t)+";";
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}
	
	public List<T> getList(Class<T> type) throws Exception{
		List<T> tList = new ArrayList<T>();
		String tableName = getTableName(type);
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT * FROM "+tableName+" ;";
		ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()){
	    	T t = type.newInstance();
	    	for(Field field: type.getDeclaredFields()) {
				field.setAccessible(true);
				String columnName = null;
				String variableName = null;
				String queryString = null;
				if(field.isAnnotationPresent(Id.class)) {
					Annotation annotation = field.getAnnotation(Id.class);
					Id id = (Id)annotation;
					columnName = id.value();
					variableName = field.getName();
					field.set(t, rs.getInt(columnName));
				}
				else if(field.isAnnotationPresent(Column.class)) {
					Annotation annotation = field.getAnnotation(Column.class);
					Column column = (Column)annotation;
					columnName = column.value();
					variableName = field.getName();
					
					if(field.getType().isAssignableFrom(String.class)) {
						field.set(t, rs.getString(columnName));
						
					}else if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(long.class)
							||field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class)) {
						field.set(t, rs.getInt(columnName));

					}else if(field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(double.class) || 
							field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(Double.class)){
						field.set(t, rs.getDouble(columnName));

					}
					else if(field.getType().isAssignableFrom(Boolean.class) || field.getType().isAssignableFrom(boolean.class)){
						field.set(t, rs.getBoolean(columnName));
//						if(value==null || value.equals(false)){
//							queryString  = columnName+"="+0;
//						}
//						else{
//							queryString  = columnName+"="+1;
//						}
					}
				
				}
			}
	    	tList.add(t);
	    }
	    rs.close();
		
		return tList;
	}
	
	public T findById(T t) throws Exception {
		Class<?> type = t.getClass();
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		String sql = "SELECT * FROM "+tableName+" "+getIdConditionStatement(t)+";";
		ResultSet rs = stmt.executeQuery(sql);
	    while(rs.next()){
	    	for(Field field: type.getDeclaredFields()) {
				field.setAccessible(true);
				String columnName = null;
				String variableName = null;
				String queryString = null;
				if(field.isAnnotationPresent(Id.class)) {
					Annotation annotation = field.getAnnotation(Id.class);
					Id id = (Id)annotation;
					columnName = id.value();
					variableName = field.getName();
					field.set(t, rs.getInt(columnName));
				}
				else if(field.isAnnotationPresent(Column.class)) {
					Annotation annotation = field.getAnnotation(Column.class);
					Column column = (Column)annotation;
					columnName = column.value();
					variableName = field.getName();
					
					if(field.getType().isAssignableFrom(String.class)) {
						field.set(t, rs.getString(columnName));
						
					}else if(field.getType().isAssignableFrom(int.class) || field.getType().isAssignableFrom(long.class)
							||field.getType().isAssignableFrom(Integer.class) || field.getType().isAssignableFrom(Long.class)) {
						field.set(t, rs.getInt(columnName));

					}else if(field.getType().isAssignableFrom(float.class) || field.getType().isAssignableFrom(double.class) || 
							field.getType().isAssignableFrom(Float.class) || field.getType().isAssignableFrom(Double.class)){
						field.set(t, rs.getDouble(columnName));

					}
					else if(field.getType().isAssignableFrom(Boolean.class) || field.getType().isAssignableFrom(boolean.class)){
						field.set(t, rs.getBoolean(columnName));
//						if(value==null || value.equals(false)){
//							queryString  = columnName+"="+0;
//						}
//						else{
//							queryString  = columnName+"="+1;
//						}
					}
				
				}
			}	
	    }
	    rs.close();
	    return t;
	}
}
