import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class Lab9Test {

	static final int NUMOFLEVELS = 4;
	static final int NUMOFMAJORS = 5;
	
	static final int NUMOFSTUDENTS = 100; 

	static final int NUMOFLEARNINGPATHS = 5;
	static final int MAXUNITSFORALEARNINGPATH = 20;
	
	static Lab9.Registrar regis = null;
	
	@BeforeClass
    public static void beforeClass() {
        // This method will be executed once on initialization time
		
		// SET UP TEST DATA
			
		Lab9 lab9 = new Lab9();
		// Instantiate registrar
		regis = lab9.new Registrar();
		
		// Create the students data for testing
		Lab9.Student students[][] = new Lab9.Student[2 * NUMOFLEVELS][ 2 * NUMOFMAJORS]; 
		
		int sIndex = 0;
		
		for (int i = 0; i < 2; i++) {
			int lIndex = 0;
			for (Lab9.StudentLevel level : Lab9.StudentLevel.values()) { 

				int mIndex = 0;
				
				for (int j = 0; j < 2; j++) {
					for (Lab9.StudentMajor major : Lab9.StudentMajor.values()) {
						
						students[i * NUMOFLEVELS + lIndex][mIndex] = 
								lab9.new Student(sIndex, level, major);
   					    
						sIndex ++;
					
						mIndex ++;
					}
				}
				
				lIndex ++;
			}
		}
		
		// enroll students to courses with randomized level and major 
		try {
			
			// Courses
			/*public static final List<String> CourseNames = 
				Arrays.asList("CS210", "CS250", "CS460", "CS530", 
				  "CS480", "CS514", "CS240", "CS370", 
				  "CS496", "CS320", "CS150", "CS160", 
				  "CS549", "CS596", "CS582", "CS649");*/
			
			Random rd = new Random();
			
			// enroll students for testing
			for (String course : Lab9.CSCourseFactory.CourseNames) {
				for (int i = 0; i < Lab9.CSCourseFactory.MAXCLASSSIZE; i ++) {
					int lIndex = rd.nextInt(2 * NUMOFLEVELS);
					int mIndex = rd.nextInt(2 * NUMOFMAJORS);
					
					try {
						regis.enrollStudentCourse(students[lIndex][mIndex], course);
					} catch (Lab9.ClassFullException e) {
						System.out.println("Encountered ClassFullException, " + e.getMessage());
					} catch (Lab9.StudentAlreadyEnrolledException e) {
						//System.out.println("Encountered StudentAlreadyEnrolledException, " + e.getMessage());
					} 
				}
			}
		
			// enter student grades for testing
			int si = 0;
			for (String course : Lab9.CSCourseFactory.CourseNames) {
				si = 0;
				while (si < NUMOFSTUDENTS) {
					int lIndex = rd.nextInt(2 * NUMOFLEVELS);
					int mIndex = rd.nextInt(2 * NUMOFMAJORS);
					
					double grade = 50 + rd.nextDouble() * 50;
					regis.insertStudentGrade(course, students[lIndex][mIndex], grade);
					si ++;
				}
			}
			
			// enter student course units for testing
			si = 0;
			while (si < NUMOFSTUDENTS) {
				int lIndex = rd.nextInt(2 * NUMOFLEVELS);
				int mIndex = rd.nextInt(2 * NUMOFMAJORS);
				
				for (int lp = 0; lp < NUMOFLEARNINGPATHS; lp ++) {
				
					int units = rd.nextInt(MAXUNITSFORALEARNINGPATH);
					
					int lpIndex = rd.nextInt(NUMOFLEARNINGPATHS);
					
					regis.insertStudentUnitsTakenForALearningPath(
							students[lIndex][mIndex], 
							Lab9.LearningPathType.values()[lpIndex],
							units);
				}
				
				si ++;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Encountered FileNotFoundException, " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Encountered IOException, " + e.getMessage());
		} finally {
			System.out.println("Final clean up after all exception catching is done.");
		}
    }
    
	@Before
	public void before() {
		// This method will be executed once before each test execution
		
    }

    @AfterClass
    public static void afterClass() {
        // This method will be executed once when all tests are executed
    }

    @After
    public void after() {
        // This method will be executed once after each test execution
    }
   
	@Test
	public void testPrintoutStudentEnrollmentStats() {
		
		/** TODO: 
		 *      1) Use stream foreach to print out number of students of each student level 
		 *         that is enrolled in each course (Lab9.CSCourseFactory.CourseNames) with the following 
		 *         sample output, you need to print all combinations of student level and course:
		 *         
		 *         Number of Students with freshman status enrolled in CS210 is: 3
		 *         Number of Students with sophomore status enrolled in CS210 is: 2
		 *         .....
		 *         Number of Students with junior status enrolled in CS530 is: 6
		 *         .....
		 *      
		 *      2) Use stream foreach to print out number of students of each student major 
		 *         that is enrolled in each course (Lab9.CSCourseFactory.CourseNames) with the following 
		 *         sample output, you need to print all combinations of student major and course:
		 *         
		 *         Number of Students with computersci major enrolled in CS210 is: 3
		 *         Number of Students with computereng major enrolled in CS210 is: 2
		 *         .....
		 *         Number of Students with math major enrolled in CS530 is: 6
		 *         .....
		 *         
		 *      3) Use stream foreach to print out number of students of each combination 
		 *         of student level and major 
		 *         that is enrolled in each course (Lab9.CSCourseFactory.CourseNames) with the following 
		 *         sample output, you need to print all combinations of student level, major and course:
		 *         
		 *         Number of Students with freshman status and computersci major enrolled in CS210 is: 3
		 *         Number of Students with freshman status and computereng major enrolled in CS210 is: 2
		 *         .....
		 *         Number of Students with junior status and computersci major enrolled in CS530 is: 6
		 *         .....
		 */
		
		// Implementation for 1)
		for (Lab9.StudentLevel level : Lab9.StudentLevel.values()) {
			Lab9.CSCourseFactory.CourseNames.stream().forEach(
					s -> System.out.println(
							"Number of Students with " + level + " status enrolled in " +
					        s + " is: " + regis.getNumOfStudentsEnrolled(level, s))); 
		}
		
		// Write your implementation for 2) per above instructions
		
		
		// Write your implementation for 3) per above instructions
		
	}
	
	/** TODO:
	 * Write a test method for each one of the following Registrar methods 
	 * against the regis instance of Registrar that was created and populated
	 * in the beforeClass method. Use debugging to step through the method
	 * execution, inspect the results and fix any bug in the logic.
	 * 
	 * getStudentsEnrolled(StudentMajor major, String course), implemented as an example
	 * getStudentsGrades(StudentMajor major, String course)
	 * getAvgGrade(StudentLevel level, String course)
	 * getTopStudents(int numOfTopStu, StudentMajor major, String course)
	 * getAvgGradeCourseLevel(StudentLevel level, String courseStarting)
	 * getStudentUnits(StudentMajor major, StudentLevel level, LearningPathType lPathType)
	 * getStudentTotalUnits(int stuID)
	 * getAvgCourseUnits(StudentMajor major, LearningPathType lPathType)
	 * getMaxCourseUnits(StudentLevel level, LearningPathType lPathType)
	 * getAvgTotalUnits()
	 * getAvgUnitsNonComputing(LearningPathType lPathType)
	 * 
	 * Name the test method as test{RegistrarMethodName}{Parameter1}{Parameter2}...
	 * e.g., using the first listed method in the list,
	 * test method should be named as:
	 *   testGetStudentsEnrolledMajorCourse
	 *  
	 * If a method returns a vector, 
	 * 	 print out the method name, inputs and return value in the following format:
	 * 	 using the getStudentsEnrolled method as an example: 
	 * 
	 * 	 getStudentsEnrolled, par1: computersci, par2: CS210
	 * 	 return: 
	 *   16
	 *   10
	 *   3
	 *   5
	 *  
	 * If a method returns a scalar value, 
	 * 	 
	 *   print out the method name, inputs and return value in the following format:
	 * 	 using getAvgGrade(major, course) method as an example: 
	 * 
	 * 	 getAvgGrade, par1: junior, par2: CS530
	 * 	 return: 78.64398
	 * 
	 * */
	
	@Test
	public void testGetStudentsEnrolledMajorCourse() {
		
		List<Integer> stus = regis.getStudentsEnrolled(Lab9.StudentMajor.math, "CS2");
		
		System.out.println("getStudentsEnrolled, par1: math, par2: CS2");
		stus.stream().forEach(s -> System.out.println(s.intValue()));
	}
	
	@Test
	public void testGetAvgGradeLevelCourse() {
		
		double avgGrade = regis.getAvgGrade(Lab9.StudentLevel.junior, "CS530");
		
		System.out.println("getAvgGrade, par1: junior, par2: CS530");
		System.out.println("return: " + avgGrade);
	}
	
	
	// TODO: write test methods according to what was specified above  
		
}