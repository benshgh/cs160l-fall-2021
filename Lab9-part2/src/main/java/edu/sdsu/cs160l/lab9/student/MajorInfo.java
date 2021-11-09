package edu.sdsu.cs160l.lab9.student;

public class MajorInfo {

  private final StudentMajor major;

  public MajorInfo(StudentMajor m) {
    major = m;
  }

  public StudentMajor getMajor() {
    return major;
  }

  @Override
  public String toString() {
    return major.toString();
  }
}
