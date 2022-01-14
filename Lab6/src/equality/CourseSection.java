package equality;

import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }
   public boolean equals (Object o){
      if (o == null )
         return false;
      CourseSection other = (CourseSection)o;
      return this.prefix.equals(other.prefix) &&
              this.number.equals(other.number)&&
              this.enrollment==other.enrollment&&
              this.startTime.equals(other.startTime)&&
              this.endTime.equals(other.endTime);
   }
   public int hashCode(){
      int hash = 7;
      hash *= 31 * this.enrollment;
      hash *= 31 * (this.prefix.hashCode() + this.number.hashCode());
      hash *= 31 * (this.startTime.hashCode() + this.endTime.hashCode());
      return hash;
   }

}
