package trainticket;
import java.io.IOException;
import java.sql.*;
import java.util.GregorianCalendar;
import java.util.Scanner;

import static trainticket.TrainDAO.findTrain;

public class TicketApplication {
    private static Scanner userInput = new Scanner(System.in);
    public static int checkInt(String requestMessage,String errorMessage){
        int out = 0;
        System.out.println(requestMessage);
        while(true){
            try{
                out = Integer.parseInt(userInput.nextLine());
                break;
            } catch (Exception e){
                System.out.println(errorMessage);
            }
        }
        return out;
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        int day = 0, month = 0, year = 0, trainNumber = 0;

        System.out.println("Enter the Train Number");
        while(true){
            try{
                trainNumber = Integer.parseInt(userInput.nextLine());
                if (trainNumber >= 1001 && trainNumber <= 1006){
                    break;
                } else{
                    System.out.println("Please enter a valid train# 1001-1006");
                }
            } catch (Exception e){
                System.out.println("Please only enter numbers without letters!");
            }
        }
        Train myTrain = findTrain(trainNumber);


        System.out.println("Enter Travel Data day month year: ");

        while(true){
            try {
                day = checkInt("enter the day","day must be a number");
                month = checkInt("enter the month","month must be a number");
                year = checkInt("enter the year","year must be a number");
                if(new GregorianCalendar(year, month, day).after(new GregorianCalendar())){
                    break;
                } else {
                    System.out.println("Please input a date that is after today!");
                }
            } catch (Exception e){
                System.out.println("Please enter a valid date");
            }
        }
        Ticket myTicket = new Ticket(myTrain,year,month,day);

        int numberOfPassengers = checkInt("Enter Number of Passengers", "Passengers must be a number");

        for(int i = 0; i < numberOfPassengers; i++){
            System.out.println("Enter Passenger Name");
            String passengerName = userInput.nextLine();

            int passengerAge = checkInt("Enter Age","Age must be a number");

            System.out.println("Enter Gender(M/F)");
            char passengerGender = userInput.nextLine().charAt(0);
            while(true){
                if (Character.toString(passengerGender).toUpperCase().equals("M") ||
                    Character.toString(passengerGender).toUpperCase().equals("F")){
                    break;
                } else{
                    System.out.println("Please enter an M or F");
                    passengerGender = userInput.nextLine().charAt(0);
                }
            }
            myTicket.addPassenger(new Passenger(passengerName, passengerAge, passengerGender));
        }
        myTicket.generateTicket();
        System.out.println("Ticket booked with PNR: " + myTicket.getPnr());
    }
}
