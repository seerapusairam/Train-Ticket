package trainticket;

public class Passenger implements Comparable {
    private String name;
    private int age;
    private char gender;
    int counter = 0;
    public Passenger(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(Object o) {
        if (((Passenger) o).getName().toUpperCase().compareTo(getName().toUpperCase()) == 0){
            return 1;
        }
        else{
            return -1;
        }
    }
}
