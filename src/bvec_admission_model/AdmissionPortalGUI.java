package bvec_admission_model;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdmissionPortalGUI extends Frame implements ActionListener {

    Label title, nameLabel, rollLabel, semLabel, deptLabel;
    TextField nameField, rollField;
    Choice semChoice, deptChoice;
    Button admitBtn, deptBtn, studentBtn, clearBtn, exitBtn;
    TextArea output;

    HashMap<String, Department> departments = new HashMap<>();

    AdmissionPortalGUI() {

        departments.put("CSE", new Department("CSE", 2));
        departments.put("ETE", new Department("ETE", 2));
        departments.put("MECH", new Department("MECH", 2));
        departments.put("CIVIL", new Department("CIVIL", 2));

        setTitle("BVEC Admission Portal");
        setSize(650, 550);
        setLayout(null);
        setBackground(new Color(230, 240, 255));

        title = new Label("BVEC ADMISSION SYSTEM");
        title.setBounds(170, 50, 350, 40);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 51, 102));
        add(title);

        nameLabel = new Label("Student Name:");
        nameLabel.setBounds(100, 130, 120, 30);
        add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(250, 130, 250, 30);
        add(nameField);

        rollLabel = new Label("Roll Number:");
        rollLabel.setBounds(100, 180, 120, 30);
        add(rollLabel);

        rollField = new TextField();
        rollField.setBounds(250, 180, 250, 30);
        add(rollField);

        semLabel = new Label("Semester:");
        semLabel.setBounds(100, 230, 120, 30);
        add(semLabel);

        semChoice = new Choice();
        for (int i = 1; i <= 8; i++) {
            semChoice.add(String.valueOf(i));
        }
        semChoice.setBounds(250, 230, 250, 30);
        add(semChoice);

        deptLabel = new Label("Department:");
        deptLabel.setBounds(100, 280, 120, 30);
        add(deptLabel);

        deptChoice = new Choice();
        deptChoice.add("CSE");
        deptChoice.add("ETE");
        deptChoice.add("MECH");
        deptChoice.add("CIVIL");
        deptChoice.setBounds(250, 280, 250, 30);
        add(deptChoice);

        admitBtn = new Button("Take Admission");
        admitBtn.setBounds(80, 340, 120, 35);
        add(admitBtn);

        deptBtn = new Button("View Departments");
        deptBtn.setBounds(220, 340, 130, 35);
        add(deptBtn);

        studentBtn = new Button("View Students");
        studentBtn.setBounds(370, 340, 120, 35);
        add(studentBtn);

        clearBtn = new Button("Clear");
        clearBtn.setBounds(510, 340, 80, 35);
        add(clearBtn);

        exitBtn = new Button("Exit");
        exitBtn.setBounds(270, 390, 100, 35);
        add(exitBtn);

        output = new TextArea();
        output.setBounds(80, 440, 510, 80);
        output.setEditable(false);
        add(output);

        admitBtn.addActionListener(this);
        deptBtn.addActionListener(this);
        studentBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void saveToDatabase(String name, int roll, int semester, String department) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO students(name, roll, semester, department) VALUES (?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, name);
            pst.setInt(2, roll);
            pst.setInt(3, semester);
            pst.setString(4, department);

            pst.executeUpdate();

            con.close();

        } catch (Exception e) {
            output.setText("Database Error: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == admitBtn) {

            try {
                String name = nameField.getText();
                int roll = Integer.parseInt(rollField.getText());
                int semNo = Integer.parseInt(semChoice.getSelectedItem());
                String deptName = deptChoice.getSelectedItem();

                Department dept = departments.get(deptName);

                if (dept.allotSeat()) {

                    Semester sem = dept.semesters.get(semNo);

                    Student s = new Student(name, roll, dept, sem);

                    sem.addStudent(s);

                    saveToDatabase(name, roll, semNo, deptName);

                    output.setText(
                            "Admission Successful!\n"
                                    + "Name: " + name + "\n"
                                    + "Roll: " + roll + "\n"
                                    + "Department: " + deptName + "\n"
                                    + "Semester: " + semNo
                    );

                } else {
                    output.setText("No seats available in " + deptName);
                }

            } catch (Exception ex) {
                output.setText("Please enter valid details!");
            }
        }

        else if (e.getSource() == deptBtn) {

            String result = "";

            for (Department d : departments.values()) {
                result += d.name + " - Seats Left: "
                        + (d.totalSeats - d.filledSeats) + "\n";
            }

            output.setText(result);
        }

        else if (e.getSource() == studentBtn) {

            String result = "";

            for (Department d : departments.values()) {

                result += "\nDepartment: " + d.name + "\n";

                for (Semester sem : d.semesters.values()) {

                    if (sem.students.size() > 0) {

                        result += "Semester " + sem.semesterNo + ":\n";

                        for (Student s : sem.students) {

                            result += s.name + " | Roll: "
                                    + s.roll + "\n";
                        }
                    }
                }
            }

            if (result.equals("")) {
                result = "No students admitted yet.";
            }

            output.setText(result);
        }

        else if (e.getSource() == clearBtn) {

            nameField.setText("");
            rollField.setText("");
            output.setText("");
        }

        else if (e.getSource() == exitBtn) {

            dispose();
        }
    }

    public static void main(String[] args) {

        new AdmissionPortalGUI();
    }
}