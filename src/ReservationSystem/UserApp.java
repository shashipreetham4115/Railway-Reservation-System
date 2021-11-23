package ReservationSystem;

import ReservationSystem.Entities.*;
import java.util.*;

public class UserApp extends BookingSoftware{
	ArrayList<Ticket> user_tickets = new ArrayList<Ticket>();
	
	public void bookTicket() {
		
		User u = Login.getLoggedInUser();
		if (currentTrain.totalNoofTickets > 0) {
	        		Ticket t = super.bookTicket(u.name, u.age);
		        		if(t != null) {
		        		t.user_id = u.id;
		        		user_tickets.add(t);
	        		}
	    } else {
	        System.out.println("Tickets Not Available");
	    }
	}
	
	public void myBookings() {
		
		ArrayList<Ticket> my_tickets = new ArrayList<Ticket>();
		
		if (currentTrain.passenger_details.size() == 0) {
			System.out.println("No Bookings done till now");
			return;
		}
		
		long User_id = Login.getLoggedInUser().id;
		for(Ticket t: user_tickets) {
			System.out.println(t.id == User_id);
			if(t.user_id == User_id) {
				my_tickets.add(t);
			}
		}
		
        printAllTickets(my_tickets);
	}

}
