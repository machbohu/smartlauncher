package cz.cvut.fel.models.periodic;

import java.util.List;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class AppDay extends Entity {
	public int id;
	public App app;
	public Day day;
	
	public static List<AppDay> filter(String col, Object val, String col2, Object val2){
		return Query.query(AppDay.class).where(Query.and(
				Query.eql(col, val), Query.eql(col2, val2))).executeMulti();
	}
}
