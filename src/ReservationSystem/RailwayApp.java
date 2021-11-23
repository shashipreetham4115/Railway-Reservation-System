package ReservationSystem;

import java.util.*;

import ReservationSystem.Entities.*;

public class RailwayApp extends BookingSoftware{
	Scanner sc = new Scanner(System.in);
	public void printPassengers() {
        if (currentTrain.passenger_details.size() == 0) {
            System.out.println("No Bookings done till now");
            return;
        }
        printAllTickets(new ArrayList<Ticket>(currentTrain.passenger_details.values()));
    }
	
public void bookTicket() {
	if (currentTrain.totalNoofTickets > 0) {
        System.out.print("Please Enter Name : ");
        String name = sc.next();
        System.out.print("Please Enter Age  : ");
        int age = sc.nextInt();
        super.bookTicket(name, age);
    } else {
        System.out.println("Tickets Not Available");
    }
}
	
	public void addNewTrain() {
		
		try {
		
		System.out.print("\nEnter Train Name :");
		String name = sc.nextLine();
		System.out.print("\nEnter Train Starting Point Location :");
		String start = sc.nextLine();
		System.out.print("\nEnter Train Destination Location :");
		String destination = sc.nextLine();
		System.out.print("\nEnter Train Starting Date :");
		String date = sc.nextLine();
		System.out.print("\nEnter Train Starting Time :");
		String time = sc.nextLine();
		
		System.out.print("\nIf You want add no.of available tickets and rac and waiting list please type y else n : ");
		if(sc.next().equals("y")) {
			System.out.print("\nEnter Train's Availale Tickets : ");
			int ava = sc.nextInt();
			System.out.print("\nEnter Train's RAC Tickets : ");
			int rac = sc.nextInt();
			System.out.print("\nEnter Train Waiting Tickets : ");
			int wl = sc.nextInt();
			Train t = new Train(name,start,destination,date,time,ava,rac,wl);
			ava_trains.put(t.id,t);
		}
		else {
			Train t = new Train(name,start,destination,date,time);
			ava_trains.put(t.id,t);
		}
		
		System.out.println("Sucessfully Added...");
		
		}catch(Exception e) {
			System.out.println("Please Enter All Details Correctly");
		}
	}
	

}
