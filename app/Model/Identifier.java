package Model;

public class Identifier {
	
	private int id;
	private String name;
	private long time;
	private int Waiting_time;
	private int companyId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getWaiting_time() {
		return Waiting_time;
	}
	public void setWaiting_time(int waiting_time) {
		Waiting_time = waiting_time;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyid) {
		companyId = companyid;
	}
}
