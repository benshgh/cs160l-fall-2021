package edu.sdsu.cs160l.lab9.course;

import java.util.List;

// Data Structure interface that every data structure course needs to implement
public interface IDataStructure extends ICourse {
  List<String> getCoursesUsingDataStructures();
}
