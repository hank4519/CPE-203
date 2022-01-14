package part1;

import java.util.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {
   private final static double DELTA = 0.0001;

   ////////////////////////////////////////////////////////////
   //                      SimpleIf Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testAnalyzeApplicant1()    {
      assertTrue(SimpleIf.analyzeApplicant(89, 85));
   }

   @Test
   public void testAnalyzeApplicant2()    {
      assertFalse(SimpleIf.analyzeApplicant(15, 90));
   }

   @Test
   public void testAnalyzeApplicant3()    {
      //fail("Missing testAnalyzeApplicant3");
      assertFalse(SimpleIf.analyzeApplicant(60, 60));
      /* TO DO: Write one more valid test case. */
   }

   @Test
   public void testMaxAverage1() {
      assertEquals(SimpleIf.maxAverage(89.5, 91.2), 91.2, DELTA);
   }

   @Test
   public void testMaxAverage2() {
      assertEquals(SimpleIf.maxAverage(60, 89), 89, DELTA);
   }

   @Test
   public void testMaxAverage3() {
      //fail("Missing testMaxAverage3");
      assertEquals(SimpleIf.maxAverage(60, 60), 60, DELTA);
      /* TO DO: Write one more valid test case. */
   }

   ////////////////////////////////////////////////////////////
   //                    SimpleLoop Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleLoop1()    {
      assertEquals(7, SimpleLoop.sum(3, 4));
   }

   @Test
   public void testSimpleLoop2()    {
      assertEquals(7, SimpleLoop.sum(-2, 4));
   }

   @Test
   public void testSimpleLoop3()    {
      //fail("Missing SimpleLoop3");
      /* TO DO: Write one more valid test case to make sure that
         this function is not just returning 7. */
      assertEquals(18, SimpleLoop.sum(3, 6));
   }

   ////////////////////////////////////////////////////////////
   //                    SimpleArray Tests                   //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleArray1()    {
      /* What is that parameter?  They are newly allocated arrays
         with initial values. */
      assertArrayEquals(
         new boolean[] {false, false, true, true, false, false},
         SimpleArray.applicantAcceptable(new int[] {80, 85, 89, 92, 76, 81}, 85)
      );
   }

   @Test
   public void testSimpleArray2()    {
      assertArrayEquals(
         new boolean[] {false, false},
         SimpleArray.applicantAcceptable(new int[] {80, 83}, 92));
   }

   @Test
   public void testSimpleArray3()   {
      assertArrayEquals( new boolean[]{false, true, true, true,}, SimpleArray.applicantAcceptable(new int []{0,10,20,100},5));

      //fail("Missing SimpleArray3");
      /* TO DO: Add a new test case. */
   }

   ////////////////////////////////////////////////////////////
   //                    SimpleList Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testSimpleList1()   {
      List<Integer> input =
         new LinkedList<Integer>(Arrays.asList(new Integer[] {80, 85, 89, 92, 76, 81}));
      List<Boolean> expected =
         new ArrayList<Boolean>(Arrays.asList(new Boolean[] {false, false, true, true, false, false}));

      assertEquals(expected, SimpleList.applicantAcceptable(input, 85));
   }

   @Test
   public void testSimpleList2()   {
      List <Integer> input = new LinkedList <Integer> (Arrays.asList(new Integer [] {13, 14, 28, 87, 93, 134}));
      List <Boolean> expected = new ArrayList <Boolean> (Arrays.asList (new Boolean [] {false, false, false, true, true, true}));
      assertEquals(expected, SimpleList.applicantAcceptable(input, 50));
      //fail("Missing SimpleList2");
      /* TO DO: Add a new test case. */
   }

   ////////////////////////////////////////////////////////////
   //                    BetterLoop Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testFourOver85_1()   {
      assertFalse(BetterLoop.atLeastFourOver85(new int[] {89, 93, 100, 39, 84, 63}));
   }

   @Test
   public void testFourOver85_2()   {
      assertTrue(BetterLoop.atLeastFourOver85(new int[] {86, 87, 90, 82, 83, 90}));
   }

   @Test
   public void testFourOver85_3()   {
      assertTrue (BetterLoop.atLeastFourOver85((new int [] {80, 89, 96, 91, 34, 100})));
      //fail("Missing BetterLoop3");
      /* TO DO: Write a valid test case where the expected result is false. */
   }

   ////////////////////////////////////////////////////////////
   //                    ExampleMap Tests                    //
   ////////////////////////////////////////////////////////////

   @Test
   public void testExampleMap1()   {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Mary",
         Arrays.asList(
            new CourseGrade("CPE 123", 89),
            new CourseGrade("CPE 101", 90),
            new CourseGrade("CPE 202", 99),
            new CourseGrade("CPE 203", 100),
            new CourseGrade("CPE 225", 89)));
      courseListsByStudent.put("Sam",
         Arrays.asList(
            new CourseGrade("CPE 101", 86),
            new CourseGrade("CPE 202", 80),
            new CourseGrade("CPE 203", 76),
            new CourseGrade("CPE 225", 80)));
      courseListsByStudent.put("Sara",
         Arrays.asList(
            new CourseGrade("CPE 123", 99),
            new CourseGrade("CPE 203", 91),
            new CourseGrade("CPE 471", 86),
            new CourseGrade("CPE 473", 90),
            new CourseGrade("CPE 476", 99),
            new CourseGrade("CPE 572", 100)));

      List<String> expected = Arrays.asList("Mary", "Sara");

      /*
       * Why compare HashSets here?  Just so that the order of the
       * elements in the list is not important for this test.
       */
      assertEquals(new HashSet<>(expected),
         new HashSet<>(ExampleMap.highScoringStudents(
            courseListsByStudent, 85)));
   }

   @Test
   public void testExampleMap2()    {
      Map<String, List<CourseGrade>> courseListsByStudent = new HashMap<>();
      courseListsByStudent.put("Hank",
              Arrays.asList(
                      new CourseGrade("CPE 419", 94),
                      new CourseGrade("CPE 991", 91),
                      new CourseGrade("CPE 459", 99),
                      new CourseGrade("CPE 850", 100),
                      new CourseGrade("CPE 951", 93)));
      courseListsByStudent.put("Marcus",
              Arrays.asList(
                      new CourseGrade("CPE 345", 97),
                      new CourseGrade("CPE 103", 89),
                      new CourseGrade("CPE 243", 100),
                      new CourseGrade("CPE 212", 15)));
      courseListsByStudent.put("Sam",
              Arrays.asList(
                      new CourseGrade("CPE 123", 99),
                      new CourseGrade("CPE 203", 14),
                      new CourseGrade("CPE 471", 86),
                      new CourseGrade("CPE 473", 13),
                      new CourseGrade("CPE 476", 98),
                      new CourseGrade("CPE 572", 17)));

      List<String> expected = Arrays.asList("Hank");
      assertEquals(new HashSet<>(expected), new HashSet<>(ExampleMap.highScoringStudents(courseListsByStudent,90 )));

      //fail("Missing ExampleMap2");
      /* TO DO: Write another valid test case. */
   }
}
