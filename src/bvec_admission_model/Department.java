package bvec_admission_model;

import java.util.HashMap;

public class Department {

    String name;
    int totalSeats;
    int filledSeats = 0;

    HashMap<Integer, Semester> semesters = new HashMap<>();

    // Constructor
    public Department(String name, int totalSeats) {

        this.name = name;
        this.totalSeats = totalSeats;

        // Create semesters
        for (int i = 1; i <= 8; i++) {

            semesters.put(i, new Semester(i));
        }
    }

    // Seat allotment
    public boolean allotSeat() {

        if (filledSeats < totalSeats) {

            filledSeats++;
            return true;
        }

        return false;
    }

    // Display Department
    public void display() {

        System.out.println("Department: " + name);
        System.out.println("Seats Left: " + (totalSeats - filledSeats));
    }
}