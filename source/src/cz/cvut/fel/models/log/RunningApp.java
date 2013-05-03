package cz.cvut.fel.models.log;

import java.util.List;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class RunningApp extends Entity {
	public int id;
	public String name;
	public boolean launched;
	
	public static RunningApp get(String name, boolean launched){
		return Entity.query(RunningApp.class).where(
				Query.and(Query.eql("name", name), 
						Query.eql("launched", false))).execute();
	}
	
	public static List<RunningApp> filter(String col, Object val){
		return Query.query(RunningApp.class).where(Query.eql(col, val)).executeMulti();
	}
}
