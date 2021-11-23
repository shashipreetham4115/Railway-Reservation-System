package ReservationSystem.Entities;

public class Ticket extends Human {
    
	public String p_berth,p_status;
	public long train_id;
	public long user_id;

    public Ticket(String name, int age, String berth, String status,long train_id) {
        this.id = d.getTime();
        this.name = name;
        this.age = age;
        this.p_berth = berth;
        this.p_status = status;
        this.train_id = train_id;
    }
}
