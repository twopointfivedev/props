package net.property.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

@Deprecated
public class Location {
	private long id = -1;
	private String zipcode = "";
	private List<String> levels = new ArrayList<String>();
	private List<Long> levelIDs = new ArrayList<Long>();
	
	public String[] getLevels() {
		String[] arrLevels = new String[levels.size()];
		arrLevels = levels.toArray(arrLevels);
		return arrLevels;
	}
		
	public void setLocation(String location, int level, long id) {
		if (levels.size() >= level) {
			levels.set(level-1, location);
		} else {
			int addLevels = level - levels.size();
			for (int i = 0; i < addLevels - 1; i++) {
				levels.add("");
			}
			levels.add(location);
		}
		
		if (id > 0) {
			if (levelIDs.size() >= level) {
				levelIDs.set(level-1, id);
			} else {
				int addLevels = level - levelIDs.size();
				for (int i = 0; i < addLevels - 1; i++) {
					levelIDs.add(-1l);
				}
				levelIDs.add(id);
			}
		}
	}
	
	public void setLocation(String location, int level) {
		setLocation(location, level, -1);
	}
	
	public String getLocation(int level) {
		if (levels.size() >= level) {
			return levels.get(level-1);
		} else {
			return "";
		}
	}
	
	public Long getLevelID (int level) {
		if (levelIDs.size() > level) {
			return levelIDs.get(level);
		} else {
			return -1l;
		}
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getLevel() {
		int level = -1;
		for (Long id: levelIDs) {
			if (id > 0) {
				if (level == -1) {
					level = 0;
				}
				level++;
			}
		}
		return level;
	}
	
	public boolean containsID(long id) {
		if (id > 0) {
			return levelIDs.contains(id);
		} else { 
			return false;
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			Location other = (Location) obj;
			if (this.id == -1 || other.getId() == -1) {
				return false;
			} else if (this.id == other.getId()) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public int hashCode() {
		return Integer.parseInt(DigestUtils.md5Hex(String.valueOf(id)).substring(8,15),16);
	}
	
	@Override
	public String toString() {
		String location = "";
		for (String level: levels) {
			if (!level.isEmpty()) {
				location += level + ",";
			}
		}
		return location;
	}
}
