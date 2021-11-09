package edu.sdsu.cs160l.lab9.college;

import edu.sdsu.cs160l.lab9.student.IStudentStatus;


public interface IRegistrar {
  // Set graduation path (strategy) based on student major and level
  // Set course path for each required learning category
  void setGraduationPath(IStudentStatus studentStatus);
}
