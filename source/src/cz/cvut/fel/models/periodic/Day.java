package cz.cvut.fel.models.periodic;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class Day extends Entity {
	public int id;
	public String name;
	
	public static Day get(String name){
		return Query.query(Day.class).where(Query.eql("name", name)).execute();
	}
}
