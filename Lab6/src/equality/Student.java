package equality;

import java.util.List;
import java.util.Objects;
class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }
   public boolean equals(Object o){
      if (o==null)
         return false;
      Student other= (Student)o;
      return Objects.equals(this.surname,other.surname) &&Objects.equals(this.givenName, other.givenName)
               && Objects.equals(this.age, other.age) && Objects.equals(this.currentCourses, other.currentCourses);
   }
   public int hashCode(){
      return Objects.hash(surname, givenName, age, currentCourses);
      //return Objects.hashCode(this);
   }

}
