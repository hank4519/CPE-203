package Part2;

public class SimpleIf {

  /**
   * Takes an applicant's average score and accepts the applicant if the average
   * is higher than 85.
   * 
   * @param avg The applicant's average score
   * @param threshold The threshold score
   * @return true if the applicant's average is over the threshold, and false otherwise
   */
  public static boolean analyzeApplicant(double avg, double threshold) {
    /*
     * TO DO: Write an if statement to determine if the avg is larger than the threshold and
     * return true if so, false otherwise
     */
    if (avg > threshold)
      return true;
    return false;
  }
  /*
    So basically, my idea for filtering applicants is:
    I will first check how many class this applicant is taking: num_of_class
    Applicant is required to participate in # of extracurricular activities >= 1/2 of num_of_class
    For example, if you take 5 classes, you'd have at least 2 corresponding activities (5/2 = 2)
    If so, applicant is eligible. If not, then check if the applicant has a good resume (already good enough)
    if an applicant has neither enough activities participated, nor a good resume, then he/she is not eligible
   */
  public static boolean analyzeApplicant2(Applicant app){
    int num_of_class = app.getGrades().size();
    if (app.getActivities() < (num_of_class / 2) && !app.hasGoodResume()) {
      return false;
    }
    return true;
  }


  /**
   * Takes two applicants' average scores and returns the score of the applicant
   * with the higher average.
   * 
   * @param avg1 The first applicant's average score
   * @param avg2 The second applicant's average score
   * @return the higher average score
   */
  public static double maxAverage(double avg1, double avg2) {
    /*
     * TO DO: Write an if statement to determine which average is larger and return
     * that value.
     */
    if (avg1 > avg2)
      return avg1;
    return avg2;
    //return 0; Clearly not correct, but testable.
  }
}
