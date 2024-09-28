import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.lang.Exception;

public class PomodoroTimer extends JFrame {
    private int workMinutes = 60;
    private int breakMinutes = 5;

    private int remainingSeconds;
    private Timer timer;
    private final JFrame frame;
    private final JLabel timeLabel;
    private final JButton startButton;
    private final JButton stopButton;
    private boolean isWorking;
    private int selectedChapter = 1;

    private final JTextField workMinutesField;
    private final JTextField breakMinutesField;

    public PomodoroTimer() {

        // Initialize the quizResults map
        // Track chapter -> [correct answers, total questions]
        frame = new JFrame("Pomodoro Timer");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        frame.add(new JLabel("Work Minutes:"));
        workMinutesField = new JTextField(String.valueOf(workMinutes), 5);
        frame.add(workMinutesField);

        frame.add(new JLabel("Break Minutes:"));
        breakMinutesField = new JTextField(String.valueOf(breakMinutes), 5);
        frame.add(breakMinutesField);

        timeLabel = new JLabel("00:00");
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 36));
        frame.add(timeLabel);

        startButton = new JButton("Start");
        startButton.addActionListener(_ -> {
            // Parse user input
            try {
                workMinutes = Integer.parseInt(workMinutesField.getText());
                breakMinutes = Integer.parseInt(breakMinutesField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
                return;
            }

            startTimer();
        });
        frame.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.addActionListener(_ -> stopTimer());
        frame.add(stopButton);

        frame.setVisible(true);
    }

    private void startTimer() {
        isWorking = true;
        remainingSeconds = workMinutes * 60;
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (remainingSeconds > 0) {
                        remainingSeconds--;
                        updateTimeLabel();
                    } else {
                        timer.cancel();
                        switchMode();
                    }
                });
            }
        }, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        remainingSeconds = isWorking ? workMinutes * 60 : breakMinutes * 60;
        updateTimeLabel();
    }

    private void switchMode() {
        if (isWorking) {
            JOptionPane.showMessageDialog(frame, "Time for a break!");
            isWorking = false;
            remainingSeconds = breakMinutes * 60;
            selectChapter();
            showQuiz();
        } else {
            JOptionPane.showMessageDialog(frame, "Back to work!");
            isWorking = true;
            remainingSeconds = workMinutes * 60;
            startTimer();
        }
    }

    private void updateTimeLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void selectChapter() {
        String[] chapters = {"Module 1: Introduction to Object Oriented Programming", "Module 2: Class, Object, Packages and Input/output", "Module 3: Array, String and Vector","Module 4: Inheritance","Module 5: Exception handling and Multithreading","Module 6: GUI programming in JAVA"};
        String selectedValue = (String) JOptionPane.showInputDialog(
                frame,
                "Select a chapter:",
                "Chapter Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                chapters,
                chapters[0]
        );

        if (selectedValue == null) {
            selectedChapter = 1;
        } else {
            switch (selectedValue) {
                case "Module 1: Introduction to Object Oriented Programming":
                    selectedChapter = 1;
                    break;
                case "Module 2: Class, Object, Packages and Input/output":
                    selectedChapter = 2;
                    break;
                case "Module 3: Array, String and Vector":
                    selectedChapter = 3;
                    break;
                case "Module 4: Inheritance":
                    selectedChapter = 4;
                    break;
                case "Module 5: Exception handling and Multithreading":
                    selectedChapter = 5;
                    break;
                case "Module 6: GUI programming in JAVA":
                    selectedChapter = 6;
                    break;
            }
        }
    }
    private final String[][] chapter1Questions = {
            {"What is Object-Oriented Programming (OOP)?", "A programming paradigm based on the concept of objects", "A procedural programming paradigm", "A functional programming paradigm", "A scripting programming paradigm", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"Which of the following is not a principle of OOP?", "Encapsulation", "Inheritance", "Polymorphism", "Compilation", "4", "https://www.javatpoint.com/what-is-object-oriented-programming"},
            {"What is a class in Java?", "A blueprint for creating objects", "An instance of an object", "A method", "A variable", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is an object in Java?", "An instance of a class", "A blueprint for creating classes", "A method", "A variable", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"Which keyword is used to create an object in Java?", "new", "create", "object", "class", "1", "https://www.javatpoint.com/what-is-object-oriented-programming"},
            {"What is inheritance?", "The mechanism by which one class acquires the properties and behaviors of another class", "The ability to hide data", "The wrapping of data and methods into a single unit", "The ability to take many forms", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is polymorphism?", "The ability of an object to take on many forms", "The ability to inherit features from another class", "The wrapping of data and methods into a single unit", "The ability to hide data", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is abstraction?", "The process of hiding the implementation details and showing only the functionality", "The ability to inherit features from another class", "The wrapping of data and methods into a single unit", "The ability to take many forms", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"Which keyword is used to inherit a class in Java?", "extends", "implements", "inherits", "super", "1", "https://www.javatpoint.com/what-is-object-oriented-programming"},
            {"What is the purpose of the 'super' keyword?", "To refer to the immediate parent class object", "To refer to the current class object", "To create a new object", "To delete an object", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is method overloading?", "Having multiple methods with the same name but different parameters", "Having multiple methods with the same name and same parameters", "Having multiple methods with different names and same parameters", "Having multiple methods with different names and different parameters", "1", "https://www.javatpoint.com/what-is-object-oriented-programming"},
            {"What is method overriding?", "A subclass provides a specific implementation of a method that is already defined in its superclass", "A subclass hides a method of its superclass", "A subclass defines a new method", "A subclass inherits a method without changes", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is the difference between an abstract class and an interface?", "An abstract class can have method implementations, an interface cannot", "An interface can have method implementations, an abstract class cannot", "Both can have method implementations", "Neither can have method implementations", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"Can an abstract class be instantiated?", "No", "Yes", "Only if it is public", "Only if it is protected", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is the 'this' keyword used for?", "To refer to the current class object", "To refer to the immediate parent class object", "To create a new object", "To delete an object", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is the 'super()' call used for?", "To call the constructor of the superclass", "To call a method of the superclass", "To create a new object", "To delete an object", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is a constructor?", "A special method that is called when an object is instantiated", "A method that is called to destroy an object", "A method that is called to update an object", "A method that is called to delete an object", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is the purpose of the 'final' keyword in inheritance?", "To prevent a class from being subclassed", "To allow a class to be subclassed", "To hide a class", "To make a class abstract", "1", "https://www.educative.io/blog/object-oriented-programming"},
            {"What is the purpose of the 'instanceof' keyword?", "To check if an object is an instance of a specific class", "To create a new instance of a class", "To delete an instance of a class", "To update an instance of a class", "1", "https://www.educative.io/blog/object-oriented-programming"}
    };

    private final String[][] chapter2Questions = {
            {"What is a class in Java?", "A blueprint for creating objects", "A data type", "A method", "A variable", "1", "https://en.wikipedia.org/wiki/Class_(computer_programming)"},
            {"What is an object in Java?", "An instance of a class", "A method", "A variable", "A package", "1", "https://en.wikipedia.org/wiki/Object_(computer_science)"},
            {"Which keyword is used to create an object in Java?", "new", "class", "object", "create", "1", "https://en.wikipedia.org/wiki/Object_(computer_science)"},
            {"What is the purpose of a constructor in a class?", "To initialize objects", "To create methods", "To define variables", "To import packages", "1", "https://en.wikipedia.org/wiki/Constructor_(object-oriented_programming)"},
            {"Which method is called when an object is created?", "Constructor", "Main", "Init", "Start", "1", "https://en.wikipedia.org/wiki/Constructor_(object-oriented_programming)"},
            {"What is a package in Java?", "A namespace for organizing classes and interfaces", "A method", "A variable", "A class", "1", "https://en.wikipedia.org/wiki/Java_package"},
            {"Which keyword is used to import a package in Java?", "import", "package", "include", "namespace", "1", "https://en.wikipedia.org/wiki/Java_package"},
            {"What is the default package in Java?", "java.lang", "java.util", "java.io", "java.net", "1", "https://en.wikipedia.org/wiki/Java_package"},
            {"Which package contains the Scanner class?", "java.util", "java.io", "java.lang", "java.net", "1", "https://en.wikipedia.org/wiki/Java_util_package"},
            {"How do you read input from the console in Java?", "Using the Scanner class", "Using the InputStream class", "Using the OutputStream class", "Using the File class", "1", "https://en.wikipedia.org/wiki/Java_util_package"},
            {"Which method of the Scanner class is used to read a string?", "nextLine()", "nextInt()", "nextDouble()", "next()", "1", "https://en.wikipedia.org/wiki/Java_util_package"},
            {"Which class is used to write data to a file in Java?", "FileWriter", "FileReader", "BufferedReader", "Scanner", "1", "https://en.wikipedia.org/wiki/Java.io.FileWriter"},
            {"Which method of the FileWriter class is used to write a string to a file?", "write()", "read()", "append()", "print()", "1", "https://en.wikipedia.org/wiki/Java.io.FileWriter"},
            {"Which class is used to read data from a file in Java?", "FileReader", "FileWriter", "BufferedWriter", "Scanner", "1", "https://en.wikipedia.org/wiki/Java.io.FileReader"},
            {"Which method of the FileReader class is used to read a character from a file?", "read()", "write()", "next()", "scan()", "1", "https://en.wikipedia.org/wiki/Java.io.FileReader"},
            {"What is the purpose of the BufferedReader class?", "To read text from an input stream efficiently", "To write text to an output stream", "To create objects", "To define classes", "1", "https://en.wikipedia.org/wiki/BufferedReader"},
            {"Which method of the BufferedReader class is used to read a line of text?", "readLine()", "read()", "nextLine()", "scanLine()", "1", "https://en.wikipedia.org/wiki/BufferedReader"},
            {"What is the purpose of the PrintWriter class?", "To write formatted text to an output stream", "To read text from an input stream", "To create objects", "To define classes", "1", "https://en.wikipedia.org/wiki/PrintWriter"},
            {"Which method of the PrintWriter class is used to print a formatted string?", "printf()", "print()", "write()", "format()", "1", "https://en.wikipedia.org/wiki/PrintWriter"},
            {"Which class is used to handle exceptions related to input/output operations?", "IOException", "FileNotFoundException", "InputMismatchException", "NullPointerException", "1", "https://en.wikipedia.org/wiki/IOException"}

    };

    private final String[][] chapter3Questions = {
            {"What is the default value of an array of integers in Java?", "0", "1", "null", "undefined", "2", "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html"},
            {"Which method is used to convert a string to a character array in Java?", "toCharArray()", "getChars()", "charAt()", "toArray()", "1", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#toCharArray()"},
            {"How do you declare a one-dimensional array in Java?", "int[] arr;", "int arr[];", "int arr[10];", "Both 1 and 2", "4", "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html"},
            {"Which class provides the functionality to dynamically grow and shrink the size of an array?", "ArrayList", "Vector", "LinkedList", "HashSet", "2", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html"},
            {"What is the length of the string \"Hello World\"?", "11", "10", "12", "13", "1", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#length()"},
            {"Which method is used to compare two strings in Java?", "equals()", "compareTo()", "==", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#equals(java.lang.Object)"},
            {"How do you initialize an array with values in Java?", "int[] arr = {1, 2, 3};", "int arr[] = new int[3];", "int arr[] = new int[]{1, 2, 3};", "Both 1 and 3", "4", "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html"},
            {"Which method is used to find the length of an array in Java?", "length", "size()", "length()", "getLength()", "1", "https://docs.oracle.com/javase/tutorial/java/nutsandbolts/arrays.html"},
            {"How do you concatenate two strings in Java?", "concat()", "+", "append()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#concat(java.lang.String)"},
            {"Which method is used to convert a string to lowercase in Java?", "toLowerCase()", "toLower()", "lower()", "toLowerCase(Locale)", "1", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#toLowerCase()"},
            {"What is the default capacity of a Vector in Java?", "10", "16", "8", "12", "1", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html"},
            {"Which method is used to add an element to a Vector in Java?", "addElement()", "add()", "insertElementAt()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html#addElement(E)"},
            {"How do you convert a string to an integer in Java?", "Integer.parseInt()", "Integer.valueOf()", "toInt()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html#parseInt(java.lang.String)"},
            {"Which method is used to remove an element from a Vector in Java?", "removeElement()", "remove()", "delete()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html#removeElement(java.lang.Object)"},
            {"How do you check if a string contains a specific substring in Java?", "contains()", "indexOf()", "substring()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#contains(java.lang.CharSequence)"},
            {"Which method is used to sort an array in Java?", "sort()", "Arrays.sort()", "Collections.sort()", "Both 2 and 3", "2", "https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#sort(int[])"},
            {"How do you create a new Vector with a specified initial capacity?", "new Vector(int initialCapacity)", "new Vector()", "new Vector(Collection c)", "new Vector(int initialCapacity, int capacityIncrement)", "1", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html#Vector(int)"},
            {"Which method is used to replace a character in a string in Java?", "replace()", "replaceAll()", "replaceFirst()", "Both 1 and 2", "1", "https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#replace(char, char)"},
            {"How do you convert an array to a list in Java?", "Arrays.asList()", "ArrayList()", "toList()", "Both 1 and 2", "1", "https://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#asList(T...)"},
            {"Which method is used to clear all elements from a Vector in Java?", "clear()", "removeAllElements()", "deleteAll()", "Both 1 and 2", "4", "https://docs.oracle.com/javase/7/docs/api/java/util/Vector.html#clear()"}
    };

    private final String[][] chapter4Questions = {
            {"What is inheritance in Java?", "A mechanism where one class acquires the properties and behaviors of another class", "A way to create multiple classes", "A method to hide data", "A technique to encapsulate data", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"Which keyword is used to inherit a class in Java?", "extends", "implements", "inherits", "super", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is the superclass in Java?", "The class that is inherited from", "The class that inherits from another class", "A class with no methods", "A class with only static methods", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"Can a class in Java inherit multiple classes?", "No", "Yes", "Only if they are abstract classes", "Only if they are interfaces", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is the keyword used to call the constructor of the superclass?", "super", "this", "extends", "base", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/super.html"},
            {"What is method overriding?", "A subclass provides a specific implementation of a method that is already defined in its superclass", "A subclass hides a method of its superclass", "A subclass defines a new method", "A subclass inherits a method without changes", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/override.html"},
            {"Which method is used to check if an object is an instance of a specific class?", "instanceof", "isInstance", "isObject", "isClass", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is the difference between method overloading and method overriding?", "Overloading is within the same class, overriding is in a subclass", "Overloading is in a subclass, overriding is within the same class", "Overloading changes the method name, overriding changes the method body", "Overloading changes the return type, overriding changes the method parameters", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/override.html"},
            {"What is the purpose of the 'final' keyword in inheritance?", "To prevent a class from being subclassed", "To allow a class to be subclassed", "To hide a class", "To make a class abstract", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/final.html"},
            {"Can constructors be inherited in Java?", "No", "Yes", "Only if they are public", "Only if they are protected", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is a subclass?", "A class that inherits from another class", "A class that is inherited from", "A class with no methods", "A class with only static methods", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is the 'super' keyword used for?", "To refer to the immediate parent class object", "To refer to the current class object", "To create a new object", "To delete an object", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/super.html"},
            {"What is multiple inheritance?", "A class can inherit from more than one class", "A class can inherit from only one class", "A class can inherit from multiple interfaces", "A class can inherit from multiple abstract classes", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"Why is multiple inheritance not supported in Java?", "To avoid complexity and ambiguity", "To allow for more flexibility", "To support polymorphism", "To enable encapsulation", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html"},
            {"What is an abstract class?", "A class that cannot be instantiated", "A class that can be instantiated", "A class with only static methods", "A class with no methods", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html"},
            {"What is the difference between an abstract class and an interface?", "An abstract class can have method implementations, an interface cannot", "An interface can have method implementations, an abstract class cannot", "Both can have method implementations", "Neither can have method implementations", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html"},
            {"Can an abstract class have a constructor?", "Yes", "No", "Only if it is public", "Only if it is protected", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/abstract.html"},
            {"What is the 'this' keyword used for?", "To refer to the current class object", "To refer to the immediate parent class object", "To create a new object", "To delete an object", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/this.html"},
            {"What is the 'super()' call used for?", "To call the constructor of the superclass", "To call a method of the superclass", "To create a new object", "To delete an object", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/super.html"},
            {"What is polymorphism?", "The ability of an object to take on many forms", "The ability of a class to inherit from multiple classes", "The ability of a method to be overridden", "The ability of a class to be abstract", "1", "https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html"}
    };
    private final String[][] chapter5Questions ={
            {"What is an exception in Java?", "An abnormal condition that disrupts the normal flow of a program", "A syntax error", "A logical error", "A runtime error", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/definition.html"},
            {"Which keyword is used to handle exceptions in Java?", "try", "catch", "finally", "All of the above", "4", "https://docs.oracle.com/javase/tutorial/essential/exceptions/handling.html"},
            {"What is the difference between checked and unchecked exceptions?", "Checked exceptions are checked at compile-time, unchecked exceptions are checked at runtime", "Checked exceptions are checked at runtime, unchecked exceptions are checked at compile-time", "Both are checked at compile-time", "Both are checked at runtime", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/runtime.html"},
            {"Which method is used to throw an exception in Java?", "throw", "throws", "thrown", "throwing", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html"},
            {"What is the purpose of the 'finally' block?", "To execute code regardless of whether an exception is thrown or not", "To catch exceptions", "To throw exceptions", "To define a new exception", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/finally.html"},
            {"Can we have multiple catch blocks for a single try block?", "Yes", "No", "Only if they are of different types", "Only if they are of the same type", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/catch.html"},
            {"What is the 'throws' keyword used for?", "To declare an exception", "To handle an exception", "To throw an exception", "To catch an exception", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/declaring.html"},
            {"What is a custom exception?", "An exception defined by the user", "A built-in exception", "A runtime exception", "A checked exception", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/creating.html"},
            {"What is the difference between 'throw' and 'throws'?", "'throw' is used to throw an exception, 'throws' is used to declare an exception", "'throw' is used to declare an exception, 'throws' is used to throw an exception", "Both are used to throw exceptions", "Both are used to declare exceptions", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/throwing.html"},
            {"What is the purpose of the 'try' block?", "To monitor a block of code for exceptions", "To catch exceptions", "To throw exceptions", "To declare exceptions", "1", "https://docs.oracle.com/javase/tutorial/essential/exceptions/handling.html"},
            {"What is multithreading in Java?", "A process of executing multiple threads simultaneously", "A process of executing a single thread", "A process of executing multiple processes", "A process of executing a single process", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/"},
            {"Which method is used to start a thread in Java?", "start()", "run()", "begin()", "init()", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html"},
            {"What is the difference between 'start()' and 'run()' methods in Java?", "'start()' creates a new thread and calls 'run()', 'run()' executes in the current thread", "'start()' executes in the current thread, 'run()' creates a new thread", "Both create a new thread", "Both execute in the current thread", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html"},
            {"What is synchronization in Java?", "A mechanism to control the access of multiple threads to shared resources", "A mechanism to create multiple threads", "A mechanism to stop threads", "A mechanism to start threads", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/sync.html"},
            {"Which keyword is used to synchronize a method in Java?", "synchronized", "synchronize", "sync", "syncs", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html"},
            {"What is a deadlock in Java?", "A situation where two or more threads are blocked forever, waiting for each other", "A situation where a thread is running", "A situation where a thread is stopped", "A situation where a thread is waiting", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html"},
            {"What is the purpose of the 'wait()' method in Java?", "To make the current thread wait until another thread invokes 'notify()' or 'notifyAll()'", "To start a thread", "To stop a thread", "To run a thread", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html"},
            {"What is the purpose of the 'notify()' method in Java?", "To wake up a single thread that is waiting on the object's monitor", "To start a thread", "To stop a thread", "To run a thread", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html"},
            {"What is the purpose of the 'notifyAll()' method in Java?", "To wake up all threads that are waiting on the object's monitor", "To start a thread", "To stop a thread", "To run a thread", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html"},
            {"What is the 'Runnable' interface in Java?", "An interface that should be implemented by any class whose instances are intended to be executed by a thread", "An interface to create threads", "An interface to stop threads", "An interface to run threads", "1", "https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html"}
    };
    private final String[][] chapter6Questions ={
            {"What is Swing in Java?", "A GUI toolkit that includes GUI components", "A database management system", "A web framework", "A networking library", "1", "https://www.guru99.com/java-swing-gui.html"},
            {"Which package contains the Swing classes?", "javax.swing", "java.awt", "java.swing", "javax.awt", "1", "https://docs.oracle.com/javase/tutorial/uiswing/start/index.html"},
            {"What is the purpose of the JFrame class?", "To create a window", "To create a button", "To create a text field", "To create a menu", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html"},
            {"Which method is used to make a JFrame visible?", "setVisible(true)", "show()", "display()", "setVisible()", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html"},
            {"What is the default layout manager for a JFrame?", "BorderLayout", "FlowLayout", "GridLayout", "CardLayout", "1", "https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html"},
            {"Which class is used to create a button in Swing?", "JButton", "Button", "SwingButton", "JButtonComponent", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/button.html"},
            {"How do you add a component to a JFrame?", "add(Component c)", "insert(Component c)", "put(Component c)", "append(Component c)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html"},
            {"Which method is used to set the size of a JFrame?", "setSize(int width, int height)", "setDimensions(int width, int height)", "setBounds(int width, int height)", "setFrameSize(int width, int height)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html"},
            {"What is the purpose of the JPanel class?", "To create a container for other components", "To create a window", "To create a button", "To create a text field", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html"},
            {"Which method is used to add a tooltip to a component?", "setToolTipText(String text)", "setTooltip(String text)", "addTooltip(String text)", "setToolTip(String text)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/tooltip.html"},
            {"What is the purpose of the JTextField class?", "To create a single-line text input field", "To create a multi-line text input field", "To create a password field", "To create a label", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html"},
            {"Which method is used to set the text of a JLabel?", "setText(String text)", "setLabelText(String text)", "setContent(String text)", "setLabel(String text)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/label.html"},
            {"What is the purpose of the JComboBox class?", "To create a drop-down list", "To create a button", "To create a text field", "To create a menu", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html"},
            {"Which method is used to add an item to a JComboBox?", "addItem(Object item)", "insertItem(Object item)", "putItem(Object item)", "appendItem(Object item)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/combobox.html"},
            {"What is the purpose of the JList class?", "To create a list of items", "To create a table", "To create a tree", "To create a menu", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/list.html"},
            {"Which method is used to add an item to a JList?", "addElement(Object item)", "insertElement(Object item)", "putElement(Object item)", "appendElement(Object item)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/list.html"},
            {"What is the purpose of the JTable class?", "To create a table", "To create a list", "To create a tree", "To create a menu", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/table.html"},
            {"Which method is used to set the data model for a JTable?", "setModel(TableModel model)", "setDataModel(TableModel model)", "setTableModel(TableModel model)", "setModelData(TableModel model)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/table.html"},
            {"What is the purpose of the JTree class?", "To create a tree structure", "To create a table", "To create a list", "To create a menu", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html"},
            {"Which method is used to add a node to a JTree?", "addNode(DefaultMutableTreeNode node)", "insertNode(DefaultMutableTreeNode node)", "putNode(DefaultMutableTreeNode node)", "appendNode(DefaultMutableTreeNode node)", "1", "https://docs.oracle.com/javase/tutorial/uiswing/components/tree.html"}
    };
    private final Map<Integer, String[][]> chapterQuestions = new HashMap<>() {{
        put(1, chapter1Questions);
        put(2, chapter2Questions);
        put(3, chapter3Questions);
        put(4, chapter4Questions);
        put(5, chapter5Questions);
        put(6, chapter6Questions);
    }};

    private void showQuiz() {
        // Ensure the selected chapter is valid
        if (!chapterQuestions.containsKey(selectedChapter)) {
            JOptionPane.showMessageDialog(frame, "Invalid chapter selected. Please choose a valid chapter.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Exit method if invalid chapter
        }

        // Fetch the questions for the selected chapter
        String[][] selectedQuizQuestions = chapterQuestions.get(selectedChapter);

        // Shuffle the questions (optional)
        List<String[]> questionList = new ArrayList<>(Arrays.asList(selectedQuizQuestions));
        Collections.shuffle(questionList);
        selectedQuizQuestions = questionList.toArray(new String[0][]);

        int correctAnswers = 0;
        for (String[] questionData : selectedQuizQuestions) {
            String question = questionData[0];

            // Ensure there are exactly four options
            if (questionData.length < 6) {
                JOptionPane.showMessageDialog(frame, "Question data is incomplete: " + question, "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Skip this question
            }

            // Fetch options and correct answer index
            String[] options = {questionData[1], questionData[2], questionData[3], questionData[4]};
            int correctAnswer = Integer.parseInt(questionData[5]);

            // Ensure the correct answer index is valid
            if (correctAnswer < 0 || correctAnswer >= options.length) {
                JOptionPane.showMessageDialog(frame, "Incorrect answer index for question: " + question, "Error", JOptionPane.ERROR_MESSAGE);
                continue; // Skip this question
            }

            String referenceURL = questionData.length > 6 ? questionData[6] : null; // Check if URL exists

            // Show the quiz question and capture user response
            String userAnswer = (String) JOptionPane.showInputDialog(
                    frame,
                    question,
                    "Quiz Time!",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (userAnswer == null) {
                JOptionPane.showMessageDialog(frame, "Quiz skipped.");
                break; // Exit the quiz loop if user skips
            } else {
                int selectedAnswerIndex = Arrays.asList(options).indexOf(userAnswer);
                if (selectedAnswerIndex == correctAnswer) {
                    JOptionPane.showMessageDialog(frame, "Correct!");
                    correctAnswers++;
                } else {
                    showWrongAnswerDialog(options[correctAnswer], referenceURL);
                }
            }
        }

        // Show final score
        JOptionPane.showMessageDialog(
                frame,
                "Quiz completed! You scored " + correctAnswers + " out of " + selectedQuizQuestions.length + ".",
                "Final Score",
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    private void showWrongAnswerDialog(String correctAnswer, String referenceURL) {
        // Create a panel with a message and a button to open the link
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel("Wrong answer! The correct answer is: " + correctAnswer);
        panel.add(messageLabel, BorderLayout.NORTH);

        if (referenceURL != null) {
            JButton openLinkButton = new JButton("Learn More");
            openLinkButton.addActionListener(_ -> openLink(referenceURL));
            panel.add(openLinkButton, BorderLayout.SOUTH);
        }

        // Show the custom dialog
        JOptionPane.showMessageDialog(frame, panel, "Incorrect Answer", JOptionPane.ERROR_MESSAGE);
    }

    private void openLink(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            } else {
                JOptionPane.showMessageDialog(frame, "Opening URLs is not supported on your system.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to open the link.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}