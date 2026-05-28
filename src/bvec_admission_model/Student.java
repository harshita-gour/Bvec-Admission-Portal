package bvec_admission_model;

public class Student {

    String name;
    int roll;
    Department department;
    Semester semester;

    // Constructor
    public Student(String name,
                   int roll,
                   Department department,
                   Semester semester) {

        this.name = name;
        this.roll = roll;
        this.department = department;
        this.semester = semester;
    }

    // Display Student
    public void display() {

        System.out.println("Name: " + name);
        System.out.println("Roll: " + roll);
        System.out.println("Department: " + department.name);
        System.out.println("Semester: " + semester.semesterNo);
    }
}
