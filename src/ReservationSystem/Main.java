package ReservationSystem;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String userType = null;
        RailwayApp ra = new RailwayApp();
        UserApp ua = new UserApp();
        Login val = new Login();
        Scanner sc = new Scanner(System.in);
        while (true) {
            while (userType == null) {
                userType = val.ValidateUser();
            }
            
            val.greetUser();
            
            while (userType != null) {

                System.out.println("\nPlease Choose Your Choice");
                System.out.println("1. Book Ticket");
                System.out.println("2. Cancel Ticket");
                System.out.println("3. Print Ticket");
                if(userType.equals("admin"))System.out.println("4. Passenger List");
                if(userType.equals("admin"))System.out.println("5. Add New User");
                if(userType.equals("user"))System.out.println("6. My Bookings");
                if(userType.equals("admin"))System.out.println("7. Add New Train");
                System.out.println("8. Change Password");
                System.out.println("9. Logout");
                System.out.println("10. Exit");
                System.out.print("Enter Your Choice Here : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: {
                    	if(userType.equals("admin")) ra.bookTicket();
                    	else ua.bookTicket();
                        break;
                    }
                    case 2: {
                        System.out.print("\n Please Enter Ticket ID : ");
                        if(userType.equals("admin")) ra.cancelTicket(sc.nextLong());
                        else ua.cancelTicket(sc.nextLong());
                        break;
                    }
                    case 3: {
                        System.out.print("\n Please Enter Ticket ID");
                        if(userType.equals("admin")) ra.printTicket(sc.nextLong());
                        else ua.printTicket(sc.nextLong());
                        break;
                    }
                    case 4: if(userType == "admin"){
                    	ra.printPassengers();
                        break;
                    }else {
                    	System.out.println("Please Choose Correct Choice");
                    	break;
                    }
                    case 5: if(userType == "admin"){
                        val.addNewUser();
                        break;
                    }else {
                    	System.out.println("Please Choose Correct Choice");
                    	break;
                    }
                    case 6:{
                    	if(userType.equals("user")) {
                    		ua.myBookings();
                    		break;
                    	}
                    	else {
                        	System.out.println("Please Choose Correct Choice");
                        	break;
                        }
                    }
                    case 7:{
                    	if(userType.equals("admin")) {
                    		ra.addNewTrain();
                    	}
                    	else {
                        	System.out.println("Please Choose Correct Choice");
                        	break;
                        }
                    }
                    case 8: {
                        val.ChangePassword();
                        break;
                    }
                    case 9: {
                        userType = null;
                        break;
                    }
                    case 10: {
                        sc.close();
                        System.exit(0);
                    }
                    default:
                        System.out.println("Please Choose Correct Choice");
                }
            }
        }
    }
}
