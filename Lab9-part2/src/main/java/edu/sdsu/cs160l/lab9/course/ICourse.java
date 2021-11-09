package edu.sdsu.cs160l.lab9.course;

import java.util.Date;
import java.util.List;

public interface ICourse {
  String getCourseName();
  Date offeredFrom();
  Date ended();

  List<String> getCoveredTopics();
  List<String> getPrerequisites();
}
