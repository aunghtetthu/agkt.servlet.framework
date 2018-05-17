package com.aungkhant.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
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
	
	private Map<String, String> getColumnNameMap(T t) throws Exception {
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
	
	public void createTable(T t) throws Exception {
		String tableName = getTableName(t);
		Statement stmt = getConnection().createStatement();
		String sql = "CREATE TABLE  IF NOT EXISTS "+tableName+"(";
		Map<String , String> columMap = getColumnNameMap(t);
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
	
	
	
	public void create(T t) {
		Class<?> type = t.getClass();
		Annotation annotation = type.getAnnotation(Table.class);
		//type.get
		for(Field field: type.getDeclaredFields()) {
			if(field.isAnnotationPresent(Column.class)) {
				
			}
		}
	}
}
