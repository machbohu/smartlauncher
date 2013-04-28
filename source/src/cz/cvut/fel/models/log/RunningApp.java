package cz.cvut.fel.models.log;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class RunningApp extends Entity {
	public int id;
	public String name;
	public Boolean launched;
	
	public static RunningApp get(String name, Boolean launched){
		return Entity.query(RunningApp.class).where(
				Query.and(Query.eql("name", name), 
						Query.eql("launched", false))).execute();
	}
}
