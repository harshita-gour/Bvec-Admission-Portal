package bvec_admission_model;

import java.util.HashMap;
import java.util.Scanner;

public class AdmissionPortal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HashMap<String, Department> departments = new HashMap<>();

        // Departments
        departments.put("CSE", new Department("CSE", 2));
        departments.put("ETE", new Department("ETE", 2));
        departments.put("MECH", new Department("MECH", 2));
        departments.put("CIVIL", new Department("CIVIL", 2));

        System.out.println("=================================");
        System.out.println("WELCOME TO BVEC ADMISSION SYSTEM");
        System.out.println("=================================");

        while (true) {

            System.out.println("\n1. Take Admission");
            System.out.println("2. View Departments");
            System.out.println("3. Exit");

            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Roll Number: ");
                    int roll = sc.nextInt();

                    System.out.print("Enter Semester: ");
                    int semNo = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Department: ");
                    String deptName =
                            sc.nextLine().toUpperCase();

                    Department dept =
                            departments.get(deptName);

                    if (dept != null) {

                        if (dept.allotSeat()) {

                            Semester sem = dept.semesters.get(semNo);

                            Student s = new Student( name, roll,dept,sem);
                            sem.addStudent(s);

                            System.out.println( "\nAdmission Successful!\n");
                            s.display();

                        } else {

                            System.out.println( "No Seats Available!");
                        }

                    } else {

                        System.out.println("Invalid Department!");
                    }

                    break;

                case 2:

                    for (Department d :
                            departments.values()) {

                        d.display();
                        System.out.println("----------------");
                    }

                    break;

                case 3:

                    System.out.println("Thank You!");
                    sc.close();
                    System.exit(0);

                default:

                    System.out.println("Invalid Choice!");
            }
        }
    }
}