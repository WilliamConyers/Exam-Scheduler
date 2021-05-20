// Students, fill this in.

import structure5.*;

public class Student {

  protected String name;
  protected Vector<String> classes;

  /** Constructor
   * @param name of the student
   * @param vector of classes student is taking
   * @post instance variables are assigned values
   */
  public Student(String studentName, Vector<String> studentClasses) {
    name = studentName;
    classes = studentClasses;
  }

  /** Get the student's name
   * @return the name
   */
  public String getName() {
    return name;
  }

  /** Get the student's classes
   * @return the vector of classes
   */
  public Vector<String> getClasses() {
    return classes;
  }

  /** Add a class to the Student
   * @param the class to add
   * @post the student has a new class
   */
  public void addClass(String theClass) {
    classes.add(theClass);
  }

  /** Print readable version of the student
   * @return string version of the student
   */
  public String toString() {
    return (name + "/n" + getClasses());
  }

}
