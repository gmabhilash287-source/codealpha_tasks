import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> marks = new ArrayList<>();
    double average;
    int highest;
    int lowest;

    // Constructor
    public Student(String name, ArrayList<Integer> marks) {
        this.name = name;
        this.marks = marks;
        calculateStats();
    }

    // Calculate average, highest, and lowest
    private void calculateStats() {
        int sum = 0;
        highest = marks.get(0);
        lowest = marks.get(0);

        for (int mark : marks) {
            sum += mark;
            if (mark > highest) highest = mark;
            if (mark < lowest) lowest = mark;
        }
        average = (double) sum / marks.size();
    }

    // Get Grade
    public String getGrade() {
        if (average >= 90) return "A+";
        else if (average >= 80) return "A";
        else if (average >= 70) return "B";
        else if (average >= 60) return "C";
        else if (average >= 50) return "D";
        else return "F";
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for Student " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Enter number of subjects: ");
            int subCount = sc.nextInt();

            ArrayList<Integer> marks = new ArrayList<>();
            for (int j = 0; j < subCount; j++) {
                System.out.print("Enter marks for subject " + (j + 1) + ": ");
                marks.add(sc.nextInt());
            }
            sc.nextLine(); // consume newline

            students.add(new Student(name, marks));
        }

        // Display Summary Report
        System.out.println("\n===== STUDENT GRADE SUMMARY =====");
        System.out.printf("%-15s %-10s %-10s %-10s %-10s%n",
                "Name", "Average", "Highest", "Lowest", "Grade");
        System.out.println("-----------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-15s %-10.2f %-10d %-10d %-10s%n",
                    s.name, s.average, s.highest, s.lowest, s.getGrade());
        }

        sc.close();
    }
}
