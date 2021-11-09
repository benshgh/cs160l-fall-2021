package edu.sdsu.cs160l.lab9.course.factory;

import edu.sdsu.cs160l.lab9.course.CourseType;
import edu.sdsu.cs160l.lab9.course.ICourse;
import edu.sdsu.cs160l.lab9.course.availablecourse.CS210;
import edu.sdsu.cs160l.lab9.course.availablecourse.CS310;
import edu.sdsu.cs160l.lab9.course.availablecourse.CS496;
import edu.sdsu.cs160l.lab9.student.IStudentStatus;
import edu.sdsu.cs160l.lab9.student.StudentMajor;

import java.util.Arrays;
import java.util.List;

/**
 * A factory that creates Course objects based on CourseType.
 * Created Objects are of IDataStructure
 */
public class CSCourseFactory {
  public static final int MAX_CLASS_SIZE = 20;

  private static CSCourseFactory csCourseFactoryInstance;

  // Courses
  public static final List<String> COURSE_NAMES =
     Arrays.asList("CS210", "CS250", "CS460", "CS530",
        "CS480", "CS514", "CS240", "CS370",
        "CS496", "CS320", "CS150", "CS160",
        "CS549", "CS596", "CS582", "CS649");

  // Empty constructor, setting it to private to avoid instance
  // getting created from constructor
  private CSCourseFactory() {

  }

  public static CSCourseFactory getInstance() {
    if (csCourseFactoryInstance == null) {
      csCourseFactoryInstance = new CSCourseFactory();
    }

    return csCourseFactoryInstance;
  }

  public static boolean isCourseValid(String course) {
    return CSCourseFactory.COURSE_NAMES.stream()
       .anyMatch(x -> x.equals(course));
  }


  public ICourse getDataStructureCourse(CourseType type) {
    switch (type) {
      case cs310:
        return new CS310();
      case cs210:
        return new CS210();
      case cs496:
        return new CS496();
      default:
        throw new UnsupportedOperationException();
    }
  }

  public ICourse getDataStructureCourse(IStudentStatus status) {
    // Non Computer Science major students will only be offered
    // with CS496 data structure class
    if (status.getStudentMajor() != StudentMajor.computersci) {
      return new CS496();
    }

    // For Computer Science major students, based on their current student level,
    // return the data structure course that were offered to them
    switch (status.getStudentLevel()) {
      case junior:
        return new CS310();
      case freshman:
      case sophomore:
        return new CS210();
      default:
        throw new UnsupportedOperationException();
    }
  }
}
