package Part2;
//import java.util.ArrayList;
import java.util.List;
public class Applicant {
    private String name;
    private List <CourseGrade> grades;
    private  int activities;
    private boolean good_resume;

    public Applicant (String name, List<CourseGrade> grades, int activities, boolean has_good_resume){
        this.name = name;
        this.grades = grades;
        this.activities = activities;
        this.good_resume = has_good_resume;
    }
    public String getName (){
        return name;
    }
    public List<CourseGrade> getGrades() {
        return grades;
    }
    public CourseGrade getGradeFor (String course){
        for (CourseGrade grade : grades){
            if (grade.getCourseName().equals(course)){
                return grade;
            }
        }
        return new CourseGrade();
    }
    public int getActivities(){
        return activities;
    }
    public boolean hasGoodResume(){
        return good_resume;
    }
    public boolean filter(){
        return SimpleIf.analyzeApplicant2(this);
    }
    public Applicant(){
        this.name ="";
        this.grades = null;
    }
    public Applicant(String name, List <CourseGrade> grades ) {
        this.name = name;
        this.grades = grades;
    }
}
