package edu.sdsu.cs160l.lab9.course;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Provides fake method implementations of the IDataStructure interface
public class DSCourseBase {
  public Date offeredFrom() {
    // fake implementation
    return new Date();
  }

  public Date ended() {
    // fake implementation
    return new Date();
  }

  public List<String> getCoveredTopics() {
    // fake implementation
    return new ArrayList<>();
  }

  public List<String> getPrerequisites() {
    // fake implementation
    return new ArrayList<>();
  }

  public List<String> getCoursesUsingDataStructures() {
    // fake implementation
    return new ArrayList<>();
  }
}
