package edu.sdsu.cs160l.lab9.college;

import edu.sdsu.cs160l.lab9.course.college.LearningPathType;
import edu.sdsu.cs160l.lab9.course.factory.CSCourseFactory;
import edu.sdsu.cs160l.lab9.exception.ClassFullException;
import edu.sdsu.cs160l.lab9.exception.IllegalCourseNameException;
import edu.sdsu.cs160l.lab9.exception.StudentAlreadyEnrolledException;
import edu.sdsu.cs160l.lab9.student.*;
import edu.sdsu.cs160l.lab9.student.path.GEPathBehavior;
import edu.sdsu.cs160l.lab9.student.path.ILearningPathBehavior;
import edu.sdsu.cs160l.lab9.student.path.LowerDivisionPathBehavior;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Registrar implements IRegistrar {

  /**
   * Implement an Observer pattern to have students subscribe to notifications
   * from the registrar on changes to course prerequisites or learning paths.
   */
  public static final String COURSE_CHANGE = "coursechange";
  public static final String LEARNING_PATH_CHANGE = "learningpathchange";

  // Map, change type with the subscribed listeners to the change type
  private final Map<String, List<IRegistrarChangeListener>> studentListeners = new HashMap<>();

  // Course student enrollments
  // Map: Key - course name, Value: List of students
  // enrolled in the course
  private final Map<String, List<IStudentStatus>> courseStudents = new HashMap<>();

  // Course student grades
  // Map: course name, Map (student id, grade)
  //   Key - course name,
  //   Value - Hash Map: Key - student ID, Value - student grade
  private final Map<String, Map<Integer, StudentGrade>> courseGrades = new HashMap<>();

  // Course student grades
  // Map: course name, list of StudentGrade
  //   Key - course name,
  //   Value - List of StudentGrade objects
  // For future use
  //Map<String, List<StudentGrade>> course_grades = new HashMap<>();

  // Student course units taken for learning paths
  // Map: student ID, Map (learning path, units taken)
  //   Key - student ID,
  //   Value - Hash Map: Key - LearningPathType, Value - course units
  private final Map<Integer, Map<LearningPathType, StudentUnits>> studentUnitsTaken = new HashMap<>();

  public Registrar() {
    this.studentListeners.put(COURSE_CHANGE, new ArrayList<>());
    this.studentListeners.put(LEARNING_PATH_CHANGE, new ArrayList<>());
  }

  public void enrollStudentCourse(IStudentStatus stu, String course) throws
     ClassFullException,
     StudentAlreadyEnrolledException,
     IOException {

    // Throw NullPointerException if stu is null
    if (stu == null) {
      throw new NullPointerException("The student reference passed in is NULL, cannot continue.");
    }

    // Throw IllegalArgumentException if course argument is not valid
    if (!CSCourseFactory.isCourseValid(course)) {
      throw new IllegalCourseNameException("The student argument passed in is NULL, cannot continue.");
    }

    if (!courseStudents.containsKey(course)) {
      // if the student list of the course has not been established.
      this.courseStudents.put(course, new ArrayList<>());
    }

    // Throw a checked ClassFullException if the class size is already
    // at the MaxClassSize.
    List<IStudentStatus> stuLst = this.courseStudents.get(course);
    if (stuLst.size() == CSCourseFactory.MAX_CLASS_SIZE) {
      throw new ClassFullException(course + " Class size is full, " +
         "failed adding " + stu.getStudentID() +
         " to the class.");
    }

    // Throw a checked StudentAlreadyEnrolledException if the student
    // is already enrolled in the class

    // TODO: use the Stream way (the same as above CSCourseFactory.IsCourseValid)
    //       to check if the student is already enrolled in the class;
    //       if so, throw a StudentAlreadyEnrolledException


    // Enroll a student to a course -
    // add the student id to the course student list in the course_students map.
    stuLst.add(stu);

  }

  // insert a course grade for a student
  public void insertStudentGrade(String course, IStudentStatus stu, double grade) {

    if (stu == null)
      return;

    // Throw IllegalArgumentException if course argument is not valid
    if (!CSCourseFactory.isCourseValid(course)) {
      throw new IllegalCourseNameException("The student argument passed in is NULL, cannot continue.");
    }

    if (!courseGrades.containsKey(course)) {
      // if the grade list of the course has not been established.
      this.courseGrades.put(course, new HashMap<>());
    }
    Map<Integer, StudentGrade> gradeMap = courseGrades.get(course);
    gradeMap.put(stu.getStudentID(), new StudentGrade(stu, grade));

  }

  // insert units taken for a learning path for a student
  public void insertStudentUnitsTakenForALearningPath(IStudentStatus stu, LearningPathType path, int units) {

    if (stu == null)
      return;

    if (!studentUnitsTaken.containsKey(stu.getStudentID())) {
      // if the grade list of the course has not been established.
      this.studentUnitsTaken.put(stu.getStudentID(), new HashMap<>());
    }

    Map<LearningPathType, StudentUnits> unitsTaken = studentUnitsTaken.get(stu.getStudentID());
    unitsTaken.put(path, new StudentUnits(stu, units));
  }

  /*
    use stream to conduct functional programming exercises for
    map, reduce, filter
    */

  /**
   * Returns number of students enrolled in a course where the students are
   * of a particular major
   * <p>
   * <p>
   * Assumption:
   *
   * @param major  student major
   * @param course course name
   * @return number of students enrolled in a course where the students are
   * of the major passed-in
   */
  public long getNumOfStudentsEnrolled(StudentMajor major, String course) {

    List<IStudentStatus> stuLst = this.courseStudents.get(course);

    if (stuLst == null)
      return 0L;

    return stuLst
       .stream()
       .filter(s -> s.getStudentMajor() == major)
       .count();
  }


  /**
   * Returns number of students enrolled in a course where the students are of a
   * particular student level
   * <p>
   * <p>
   * Assumption:
   *
   * @param sl     student level
   * @param course course name
   * @return number of students enrolled in a course where the students are
   * of the student level passed-in
   */
  public int getNumOfStudentsEnrolled(StudentLevel sl, String course) {

    // TODO: use the similar stream operations as in the above getNumOfStudentsEnrolled
    //       method to implement the following method
    return 0;

  }


  /**
   * Returns number of students enrolled in a course where the students are
   * of a particular major and a particular student level
   * <p>
   * <p>
   * Assumption:
   *
   * @param major  student major
   * @param sl     student level
   * @param course course name
   * @return number of students enrolled in a course where the students are
   * of the student major and the student level passed-in
   */
  public int getNumOfStudentsEnrolled(StudentLevel sl, StudentMajor major, String course) {

    // TODO: use the similar stream operations as in the above getNumOfStudentsEnrolled
    // method to implement the following method

    return 0;
  }

  /**
   * Returns a list of IDs of students enrolled in a group of courses of a certain level
   * where the students are of a particular major
   * <p>
   * <p>
   * Assumption:
   *
   * @param major          student major
   * @param courseStarting courses that start with, e.g., "CS2" meaning all courses starting with "CS2"
   * @return List of IDs of students enrolled in the courses starting with courseStarting,
   * e.g., CS5XX (CS500 level courses: CS530, CS514, etc.). Note in the case of
   * a student enrolled into multiple CS5XX course, its student ID should be added
   * to the return list ONCE.
   */
  public List<Integer> getStudentsEnrolled(StudentMajor major, String courseStarting) {

    /*
      Note if students have trouble in understanding ONE sequence of streamlined operations,
      they could also use the split implementation followed (which I commented out here).
      */
    return this.courseStudents
       .entrySet() //Map.Entry<String, List<IStudentStatus>>
       .stream()//Stream<Map.Entry<String, List<IStudentStatus>>>
       .filter(c -> c.getKey().startsWith(courseStarting))//Stream<Map.Entry<String, List<IStudentStatus>>>
       .map(Map.Entry::getValue)//Stream<List<IStudentStatus>>
       .flatMap(s -> s.stream()) //Stream<IStudentStatus>
       .filter(s -> s.getStudentMajor() == major) //Stream<IStudentStatus>
       .map(ss -> ss.getStudentID()) //Stream<Integer>
       .distinct() //Stream<Integer>
       .collect(Collectors.toList()); // List<Integer>


    /*List<List<IStudentStatus>> lsts = this.course_students.entrySet().stream()
        .filter(c -> c.getKey().startsWith(courseStarting))
        .map(Map.Entry::getValue)
        .collect(Collectors.toList());

    // flatmap flattens all List<IStudentStatus> in lsts to
          // individual IStudentStatus objects.

    // flattening means following transformation:
    // using an integer array for illustration
    // before flattening - [ [1, 3], [5, 17], [22, 65] ]
    // after flattening - [ 1, 3, 5, 17, 22, 65 ]
    List<IStudentStatus> concatLst = lsts.stream()
                                     .flatMap(l -> l.stream())
                                     .collect(Collectors.toList());

    //Note distinct() is used here for only counting a student once
    return concatLst.stream()
                .filter(s -> s.getStudentMajor() == major)
                .map(ss -> ss.getStudentID())
                .distinct()
                .collect(Collectors.toList()); */
  }

  // TODO: following the instructions below

  /**
   * Returns the grades of students of a course
   * where the students are of a particular major
   * <p>
   * <p>
   * Assumption:
   *
   * @param major  student major
   * @param course course name
   * @return grades of the students enrolled in a course where the students
   * are of a particular major
   */
  public List<Double> getStudentsGrades(StudentMajor major, String course) {

    //Write your implementation

    return null;

  }

  /**
   * Returns the average grade of students of a course
   * where the students are of a particular student level
   * <p>
   * <p>
   * Assumption:
   *
   * @param level  student level
   * @param course course name
   * @return average grade of the students in a course where the students
   * are of a particular student level
   */
  public double getAvgGrade(StudentLevel level, String course) {

    // get the map of Student Grades given the course.
    Map<Integer, StudentGrade> stuGrades = this.courseGrades.get(course);

    if (stuGrades == null)
      return 0; // later consider to return an empty list

    // use filter and mapToDouble to get the list of Students(IStudentStatus) of
    // the level passed-in, then map and calculate the average grades
    OptionalDouble avgGrade = stuGrades.entrySet().stream()
       .filter(s -> s.getValue().getStudentStatus().getStudentLevel() == level)
       .mapToDouble(s -> s.getValue().getGrade())
       .average();

    if (avgGrade.isPresent())
      return avgGrade.getAsDouble();

    return 0;

  }

  // TODO: following the instructions below

  /**
   * Returns the top N students (in gpa) of a course
   * where the students are of a particular major
   * <p>
   * <p>
   * Assumption:
   *
   * @param numOfTopStu number of top students
   * @param major       student major
   * @param course      course name
   * @return grades of the students enrolled in a course where the students
   * are of a particular major
   */
  public List<StudentGrade> getTopStudents(int numOfTopStu, StudentMajor major, String course) {

    //Write your implementation

    return null;

  }

  // TODO: following the instructions below

  /**
   * Returns the average grade of students enrolled in a group of courses
   * of a certain course level (CS4XX, CS5XX, or ...)
   * where the students are of a particular major
   * <p>
   * <p>
   * Assumption:
   *
   * @param major          student major
   * @param courseStarting course Starting, e.g., "CS2" meaning all courses starting with "CS2"
   * @return average grade of the students in a course level where the students
   * are of a particular major
   */
  public double getAvgGradeCourseLevel(StudentMajor major, String courseStarting) {

    // Use filter and map to
    // get the list of the Hashmaps of the students' grades in the courses of the course level

    // Use filter to get the grades of students of the passed-in major

    // Hint: flatMap needs to used

    //Write your implementation

    return 0.0;
  }

  //
  // TODO: following the instructions below

  /**
   * Returns List of StudentUnits objects of a learning path for
   * students of a particular major and a particular level
   * <p>
   * <p>
   * Assumption:
   *
   * @param major     student major
   * @param level     student level
   * @param lPathType student learning path
   * @return A list of StudentUnits for students of a particular major
   * and/or a particular level
   */
  public List<StudentUnits> getStudentUnits(StudentMajor major,
                                            StudentLevel level,
                                            LearningPathType lPathType) {

    // use filter and map to get the list of Students(StudentUnits) where
    // students are of the major and the level passed-in

    // use filter to sort out the StudentUnits for the passed-in learning path

    //Write your implementation

    return null;
  }

  /**
   * Returns Total student course units a student has taken, use reduce
   * <p>
   * <p>
   * Assumption:
   *
   * @param stuID student ID
   * @return Total student course units a student has taken
   */
  public int getStudentTotalUnits(int stuID) {

    Map<LearningPathType, StudentUnits> su = studentUnitsTaken.get(stuID);

    if (su == null)
      return 0;

    return su.entrySet()
       .stream()
       .map(Map.Entry::getValue)
       .map(s -> s.getUnits())
       .reduce(0, (subtotal, nUnits) -> subtotal + nUnits);
  }

  /**
   * Returns Average number of course units students of a major have taken
   * for a particular learning path
   * <p>
   * <p>
   * Assumption:
   *
   * @param major     student major
   * @param lPathType student learning path
   * @return Average number of course units students of a major have taken
   * for the given learning path
   */
  public double getAvgCourseUnits(StudentMajor major,
                                  LearningPathType lPathType) {

    // use filter and map to get the list of StudentUnits where
    // students are of the passed-in major and the learning Path Type is
    // of the passed-in lPathType
    OptionalDouble avgUnits = this.studentUnitsTaken.entrySet()
       .stream()
       .filter(su -> su.getValue().containsKey(lPathType))
       .map(Map.Entry::getValue) // get Map<LearningPathType, StudentUnits>
       .flatMap(su -> su.values().stream()) // flattern each map --> List<StudentUnits>
       .filter(su -> (su.getStudentStatus().getStudentMajor() == major))
       .mapToDouble(su -> su.getUnits())
       .average();

    return avgUnits.isPresent() ? avgUnits.getAsDouble() : 0.0;

  }

  // TODO: Max number of units students of a level have taken for a particular
  //       learning path

  /**
   * Returns Max number of course units students of a level have taken
   * for a particular learning path
   * <p>
   * <p>
   * Assumption:
   *
   * @param level     student level
   * @param lPathType student learning path
   * @return Max number of course units students of a level have taken
   * for the given learning path
   */
  public int getMaxCourseUnits(StudentLevel level,
                               LearningPathType lPathType) {

    //Write your implementation

    return 0;

  }

  // TODO: Average number of total units students have taken

  /**
   * Returns Average number of total course units students have taken
   * <p>
   * <p>
   * Assumption:
   *
   * @return Average number of total units students have taken
   */
  public double getAvgTotalUnits() {

    //Write your implementation

    return 0.0;

  }

  // TODO: Student units for students from non-computing majors for a learning path

  /**
   * Returns Average number of course units students of non-computing majors have taken
   * for a learning path
   * <p>
   * <p>
   * Assumption:
   *
   * @param lPathType student learning path
   * @return Average number of course units students of non-computing majors have taken
   * for a learning path
   */
  public double getAvgUnitsNonComputing(LearningPathType lPathType) {

    //Write your implementation

    return 0.0;
  }

  /**
   * Map, Filter, Reduce. Ending
   */

  public void subscribe(String changeType, IRegistrarChangeListener listener) {
    List<IRegistrarChangeListener> students = studentListeners.get(changeType);
    students.add(listener);
  }

  public void unsubscribe(String changeType, IRegistrarChangeListener listener) {

  }

  // Notify students of course prerequisite changes
  public void notifyCourseChange(String courseName, List<String> prereqs) {
    List<IRegistrarChangeListener> students = studentListeners.get(COURSE_CHANGE);

    for (IRegistrarChangeListener listener : students) {
      listener.coursePrerequisitesChanged(courseName, prereqs);
    }
  }

  /**
   * Implement the function to notify all subscribed students about a learning path change
   */
  public void notifyLearningPathChange(LearningPathType pathType, ILearningPathBehavior lpb) {
    List<IRegistrarChangeListener> students = studentListeners.get(LEARNING_PATH_CHANGE);

    for (IRegistrarChangeListener s : students) {
      s.learningPathChanged(pathType, lpb);
    }
  }


  // Set graduation path (strategy) based on student major and level
  // Set course path for each required learning category
  @Override
  public void setGraduationPath(IStudentStatus studentStatus) {
    IGraduationPath gp = (IGraduationPath) studentStatus;
    if (gp != null) {
      switch (studentStatus.getStudentMajor()) {
        case computersci:
          // Run-time injection of the graduation path behaviors.
          // Dependency injections
          gp.setGEPathBehavior(new GEPathBehavior("CS GE Behavior"));
          gp.setLowerDivisionPathBehavior(new LowerDivisionPathBehavior("CS Lower Behavior"));
          gp.setUpperDivisionPathBehavior(new LowerDivisionPathBehavior("CS Upper Behavior"));
          gp.setMathPathBehavior(new LowerDivisionPathBehavior("CS Math Behavior"));
          gp.setMiscPathBehavior(new LowerDivisionPathBehavior("CS Miscellaneous Behavior"));
          break;
        case computereng:
          // implement similar Path behaviors for computer engineering students
          break;
        case biology:
          // implement similar Path behaviors for biology students
          break;
        case math:
          // implement similar Path behaviors for math students
          break;
        case politicalsci:
          // implement similar Path behaviors for political science students
          break;
        default:
          throw new UnsupportedOperationException();
      }


    }
  }
}
