package cz.cvut.fel.models.periodic;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

public class Period extends Entity {
	public int id;
	public String name;
	
	public static Period get(String name){
		return Query.query(Period.class).where(Query.eql("name", name)).execute();
	}
}
