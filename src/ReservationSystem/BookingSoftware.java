package ReservationSystem;

import java.util.*;
import ReservationSystem.Entities.*;


public class BookingSoftware {
	Scanner sc = new Scanner(System.in);
	static Train currentTrain;
	static Map<Long,Train> ava_trains = new HashMap<Long,Train>();
	public BookingSoftware() {
		if(ava_trains.isEmpty()) {
			Train t1 = new Train("Madurai Special","Warangal","Madurai","23-11-2021","4:00PM");
			currentTrain = t1;
			ava_trains.put(t1.id,t1);
		}
	}

    // This is book ticket method.
    public Ticket bookTicket(String name, int age) {
    	
    	printTrains();
    	
    	System.out.print("\nPlease Enter Train Number from Above List : ");
    	long train_id = sc.nextLong();
    	if(ava_trains.containsKey(train_id)) {
    		currentTrain = ava_trains.get(train_id);
    	}else {
    		System.out.print("\nPlease Enter Correct ID");
    		return null;
    	}

        // First it creates a new Ticket Object
        Ticket p = new Ticket(name, age, "", "",currentTrain.id);

        // Then it Checks Wheather the Tickets are available.
        if (currentTrain.available_tickets > 0) {
            // Here it creates a berth like which compartment and seat number and middle or
            // upper or lower.
            p.p_berth = "S" + currentTrain.compartment + "/" + currentTrain.seating + "/" + currentTrain.seat_berth;
            p.p_status = "SL";
            // Then here it add the passengers to passenger_details map with a ticket_id as
            // a key.
            // where ticket_id is total noof tickets available
            currentTrain.passenger_details.put(p.id, p);

            // Here iam assumming there are 10 seats in a compartment
            if (currentTrain.seating > 10) {

                // so when ever seating reaches 10 then iam going to change compartment
            	currentTrain.compartment++;
            	currentTrain.seat_berth = "L";
            	currentTrain.seating = 1;
            } else {

                // for every ticket iam increasing seat number and change berth
            	currentTrain.seating++;
                if (currentTrain.seat_berth.equals("L"))
                	currentTrain.seat_berth = "M";
                else if (currentTrain.seat_berth.equals("M"))
                	currentTrain.seat_berth = "U";
                else
                	currentTrain.seat_berth = "L";
            }
            currentTrain.available_tickets--;

            // Here if there are no available tickets then it checks wheather there are
            // rac_tickets.
        } else if (currentTrain.rac_tickets > 0) {
            // here it create a status of the ticket along with waiting list number.
            p.p_berth = "RAC" + "/" + currentTrain.rac_waiting_list.size()+1;
            p.p_status = "RAC";

            // Then here it add the passengers to passenger_details map with a ticket_id as
            // a key.
            // where ticket_id is total noof tickets available
            currentTrain.passenger_details.put(p.id, p);

            // for every passengers in rac it maintains a queue so here iam adding every rac
            // Ticket to rac queue
            currentTrain.rac_waiting_list.add(p.id);
            currentTrain.rac_tickets--;

            // here if there are no tickets then it adds in a waiting_list
        } else {
            // here it create a status of the ticket along with waiting list number.
            p.p_berth = "WL" + "/" + currentTrain.waiting_list.size()+1;
            p.p_status = "WL";

            // Then here it add the passengers to passenger_details map with a ticket_id as
            // a key.
            // where ticket_id is total noof tickets available
            currentTrain.passenger_details.put(p.id, p);

            // for every passengers in waiting list it maintains a queue so here iam adding
            // every wl Ticket to waiting list queue
            currentTrain.waiting_list.add(p.id);
            currentTrain.waiting_tickets--;
        }
        currentTrain.totalNoofTickets--;
        System.out.println("\nTicket Booked Successfully\n");
        print(p);
        return p;
    }

