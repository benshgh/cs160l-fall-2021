package edu.sdsu.cs160l.lab9.student;

import edu.sdsu.cs160l.lab9.student.path.ILearningPathBehavior;

public interface IGraduationPath {
  // Set course path for each required learning category
  void setGEPathBehavior(ILearningPathBehavior lpb);

  void setLowerDivisionPathBehavior(ILearningPathBehavior lpb);

  void setUpperDivisionPathBehavior(ILearningPathBehavior lpb);

  void setMathPathBehavior(ILearningPathBehavior lpb);

  void setMiscPathBehavior(ILearningPathBehavior lpb);

  // Fulfill learning paths for graduation
  void fulfillGEPath();

  void fulfillLowerDivisionPath();

  void fulfillUpperDivisionPath();

  void fulfillMathPath();

  void fulfillMiscPath();

  // Implement as a Template method following the template pattern.
  void completeGraduationPath();
}
