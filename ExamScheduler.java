//App uses undirected graphs to build an exam schedule with no conflicts and the fewest numbr of exam splots (using a greedy algorithm) 

import structure5.*;
import java.util.Scanner;
import java.util.Iterator;

public class ExamScheduler {

  /** Read in the file and create all of the students (hold in a vector of students)
   * @param file to read student from
   * @pre the file is correctly formatted
   * @return vector of all students the file, as Student objects
   */
  public static Vector<Student> readFile(String fileName) {

    //make scanner and vector to hold students
		Scanner sc = new Scanner(new FileStream(fileName));
    Vector<Student> allStudents = new Vector<Student>();

    //while there is more file to read
		while (sc.hasNext()) {

      //get student name from the file and create Student object
      Student theStudent = new Student(sc.nextLine(), new Vector<String>());

      //add classes to the Student
      for (int i = 1; i <= 4; i++) {
        theStudent.addClass(sc.nextLine());
      }

      //add Student to the vector
      allStudents.add(theStudent);

		}

    //return vector with all the students
    return allStudents;

  }

  /** Go through all students and make a graph of classes
   * @param vector of students to use
   * @return a graph of with all classes as verticies, and edges representing scheduling conflicts
   */
  public static GraphListUndirected<String, String> createGraph(Vector<Student> students) {

    //Intialize graph
    GraphListUndirected<String, String> theGraph = new GraphListUndirected<String, String>();

    //loop through all the students
    for (Student s : students) {

      Vector<String> sClasses = s.getClasses();

      //loop through all classes that student is taking
      for (String c : sClasses) {

        //System.out.println(c);

        //if class is not is not on the graph, add it
        if (!theGraph.contains(c)) {
          theGraph.add(c);
        }

        //for every pair of classes, add a conflict edge
        for (String cl : sClasses) {
          if (theGraph.contains(cl)) {
            theGraph.addEdge(c, cl, "conflict");
          }
        }

      }

    }

    //return the completed graph
    return theGraph;

  }


  /** Go through the graph and fill out all of the slots in the exam schedule
   * @param graph to cycle over
   * @return return a vector of associations that represents the examschedule
   */
  public static Vector<Vector<String>> makeExamSchedule(GraphListUndirected<String, String> classGraph) {

    Vector<Vector<String>> examschedule = new Vector<Vector<String>>();

    //while there are verticies left in the graph
    while (!classGraph.isEmpty()) {

      Vector<String> theSlot = new Vector<String>();

      Iterator<String> classIterator = classGraph.iterator();

      //loop thorugh entire graph
      while (classIterator.hasNext()) {

        //get the next class from the interator
        String nextClass = classIterator.next();

        //if that class is a valid addition to the slot, add it
        if (validToAdd(nextClass, theSlot, classGraph)) {
          theSlot.add(nextClass);
        }

      }

      //remove all the classes in the slot from the graph
      for (String aClass : theSlot) {
        classGraph.remove(aClass);
      }

      //add the slot to the exam schedule
      examschedule.add(theSlot);

    }

    return examschedule;

  }


  /** Check if a class can be added to a slot
   * @param the class you want to add
   * @param the the slot of classes
   * @param the graph of classes
   * @return boolean of whether or not the addition is valid
   */
  protected static boolean validToAdd(String theClass, Vector<String> theSlot, GraphListUndirected<String, String> classGraph) {
    //check if new class if connected to any classes already in the slot
    for (String vertex : theSlot) {
      if (classGraph.containsEdge(vertex, theClass)) {
        return false;
      }
    }
    //if not, return true
    return true;
  }


  /** Print the exam scheduel to the console
   * @param the vector of slots
   * @post class schedule printed to the console
   */
  public static void printExamSchedule(Vector<Vector<String>> slots) {

    int slotNumber = 1;

    for (Vector<String> slot : slots) {

      System.out.print("Slot " + slotNumber + ": ");

      for (String aclass : slot) {
        System.out.print(aclass  + " ");
      }

      System.out.print("\n");
      slotNumber++;

    }

  }

  /** Create a vector of classes and their exam slots
   * @param the exam schedule
   * @return a vector of all the classes and their exam slots
   */
  public static Vector<String> createClassVector(Vector<Vector<String>> examSched) {

    Vector<String> classExamSched = new Vector<String>();

    int i = 1;

    for (Vector<String> slot : examSched) {

      for (String aClass : slot) {
        classExamSched.add(aClass + " - slot " + i);
      }

      i++;

    }

    return classExamSched;
  }

  /** Print all classes in alphabetical order with their exam slots
   * @param the vector of classes and their slots
   * @post classes printed to the console
   */
  public static void printClassExamEched(Vector<String> classExamSched) {

    OrderedList<String> orderedClasses = new OrderedList<String>();

    //add on the classes to the ordered list
    for (String aClass : classExamSched) {
      orderedClasses.add(aClass);
    }

    //create iterator over orderedClasses
    Iterator<String> classIterator = orderedClasses.iterator();

    while (classIterator.hasNext()) {
      System.out.println(classIterator.next());
    }

  }

  /** Main
   */
  public static void main(String[] args) {

    Vector<Student> studentList = readFile(args[0]);

    GraphListUndirected<String, String> conflictGraph = createGraph(studentList);

    Vector<Vector<String>> examSched = makeExamSchedule(conflictGraph);

    printExamSchedule(examSched);

    Vector<String> classExamSched = createClassVector(examSched);

    printClassExamEched(classExamSched);

  }


}
