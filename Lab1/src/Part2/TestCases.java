package Part2;

import org.junit.jupiter.api.Test;
//import part1.CourseGrade;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestCases {
   /*
    * This test is just to get you started.
    */
   @Test
   public void testGetName()   {
      // This will not compile until you implement the Applicant class
      List<Part2.CourseGrade> grades = Arrays.asList(
              new Part2.CourseGrade("Intro to CS", 100),
              new Part2.CourseGrade("Data Structures", 95),
              new Part2.CourseGrade("Algorithms", 91),
              new Part2.CourseGrade("Computer Organization", 91),
              new Part2.CourseGrade("Operating Systems", 75),
              new Part2.CourseGrade("Non-CS", 83)
      );
      Applicant testApplicant = new Applicant("Aakash", grades);
      assertEquals("Aakash", testApplicant.getName());
   }
   @Test
   public void testGetGrade(){
      List<Part2.CourseGrade> grades = Arrays.asList(
              new Part2.CourseGrade("Linear Algebra", 134),
              new Part2.CourseGrade("Calculus", 459)
      );
      Applicant testApplicant = new Applicant("Robert", grades);
      assertEquals(grades, testApplicant.getGrades());
   }
   //Test for AnalyzeApplicant2
   @Test
   public void test_filter(){
      List<Part2.CourseGrade> grades2 = Arrays.asList(
              new Part2.CourseGrade("CS", 100),
              new Part2.CourseGrade("Physics", 95),
              new Part2.CourseGrade("Sociology", 91),
              new Part2.CourseGrade("Circuit", 91),
              new Part2.CourseGrade("Math", 75),
              new Part2.CourseGrade("Science", 83),
              new Part2.CourseGrade("English", 67)
      );
      //num_of_class: 7
      int num_of_activities = 4;
      boolean has_good_resume = false;
      Applicant testApplicant2 = new Applicant("Hank", grades2, num_of_activities, has_good_resume);
      assertEquals(true, testApplicant2.filter());
   }
   @Test
   public void TestGetGradeFor (){
      List<Part2.CourseGrade> grades = Arrays.asList(
              new Part2.CourseGrade("ABC class", 78),
              new Part2.CourseGrade("1+2 class", 82)
      );
      Applicant testApplicant = new Applicant("Muir", grades);
      CourseGrade abc_class = testApplicant.getGradeFor("ABC class");
      assertEquals(78, abc_class.getScore());
   }
   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */
   @Test
   public void testImplSpecifics()
      throws NoSuchMethodException   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getName",
         "getGrades",
         "getGradeFor",
         "getActivities",
         "hasGoodResume",
         "filter"
      );
      final List<Class> expectedMethodReturns = Arrays.asList(
         String.class,
         List.class,
         CourseGrade.class,
         Object.class,
         Object.class,
         Object.class
      );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0],
         new Class[0],
         new Class[] { String.class },
         new Class[0],
         new Class[0],
         new Class[0]
         );

      verifyImplSpecifics(Applicant.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException    {
      assertEquals(0, Applicant.class.getFields().length,
              "Unexpected number of public fields");

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertTrue(expectedMethodNames.size()+1 >= publicMethods.size(),
              "Unexpected number of public methods");

      assertTrue(expectedMethodNames.size() == expectedMethodReturns.size(),
              "Invalid test configuration");
      assertTrue(expectedMethodNames.size() == expectedMethodParameters.size(),
              "Invalid test configuration");

      for (int i = 0; i < expectedMethodNames.size(); i++)       {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertTrue(expectedMethodReturns.get(i) == method.getReturnType()||
                 method.getReturnType().isPrimitive());
      }
   }
   @Test
   public void testGetActivities(){
      List<Part2.CourseGrade> grades = Arrays.asList(
              new Part2.CourseGrade("Aero", 194),
              new Part2.CourseGrade("Quantum mechanics", 82)
      );
      int num_of_activities = 2;
      Applicant testApplicant = new Applicant("Jake", grades, num_of_activities, false);
      assertEquals(2, testApplicant.getActivities());
   }
}