    // this is method used for canceling Tickets
    public void cancelTicket(long id) {

        // First it checks wheather ticket is booked or not with given ticket id.
        if (currentTrain.passenger_details.containsKey(id)) {

            // Checks status of ticket if it is general then, Before removing the ticket
            // from the Ticket list it checks wheather any ticket is in rac waiting
            // queue.
            if (currentTrain.passenger_details.get(id).p_status.equals("SL")) {
                if (!currentTrain.rac_waiting_list.isEmpty()) {

                    // if yes it gives the canceled ticket to rac
                    String rac_berth = currentTrain.passenger_details.get(id).p_berth;
                    long rac_id = currentTrain.rac_waiting_list.poll();

                    // and then it changes the berth of Ticket to general from rac
                    Ticket racP = currentTrain.passenger_details.get(rac_id);
                    racP.p_berth = rac_berth;
                    racP.p_status = "SL";

                    // here it checls weather there is any waiting list queue
                    if (!currentTrain.waiting_list.isEmpty()) {

                        // if yes then it adds head of the waiting list queue in rac_waiting_list queue
                    	currentTrain.rac_waiting_list.add(currentTrain.waiting_list.poll());
                        currentTrain.waiting_tickets++;

                        // this is berth correction method where after removing any berth status will be
                        // corrected using this method
                        MakeberthCorrections("WL");
                    } else
                    	currentTrain.rac_tickets++;

                    MakeberthCorrections("RAC");
                } else
                	currentTrain.available_tickets++;

                // Here it checks weather given passenger_id is in rac then it will remove and
                // make checks for waiting_list queue if any.
            } else if (currentTrain.passenger_details.get(id).p_berth.equals("RAC")) {
            	currentTrain.rac_waiting_list.remove(id);

                // if there is any waiting list passengers then it addes them to
                // rac_waiting_list queue.
                if (!currentTrain.waiting_list.isEmpty()) {
                	currentTrain.rac_waiting_list.add(currentTrain.waiting_list.poll());
                    currentTrain.waiting_tickets++;
                } else
                	currentTrain.rac_tickets++;

                MakeberthCorrections("RAC");

                // Else given id is an waiting list id then it removes the id from waiting_list
                // queue.
            } else {
            	currentTrain.waiting_list.remove(id);
                MakeberthCorrections("WL");
                currentTrain.waiting_tickets++;
            }

            // At last here it will remove the canceled ticket from the passenger_details
            // Map.
            currentTrain.passenger_details.remove(id);
            System.out.println("Ticket Cancelled Successfully");

        } else {
            System.out.println("Invalid Ticket ID");
        }
    }

    // This method is for printing a particular ticket.
    public void printTicket(long id) {

        // here it checks wheather the given id is booked a ticket if yes it print the
        // Ticket details else prints an error

        if (currentTrain.passenger_details.containsKey(id))
            print(currentTrain.passenger_details.get(id));
        else
            System.out.println("Please Enter the Valid Ticket ID");
    }

    void printTrains() {
    	
    	System.out.print("\nEnter From Station Name : ");
    	String start = sc.nextLine().toLowerCase();
    	System.out.print("\nEnter To Station Name : ");
    	String destination = sc.nextLine().toLowerCase();
    	System.out.print("Please use '-' while entering date as seperator");
    	System.out.print("\nEnter date of travel : ");
    	String date = sc.nextLine();
    	
    	System.out.println("\n\n-----------------------------------------------------------------------------");
        System.out.format("%1$-20s%2$-20s%3$-20s%4$-20s","Train_ID","Train","Time","Available Seats");
        System.out.println("\n-----------------------------------------------------------------------------\n");
        int i = 0;
        for (Train p : ava_trains.values()) {
        	if(start.equals(p.start.toLowerCase()) && destination.equals(p.destination.toLowerCase()) && date.equals(p.date)) {
        		System.out.format("%1$-20s%2$-20s%3$-20s%4$-10s",p.id,p.name,p.time,p.available_tickets);
        		System.out.println();
        		i++;
        	}
        }
        if(i==0) {
        	System.out.print("\nNo Trains Found...");
        }
        System.out.println("\n-----------------------------------------------------------------------------\n");
    }
    
    void printAllTickets(ArrayList<Ticket> pd) {
    	System.out.println("\n------------------------------------------------------------------------------------------------------------------------------");
        System.out.format("%1$-15s%2$-15s%3$-15s%4$-15s%5$-15s%6$-15s%7$-15s%8$-15s","Ticket_ID","Name","Age","From","To","Date","Time","Status/berth");
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------\n");
        for (Ticket p : pd) {
        	Train t = ava_trains.get(p.train_id);
            System.out.format("%1$-15s%2$-15s%3$-15s%4$-15s%5$-15s%6$-15s%7$-15s%8$-15s",p.id,p.name,p.age,t.start,t.destination,t.date,t.time,p.p_berth);
            System.out.println();
        }
        System.out.println("\n------------------------------------------------------------------------------------------------------------------------------\n");
    }
    
    void print(Ticket p) {
        System.out.println("--------------------------\n");
        System.out.println("Ticket ID : " + p.id);
        System.out.println("Name      : " + p.name);
        System.out.println("Age       : " + p.age);
        System.out.println("berth     : " + p.p_berth);
        System.out.println("--------------------------\n");
    }

    void MakeberthCorrections(String qn) {
        int j = 1;
        for (Long i : qn.equals("RAC") ? currentTrain.rac_waiting_list : currentTrain.waiting_list) {
        	currentTrain.passenger_details.get(i).p_berth = qn + "/" + j;
        	currentTrain.passenger_details.get(i).p_status = qn;
            j++;
        }
    }

}
