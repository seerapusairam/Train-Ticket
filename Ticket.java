package trainticket;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Ticket {
    private int counter = 100;
    private String pnr;
    private GregorianCalendar travelDate;
    private Train train;
    private TreeMap <Passenger, Double> passengers;

    public Ticket(Train train, int year, int month, int day) {
        this.train = train;
        this.travelDate = new GregorianCalendar(year, month - 1, day);
        this.pnr = generatePNR();
        passengers = new TreeMap<>();
    }



    public String generatePNR(){
        return
                Character.toString(train.getSource().charAt(0)) +
                Character.toString(train.getDestination().charAt(0)) + "_" +
                travelDate.toZonedDateTime()
                          .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" +
                ++counter;
    }

    public double calcPassengerFare( Passenger passenger)
    {
        if (passenger.getAge() <= 12)
        {
            return train.getTicketPrice() * 0.5;
        }
        if (passenger.getAge() >= 60)
        {
            return train.getTicketPrice() *0.6;
        }
        if (passenger.getGender() == 'F' || passenger.getGender() =='f')
        {
            return train.getTicketPrice() * 0.75;
        } else{
            return train.getTicketPrice();
        }
    }

    public void addPassenger(Passenger pass){
        passengers.put(pass,calcPassengerFare(pass));
    }

    public double calculateTotalTicketPrice(){
        return 0.0;
    }

    public void generateTicket() throws IOException {
        displayPassengers();
    }

    public void writeTicket(){
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public String getTravelDate() {
        return travelDate.toZonedDateTime()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setTravelDate(GregorianCalendar travelDate) {
        this.travelDate = travelDate;
    }

    public TreeMap<Passenger, Double> getPassengers() {
        return passengers;
    }
    // writes passenger info to a file
    public void displayPassengers() throws IOException {
        List<String> headersList = Arrays.asList("Name","Age","Gender","Fare");
        List<List<String>> rowsList = new ArrayList<>(Collections.emptyList());
        double total = 0.0;
        Path p = Paths.get("passenger.txt");
        if (Files.exists(p)){
            Files.delete(p);
        }
        p = Files.createFile(p);
        Files.write(p,("PNR:          " + getPnr() + "\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("Train NO:     " +train.getTrainNo() +"\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("Train Name:   "  + train.getTrainName().replace("\n", " ") + "\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("From:         " + train.getSource() + "\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("To:           " +train.getDestination() + "\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("Travel Date:  " + getTravelDate() + "\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("\n").getBytes(),StandardOpenOption.APPEND);
        Files.write(p,("Passengers:\n").getBytes(),StandardOpenOption.APPEND);
        for(Map.Entry<Passenger,Double> entry : passengers.entrySet()) {
            Passenger pass = entry.getKey();
            Double fare = entry.getValue();
            rowsList.add(Arrays.asList(pass.getName(), Integer.toString(pass.getAge()), Character.toString(pass.getGender()), Double.toString(fare)));
            total += fare;
        }
        Board board = new Board(75);
        String tableString = board.setInitialBlock(new Table(board, 75, headersList, rowsList).tableToBlocks()).build().getPreview();
        Files.write(p,tableString.getBytes(), StandardOpenOption.APPEND);
        Files.write(p, ("Total Price: " + total).getBytes(), StandardOpenOption.APPEND);

    }

    public void setPassengers(TreeMap<Passenger, Double> passengers) {
        this.passengers = passengers;
    }

}
