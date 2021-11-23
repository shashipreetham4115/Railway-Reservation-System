package ReservationSystem;

import java.util.*;

import ReservationSystem.Entities.User;
import ReservationSystem.Interfaces.LoginServices;

public class Login implements LoginServices {
	Scanner sc = new Scanner(System.in);
    private static Map<String,User> user = new HashMap<String,User>();
    private static String loggedInUser; 
    Login() {
        User l1 = new User("p","Shashipreetham","Male",21,"admin");
        user.put("shashi",l1);
        User l2 = new User("p","Mahesh","Male",21,"admin");
        user.put("mahesh",l2);
        User l3 = new User("p","Balaji","Male",21,"user");
        user.put("royal",l3);
    }

    public String ValidateUser() {
    	   System.out.println("Please Select Your Choice \n 1) Login \n 2) New User");
    	   switch(sc.nextInt()) {
    	   case 1: break;
    	   case 2: { addNewUser(); break;}
    	   default:return null;
    	   }
           System.out.print("\n Please Enter Your Username :");
           String username = sc.next();
           if(user.containsKey(username)) {
           System.out.print("\n Please Enter Your Password :");
           if (user.get(username).verifyUser(sc.next())) {
        	   loggedInUser = username;
               return user.get(username).userType;
           } else
               System.out.println("\nInvalid password");
           }else System.out.println("\nInvalid Username");
    	   return null;
    }

    public void addNewUser() {
    	while(true) {
	    	System.out.print("\nPlease Enter New Username  :");
	    	String un = sc.next();
	    	if(user.containsKey(un)){
	    		System.out.println("Username not Available");
	    		continue;
	    	}
	    	System.out.print("\nPlease Enter Full Name :");
	    	sc.nextLine();
	    	String name = sc.nextLine();
	    	System.out.print("\nPlease Enter Your Gender :");
	    	String gender = sc.next();
	    	System.out.print("\nPlease Enter Your Age    :");
	    	int age = sc.nextInt();
	        System.out.print("\nPlease Enter Password  :");
	        String pw = sc.next();
	        User l = new User(pw,name,gender,age,loggedInUser == null ? "user":"admin");
	        user.put(un,l);
	        System.out.println("Successfully Added");
	        break;
    	}
    }

    public void ChangePassword() {
        System.out.println("Please Enter Your Old Password");
        String oldPassword = sc.next();
        System.out.println("Please Enter Your New Password");
        if(user.get(loggedInUser).changePassword(oldPassword,sc.next())) {
            System.out.println("Your Password has been changed Successfully");
        } else {
            System.out.println("You Have Entered Wrong Password");
        }
    }

    public void greetUser() {
        System.out.println("\nWelcome " + user.get(loggedInUser).name);
    }
    
    public static User getLoggedInUser() { 
    	return user.get(loggedInUser);
    }
}
