package equality;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalTime;

import org.junit.Test;

public class TestCases
{
   @Test
   public void testExercise1()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }
   @Test
   public void studentTest1(){
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      ArrayList<CourseSection> list = new ArrayList<>();
      list.add(one); list.add(two);
      final Student student_one = new Student ("Jack", "Roy", 18, list);
      final Student student_identical = new Student ("Jack", "Roy", 18, list);
      assertTrue(student_one.equals(student_identical));
      assertTrue(student_identical.equals(student_one));
   }
   @Test
   public void studentTest2(){
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      ArrayList<CourseSection> list1 = new ArrayList<>();
      list1.add(one);

      ArrayList<CourseSection> list2 = new ArrayList<>(){final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(1, 10), LocalTime.of(2, 0));};

      final Student student_one = new Student ("Taylor", "Zoey", 18, list1);
      final Student student_two = new Student ("Jack", "Roy", 18, list1);
      final Student student_three = new Student("Taylor", "Zoey", 18, list2);
      final Student student_four = new Student("Taylor", "Zoey", 26, list1);
      assertFalse(student_one.equals(student_two));
      assertFalse(student_one.equals(student_three));
      assertFalse(student_one.equals(student_four));
   }
   @Test
   public void studentTest3(){
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      ArrayList<CourseSection> list = new ArrayList<>();
      list.add(one); list.add(two);
      final Student student_one = new Student ("Jack", "Roy", 18, list);
      final Student student_two = new Student ("Jack", "Roy", 18, list);
      assertEquals(student_one.hashCode(), student_two.hashCode());
   }
   @Test
   public void studentTest4(){
      final CourseSection one = new CourseSection("CSC", "203", 35,
              LocalTime.of(9, 10), LocalTime.of(10, 0));
      ArrayList<CourseSection> list1 = new ArrayList<>();
      list1.add(one);

      ArrayList<CourseSection> list2 = new ArrayList<>(){final CourseSection two = new CourseSection("CSC", "203", 35,
              LocalTime.of(1, 10), LocalTime.of(2, 0));};

      final Student student_one = new Student ("Taylor", "Zoey", 18, list1);
      final Student student_two = new Student ("Jack", "Roy", 18, list1);
      final Student student_three = new Student("Taylor", "Zoey", 18, list2);
      final Student student_four = new Student("Taylor", "Zoey", 26, list1);
      assertNotEquals(student_one.hashCode(), student_two.hashCode());
      assertNotEquals(student_one.hashCode(), student_three.hashCode());
      assertNotEquals(student_one.hashCode(), student_four.hashCode());
   }
}
