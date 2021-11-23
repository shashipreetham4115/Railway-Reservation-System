package ReservationSystem.Entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Train extends ID{
    public  int available_tickets = 5;
    public int rac_tickets = 2;
    public int waiting_tickets = 2;
    public int totalNoofTickets = 9;
    public int compartment = 1;
    public String seat_berth = "L";
    public int seating = 1;
    public String start;
    public String destination;
    public String date;
    public String time;
    public String name;
    public Queue<Long> rac_waiting_list = new LinkedList<Long>();
    public Queue<Long> waiting_list = new LinkedList<Long>();

    public Map<Long, Ticket> passenger_details = new HashMap<Long, Ticket>();

    public Train(String name,String start,String destination,String date,String time) {
        SetValuesofConstructor(name,start,destination,date,time);
    }

    public Train(String name,String start,String destination,String date,String time,int at, int rt, int wt) {
    	this.available_tickets = at;
    	this.rac_tickets = rt;
    	this.waiting_tickets = wt;
    	this.totalNoofTickets = at + rt + wt;
        SetValuesofConstructor(name,start,destination,date,time);
    }
    
    private void SetValuesofConstructor(String name,String start,String destination,String date,String time) {
    	this.start = start;
    	this.destination = destination;
    	this.date = date;
    	this.time = time;
    	this.name = name;
    	this.id = d.getTime();
    }
}
