package edu.sdsu.cs160l.lab9.student;

import edu.sdsu.cs160l.lab9.college.IRegistrar;
import edu.sdsu.cs160l.lab9.college.IRegistrarChangeListener;
import edu.sdsu.cs160l.lab9.college.Registrar;
import edu.sdsu.cs160l.lab9.course.college.LearningPathType;
import edu.sdsu.cs160l.lab9.student.path.ILearningPathBehavior;

import java.util.ArrayList;
import java.util.List;

public class Student implements IStudentStatus,
   IGraduationPath,
   IRegistrarChangeListener {
  private int redId;
  private StudentLevel studentLevel;
  private StudentMajor studentMajor;

  //private ArrayList<ICourse> s_courses;  //for future uses
  private ArrayList<MajorInfo> studentMajors; //for future uses, a student could have multiple majors

  private ILearningPathBehavior gePath = null;
  private ILearningPathBehavior lowerDivisionPath = null;
  private ILearningPathBehavior upperDivisionPath = null;
  private ILearningPathBehavior mathPath = null;
  private ILearningPathBehavior miscPath = null;

  // Constructor - initialize with Student Level and Major
  public Student(int id, StudentLevel l, StudentMajor m) {
    redId = id;
    studentLevel = l;
    studentMajor = m;

    studentMajors = new ArrayList<>(); //for future uses
    studentMajors.add(new MajorInfo(m)); //for future uses
  }

  //for future uses
  public void AddMajor(StudentMajor m) {
    if (studentMajors != null)
      studentMajors.add(new MajorInfo(m));
  }

  @Override
  public String toString() {
    
    StringBuilder sb = new StringBuilder();
    studentMajors.forEach(m -> sb.append(m.toString()).append(" "));

    return "Level = " + studentLevel + ", Major/s = " + sb;
  }

  /**
   * IStudentStatus implementation
   */
  @Override
  public StudentLevel getStudentLevel() {
    return studentLevel;
  }

  @Override
  public StudentMajor getStudentMajor() {
    return studentMajor;
  }

  @Override
  public int getStudentID() {
    return redId;
  }

  /**
   * IGraduationPath implementation
   */
  // Set the Graduation path for each learning category
  @Override
  public void setGEPathBehavior(ILearningPathBehavior lpb) {
    gePath = lpb;
  }

  @Override
  public void setLowerDivisionPathBehavior(ILearningPathBehavior lpb) {
    lowerDivisionPath = lpb;
  }

  @Override
  public void setUpperDivisionPathBehavior(ILearningPathBehavior lpb) {
    upperDivisionPath = lpb;
  }

  @Override
  public void setMathPathBehavior(ILearningPathBehavior lpb) {
    mathPath = lpb;
  }

  @Override
  public void setMiscPathBehavior(ILearningPathBehavior lpb) {
    miscPath = lpb;
  }

  // Fulfill learning paths for graduation
  @Override
  public void fulfillGEPath() {
    System.out.println("Complete the GE learning path based on the GE path behavior");
    if (gePath != null) {
      System.out.println(gePath.getPathBehavior());
    }
  }

  @Override
  public void fulfillLowerDivisionPath() {
    System.out.println("Complete the Lower Division learning path based on the Lower division path behavior");
    // Do the same as FulfillGEPath to carry out the path behavior
  }

  @Override
  public void fulfillUpperDivisionPath() {
    System.out.println("Complete the Upper Division learning path based on the Upper division path behavior");
    // Do the same as FulfillGEPath to carry out the path behavior
  }

  @Override
  public void fulfillMathPath() {
    System.out.println("Complete the Math learning path based on the Math path behavior");
    // Do the same as FulfillGEPath to carry out the path behavior
  }

  @Override
  public void fulfillMiscPath() {
    System.out.println("Complete the Misc learning path based on the Misc path behavior");
    // Do the same as FulfillGEPath to carry out the path behavior
  }

  // Implement as a Template method following the template pattern.
  @Override
  public void completeGraduationPath() {
    // Hint: To complete the graduation path, one has to fulfill all learning paths (the mmethods
    // above in a good order.

  }

  /**
   * IRegistrarChangeListener implementation
   */
  @Override
  public void coursePrerequisitesChanged(String courseName, List<String> preRequisites) {
    System.out.println("Course " + courseName + " prerequisites have been changed to: ");
    if (preRequisites != null) {
      preRequisites.forEach(p -> System.out.println(p.toString()));
    }

    // Do something according to course prerequisite change
  }

  @Override
  public void learningPathChanged(LearningPathType path, ILearningPathBehavior learningPathBehavior) {
    switch (path) {
      case gepath:
        System.out.println("GE learning path has changed, check and fulfill GE Path.");
        gePath = learningPathBehavior;
        fulfillGEPath();
        break;
      case lowerdivisionpath:
        System.out.println("Lower Divsion learning path has changed, check and fulfill Lower Division Path.");
        lowerDivisionPath = learningPathBehavior;
        fulfillLowerDivisionPath();
        break;
      case upperdivisionpath:
        System.out.println("Upper Divsion learning path has changed, check and fulfill Upper Division Path.");
        upperDivisionPath = learningPathBehavior;
        fulfillUpperDivisionPath();
        break;
      case mathpath:
        System.out.println("Math learning path has changed, check and fulfill Math Path.");
        mathPath = learningPathBehavior;
        fulfillMathPath();
        break;
      case miscpath:
        System.out.println("Misc learning path has changed, check and fulfill Misc Path.");
        miscPath = learningPathBehavior;
        fulfillMiscPath();
        break;
    }
  }


  public void getGraduationLearningPaths() {
    IRegistrar r = new Registrar();
    r.setGraduationPath(this);
  }
}
