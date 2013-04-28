package cz.cvut.fel.models.log;

import java.util.List;

import com.roscopeco.ormdroid.Entity;
import com.roscopeco.ormdroid.Query;

import cz.cvut.fel.listeners.LogListener;
import cz.cvut.fel.managers.NetworkManager;

public class BTS extends Entity implements LogListener{
	public int id;
	public int lac;
	public int cid;
	
	@Override
	public List<Entity> onLogSave(LogRecord lr) {
		lac = NetworkManager.getAreaID();
		cid = NetworkManager.getCellID();
		
		if(lac != -1 && cid != -1){
			BTS old = BTS.get(lac, cid);
			
			if(old == null){
				lr.bts = this;
			}else{
				lr.bts = old;
			}
		}
		
		return null;
	}
	
	public static BTS get(int lac, int cid){
		return Entity.query(BTS.class).where(
				Query.and(Query.eql("lac", lac), Query.eql("cid", cid))).execute();
	}
}
