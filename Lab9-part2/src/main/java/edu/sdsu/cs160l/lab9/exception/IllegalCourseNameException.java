package edu.sdsu.cs160l.lab9.exception;

// Customized runtime exception - IllegalCourseNameException
public class IllegalCourseNameException extends IllegalArgumentException {
  public IllegalCourseNameException(String errorMessage) {
    super(errorMessage);
  }
}
