import java.util.ArrayList;
import java.util.*;
public class Student extends ConsoleProgram
{
    public int id;
    public String name;
    private HashMap<Classroom, Gradebook> grades;
    
    public static ArrayList<Student> students = new ArrayList<Student>();

    public Student(String name, int id)
    {
        this.id = id;
        this.name = name;
        grades = new HashMap<Classroom,Gradebook>();
        students.add(this);
    }
    //user input constructor
    public Student()
    {
        boolean found = true;
        while(found)
        {
            name = readLine("Name: ");
            found = false;
            for(Student s: students)
            {
                if(s.name.equals(name))
                {
                    found = true;
                }
            }
            if(found)
            {
                System.out.println("Student with this name is alread in the system.");
            }
        }
        id = readInt("ID Number: ");
        grades = new HashMap<Classroom,Gradebook>();
        students.add(this);
    }
    //called in the Classroom instance method addStudent
    public Gradebook addClass(Classroom c)
    {
        Gradebook res = new Gradebook();
        grades.put(c, res);
        return res;
    }
    //prints past/present classes with gpa
    public void printTranscript()
    {
        System.out.println("GPA: " + gpa());
        for(Classroom key: grades.keySet())
        {
            String name = key.name;
            while(name.length() % 20 != 0 || name.length() == 0)
            {
                name += " ";
            }
            String percent = grades.get(key).percent() + "%";
            while(percent.length() % 7 != 0 || percent.length() == 0)
            {
                percent += " ";
            }
            String teacher = key.teacher.name;
            while(teacher.length() % 20 != 0 || teacher.length() == 0)
            {
                teacher += " ";
            }
            
            String line = "";
            line += name.substring(0, 20) + "   ";
            name = name.substring(20);
            line += Gradebook.grade(grades.get(key).percent()) + "   ";
            line += percent.substring(0, 7) + "   ";
            percent = percent.substring(7);
            line += teacher.substring(0, 20);
            teacher = teacher.substring(20);
            System.out.println(line);
            line = "";
            while(name.length()>0 || percent.length()>0 || teacher.length()>0)
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
                line += "   ";
                if(teacher.length()>0)
                {
                    line = teacher.substring(0, 20);
                    teacher = teacher.substring(20);
                }
                System.out.println(line);
                line = "";
            }
        }
    }
    //prints gradebook summary
    public void printHomePage()
    {
        System.out.println();
        System.out.println("CLASSES");
        HashMap<Classroom, Gradebook> res = new HashMap<Classroom, Gradebook>();
        for(Classroom key: grades.keySet())
        {
            if(grades.get(key).active())
            {
                res.put(key, grades.get(key));
            }
        }
        for(Classroom key: res.keySet())
        {
            String n = key.name;
            while(n.length() % 20 != 0 || n.length() == 0)
            {
                n += " ";
            }
            String percent = grades.get(key).percent() + "%";
            while(percent.length() % 7 != 0 || percent.length() == 0)
            {
                percent += " ";
            }
            String teacher = key.teacher.name;
            while(teacher.length() % 20 != 0 || teacher.length() == 0)
            {
                teacher += " ";
            }
            
            String line = "";
            line += n.substring(0, 20) + "   ";
            n = n.substring(20);
            line += Gradebook.grade(grades.get(key).percent()) + "   ";
            line += percent.substring(0, 7) + "   ";
            percent = percent.substring(7);
            line += teacher.substring(0, 20);
            teacher = teacher.substring(20);
            System.out.println(line);
            line = "";
            while(n.length()>0 || percent.length()>0 || teacher.length()>0)
            {
                if(n.length()>0)
                {
                    line = n.substring(0, 20);
                    n = n.substring(20);
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
                line += "   ";
                if(teacher.length()>0)
                {
                    line += teacher.substring(0, 20);
                    teacher = teacher.substring(20);
                }
                System.out.println(line);
                line = "";
            }
        }
        System.out.println();
    }
    //returns true if name and id match
    public boolean successfulLogin(String name, int id)
    {
        return this.id == id && this.name.equals(name);
    }
    //calculates cumulative gpa
    public double gpa()
    {
        double sum = 0;
        for(Classroom key: grades.keySet())
        {
            sum += grades.get(key).gp();
        }
        return sum/grades.keySet().size();
    }
    //returns Student with a certain name, null if none exists
    public static Student object(String student)
    {
        for(Student s: students)
        {
            if(s.name.equals(student))
            {
                return s;
            }
        }
        return null;
    }
    //removes this from system
    public void remove()
    {
        for(int i = students.size()-1; i >= 0; i--)
        {
            if(this == students.get(i))
            {
                students.remove(i);
                break;
            }
        }
    }
    //the student role
    public void portal()
    {
        String action = "";
        printHomePage();
        while(!action.equals("exit"))
        {
            action = readLine("Enter name of class whose gradebook you wish to view, 'transcript' to view your transcript, or 'exit' to log out: ");
            if(action.equals("transcript"))
            {
                printTranscript();
                readLine("Press enter to return to your home page. ");
                printHomePage();
            }
            else if(grades.containsKey(Classroom.object(action)))
            {
                Classroom c = Classroom.object(action);
                System.out.println("GRADEBOOK: " + c.name);
                grades.get(c).print();
                readLine("Press enter to return to your home page. ");
                System.out.println();
                printHomePage();
            }
            
        }
    }
}
