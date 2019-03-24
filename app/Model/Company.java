package Model;

public class Company {
	
	private int id;
	private String name;
	private int sla_time;
	private float sla_percentage;
	private float current_sla_percentage;
	
	
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
	public int getSla_time() {
		return sla_time;
	}
	public void setSla_time(int sla_time) {
		this.sla_time = sla_time;
	}
	public float getSla_percentage() {
		return sla_percentage;
	}
	public void setSla_percentage(float sla_percentage) {
		this.sla_percentage = sla_percentage;
	}
	public float getCurrent_sla_percentage() {
		return current_sla_percentage;
	}
	public void setCurrent_sla_percentage(float current_sla_percentage) {
		this.current_sla_percentage = current_sla_percentage;
	}
	

}
