package bvec_admission_model;

import java.util.ArrayList;

public class Semester {

    int semesterNo;

    ArrayList<Student> students = new ArrayList<>();

    // Constructor
    public Semester(int semesterNo) {

        this.semesterNo = semesterNo;
    }

    // Add Student
    public void addStudent(Student s) {

        students.add(s);
    }

    // Display Students
    public void displayStudents() {

        for (Student s : students) {

            s.display();
            System.out.println("----------------");
        }
    }
}
