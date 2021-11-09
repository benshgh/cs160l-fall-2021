package edu.sdsu.cs160l.lab9.student;

public class StudentUnits {
  private final IStudentStatus stuStatus;
  private final int units;

  public StudentUnits(IStudentStatus stu, int u) {
    stuStatus = stu;
    units = u;
  }

  public IStudentStatus getStudentStatus() {
    return stuStatus;
  }

  public int getUnits() {
    return units;
  }
}
