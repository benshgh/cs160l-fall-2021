package edu.sdsu.cs160l.lab9.student;

public class StudentGrade {
  private final IStudentStatus stuStatus;
  private final double grade; //A: 4.0, B: 3.0, C: 2.0, D: 1.0, F: 0

  public StudentGrade(IStudentStatus stu, double g) {
    stuStatus = stu;
    grade = g;
  }

  public IStudentStatus getStudentStatus() {
    return stuStatus;
  }

  public double getGrade() {
    return grade;
  }
}
