package edu.sdsu.cs160l.lab9.exception;

// StudentAlreadyEnrolledException checked exception
public class StudentAlreadyEnrolledException extends Exception {
  public StudentAlreadyEnrolledException(String errorMessage) {
    super(errorMessage);
  }
}
