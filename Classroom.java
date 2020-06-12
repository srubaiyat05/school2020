import java.util.ArrayList;
import java.util.*;
public class Classroom extends ConsoleProgram
{
    public String name;
    public HashMap<Student, Gradebook> students;
    private ArrayList<Assignment> assignments;
    private ArrayList<AssignmentCategory> categories;
    public Teacher teacher;
    
    public static ArrayList<Classroom> classes = new ArrayList<Classroom>();
    
    public Classroom(String n, Teacher t, ArrayList<AssignmentCategory> c)
    {
        name = n;
        teacher = t;
        categories = c;
        students = new HashMap<Student, Gradebook>();
        assignments = new ArrayList<Assignment>();
        teacher.addClass(this);
        classes.add(this);
    }
    //user input constructor
    public Classroom()
    {
        name = readLine("Name: ");
        String t = readLine("Teacher: ");
        while(Teacher.object(t) == null)
        {
            t = readLine("Teacher: ");
        }
        teacher = Teacher.object(t);
        
        String w = readLine("Weights: ");
        boolean done = true;
        ArrayList<String> str= new ArrayList<String>();
        if(w.indexOf("  ") > -1)
        {
            done = false;
            System.out.println("Formatted incorrectly.");
        }
        else
        {
            while(w.indexOf(" ") > -1)
            {
                str.add(w.substring(0, w.indexOf(" ")));
                w = w.substring(w.indexOf(" ")+1);
            }
            str.add(w);
            double sum = 0;
            for(String s: str)
            {
                if(!isDouble(s))
                {
                    done = false;
                    System.out.println("Formatted incorrectly.");
                    break;
                }
                else
                {
                    sum += toDouble(s);
                }
            }
            if(sum != 100)
            {
                if(done)
                {
                    System.out.println("Those are not valid weights. Try again.");
                    done = false;
                }
                
            }
        }
        while(!done)
        {
            //System.out.println("Those are not valid weights. Try again.");
            w = readLine("Weights: ");
            done = true;
            str= new ArrayList<String>();
            if(w.indexOf("  ") > -1)
            {
                done = false;
                System.out.println("Formatted incorrectly.");
            }
            else
            {
                while(w.indexOf(" ") > -1)
                {
                    str.add(w.substring(0, w.indexOf(" ")));
                    w = w.substring(w.indexOf(" ")+1);
                }
                str.add(w);
                double sum = 0;
                for(String s: str)
                {
                    if(!isDouble(s))
                    {
                        done = false;
                        System.out.println("Formatted incorrectly.");
                        break;
                    }
                    else
                    {
                        sum += toDouble(s);
                    }
                }
                if(sum != 100)
                {
                    if(done)
                    {
                        System.out.println("Those are not valid weights. Try again.");
                        done = false;
                    }
                }
            }
        }
        ArrayList<AssignmentCategory> c = new ArrayList<AssignmentCategory>();
        for(String s: str)
        {
            c.add(new AssignmentCategory(readLine("Name of category with weight of " + s + ": "), toDouble(s)));
        }
        categories = c;
        students = new HashMap<Student, Gradebook>();
        assignments = new ArrayList<Assignment>();
        classes.add(this);
        teacher.addClass(this);
    }
    //helper method for user input constructor
    private static boolean isDouble(String d)
    {
        if(d.equals(""))
        {
            return false;
        }
        if(d.charAt(0) == '-')
        {
            d = d.substring(1);
        }
        for(int i = 0; i < d.length(); i++)
        {
            if(!Character.isDigit(d.charAt(i)) && d.charAt(i) != '.')
            {
                return false;
            }
        }
        if(d.indexOf(".") > 0)
        {
            return d.substring(d.indexOf(".")+1).indexOf(".") == -1;
        }
        return true;
    }
    //helper method for user input constructor
    private static double toDouble(String d)
    {
        double res = 0;
        int neg = 1;
        if(d.indexOf("-") > -1)
        {
            neg = -1;
            d = d.substring(d.indexOf("-") + 1);
        }
        if(d.indexOf(".") > -1)
        {
            for(int i = 0; i < d.indexOf("."); i++)
            {
                res = res*10 + ((int)d.charAt(i) - 48);
            }
            int dec = 10;
            for(int i = d.indexOf(".") + 1; i < d.length(); i++)
            {
                res += ((int)d.charAt(i) - 48)/(double)dec;
                dec *= 10;
            }
        }
        else
        {
            for(int i = 0; i < d.length(); i++)
            {
                res = res*10 + ((int)d.charAt(i) - 48);
            }
        }
        return neg*res;
    }
    public void addStudent(Student s)
    {
        students.put(s, s.addClass(this));
    }
    //deactivates student gradebook and removes student from roster
    public void removeStudent(Student s)
    {
        HashMap<Student, Gradebook> copy = new HashMap<Student, Gradebook>();
        for(Student key: students.keySet())
        {
            if(key == s)
            {
                students.get(key).deactivate();
            }
            else
            {
                copy.put(key, students.get(key));
            }
        }
        students = copy;
    }
    public void addAssignment(Assignment a)
    {
        for(Student key: students.keySet())
        {
            students.get(key).addAssignment(a);
        }
        assignments.add(a);
    }
    public void removeAssignment(Assignment a)
    {
        for(Student key: students.keySet())
        {
            students.get(key).removeAssignment(a);
        }
        for(int i = assignments.size()-1; i >= 0; i++)
        {
            if(a == assignments.get(i))
            {
                assignments.remove(i);
                break;
            }
        }
    }
    //prints assignment list
    public void print()
    {
        System.out.println();
        System.out.println("ASSIGNMENTS");
        for(Assignment a: assignments)
        {
            a.print();
        }
        System.out.println();
    }
    //returns assignment with a certain name, null if none exists
    public Assignment getAssignment(String name)
    {
        for(Assignment a: assignments)
        {
            if(a.name.equals(name))
            {
                return a;
            }
        }
        return null;
    }
    //user interative for scoring an assignment
    public void scoreAssignment(Assignment a)
    {
        String name = "";
        Student stu = null;
        while(!name.equals("exit"))
        {
            for(Student s: students.keySet())
            {
                System.out.println(s.name);
            }
            while(stu == null && !name.equals("exit"))
            {
                name = readLine("Student ('exit' to return to class homepage): ");
                stu = Student.object(name);
            }
            if(stu != null)
            {
                students.get(stu).addScore(a, readInt("Score: "));
            }
        }
            
    }
    //user interative for editing an assignment
    public void editAssignment(Assignment a)
    {
        a.print();
        String action;
        action = readLine("Enter 'remove' to delete this assignment, 'points' to alter the number of points it's worth, 'due date' to alter the due date, 'category' to change the category of the assignment, 'name' to change the name, or 'exit' to return to assignment list:");
        while(!action.equals("exit"))
        {
            if(action.equals("remove"))
            {
                removeAssignment(a);
            }
            else if(action.equals("points"))
            {
                a.points = readInt("New point value: ");
            }
            else if(action.equals("due date"))
            {
                a.date = MyProgram.getDay("New due date: ");
            }
            else if(action.equals("category"))
            {
                System.out.println("CATEGORIES");
                for(AssignmentCategory cat: categories)
                {
                    System.out.println(cat.name + " (" + cat.weight + "%)");
                }
                String category = "";
                AssignmentCategory cat = null;
                while(cat == null)
                {
                    category = readLine("New category: ");
                    for(AssignmentCategory c: categories)
                    {
                        if(c.name.equals(category))
                        {
                            cat = c;
                        }
                    }
                }
                a.category = cat;
            }
            else if(action.equals("name"))
            {
                String name = "";
                Assignment assignment = null;
                while(assignment != null)
                {
                    assignment = null;
                    name = readLine("New name: ");
                    for(Assignment key: assignments)
                    {
                        if(key.name.equals(name))
                        {
                            assignment = key;
                        }
                    }
                }
                a.name = name;
            }
            action = readLine("Enter 'remove' to delete this assignment, 'points' to alter the number of points it's worth, 'due date' to alter the due date, 'category' to change the category of the assignment, 'name' to change the name, or 'exit' to return to assignment list:");
        }
    }
    //user interative for viewing student gradebooks
    public void viewGradebooks()
    {
        String n = "";
        Student stu = null;
        while(!name.equals("exit"))
        {
            for(Student key: students.keySet())
            {
                String name = key.name;
                while(name.length() % 20 != 0 && name.length() != 0)
                {
                    name += " ";
                }
                String percent = students.get(key).percent() + "%";
                while(percent.length() % 7 != 0 && percent.length() != 0)
                {
                    name += " ";
                }
                
                String line = "";
                line = name.substring(0, 20) + "   ";
                name = name.substring(20);
                line += Gradebook.grade(students.get(key).percent()) + "   ";
                line += percent.substring(0, 7) + "   ";
                percent = percent.substring(7);
                System.out.println(line);
                line = "";
                while(name.length()>0 || percent.length()>0)
                {
                    if(name.length()>0)
                    {
                        line = name.substring(0, 20);
                        name = name.substring(20);
                    }
                    else
                    {
                        for(int i=0; i<20; i++)
                        {
                            line += " ";
                        }
                    }
                    line += "   ";
                    line += " ";
                    line += "   ";
                    if(percent.length()>0)
                    {
                        line += percent.substring(0, 7);
                        percent = percent.substring(7);
                    }
                    else
                    {
                        for(int i=0; i<7; i++)
                        {
                            line += " ";
                        }
                    }
                    System.out.println(line);
                    line = "";
                }
            }
            while(!n.equals("exit") && stu == null)
            {
                n = readLine("Student ('exit' to return to class homepage): ");
                if(students.containsKey(Student.object(n)))
                {
                    stu = Student.object(n);
                }
                
            }
            if(stu != null)
            {
                students.get(stu).print();
                readLine("Press enter to return to gradebooks.");
                stu = null;
            }
        }
    }
    //returns Classroom with a certain name, null if none exists
    public static Classroom object(String name)
    {
        for(Classroom c: classes)
        {
            if(c.name.equals(name))
            {
                return c;
            }
        }
        return null;
    }
    //removes this from system
    public void remove()
    {
        teacher.removeClass(this);
        for(Student s: students.keySet())
        {
            removeStudent(s);
        }
        for(int i = classes.size()-1; i >= 0; i--)
        {
            if(this == classes.get(i))
            {
                classes.remove(i);
                break;
            }
        }
    }
}
