import java.util.ArrayList;
import java.util.*;
public class Teacher extends ConsoleProgram
{
    public int id; //password
    public String name;
    private ArrayList<Classroom> classes; //classes they teach
    
    public static ArrayList<Teacher> teachers = new ArrayList<Teacher>(); //all teachers curently in the system
    
    public Teacher(String name, int id)
    {
        this.name = name;
        this.id = id;
        classes = new ArrayList<Classroom>();
        teachers.add(this);
    }
    //user input constructor
    public Teacher()
    {
        boolean found = true;
        while(found)
        {
            name = readLine("Name: ");
            found = false;
            for(Teacher t: teachers)
            {
                if(t.name.equals(name))
                {
                    found = true;
                }
            }
            if(found)
            {
                System.out.println("Teacher with this name is alread in the system.");
            }
        }
        id = readInt("ID Number: ");
        classes = new ArrayList<Classroom>();
        teachers.add(this);
    }
    //returns true if name and id match
    public boolean successfulLogin(String name, int id)
    {
        return this.id == id && this.name.equals(name);
    }
    //called in the Classroom constructor or editClass
    public void addClass(Classroom c)
    {
        classes.add(c);
    }
    //called in Administrator.editClass() and Classroom instance method remove() 
    public void removeClass(Classroom c)
    {
        for(int i = classes.size()-1; i >= 0; i--)
        {
            classes.remove(i);
            break;
        }
    }
    //returns teacher object with a certain name, or null if none exists
    public static Teacher object(String teacher)
    {
        for(Teacher t: teachers)
        {
            if(t.name.equals(teacher))
            {
                return t;
            }
        }
        return null;
    }
    //removes this from system
    public void remove()
    {
        for(int i = teachers.size()-1; i >= 0; i--)
        {
            if(this == teachers.get(i))
            {
                teachers.remove(i);
                break;
            }
        }
    }
    //prints all classes they teach
    public void printHomePage()
    {
        System.out.println();
        System.out.println("CLASSES");
        for(Classroom c: classes)
        {
            System.out.println(c.name);
        }
        System.out.println();
    }
    //the teacher role
    public void portal()
    {
        String action = "";
        Classroom c = null;
        while(!action.equals("exit"))
        {
            printHomePage();
            action = readLine("Enter the name of the class you wish to view, or 'exit' to log out: ");
            while(!action.equals("exit") && Classroom.object(action) == null)
            {
                action = readLine("Enter the name of the class you wish to view, or 'exit' to log out: ");
            }
            c = Classroom.object(action);
            if(c != null)
            {
                String action2 = "";
                c.print();
                while(!action2.equals("exit"))
                {
                    action2 = readLine("Enter 'edit' to alter an assignment, 'score' to input scores, 'add assignment' to add an assignment, 'view' to view student gradebooks, or 'exit' to return to home page: ");
                    if(action2.equals("edit"))
                    {
                        Assignment a = null;
                        String name = "";
                        while(!name.equals("exit"))
                        {
                            while(a == null && !name.equals("exit"))
                            {
                                name = readLine("Enter the name of the assignment you wish to alter, or 'exit' to log out: ");
                                a = c.getAssignment(name);
                            }
                            if(a != null)
                            {
                                c.editAssignment(a);
                                a = null;
                                readLine("Press enter to return to your home page. ");
                                c.print();
                            }
                        }
                    }
                    else if(action2.equals("score"))
                    {
                        Assignment a = null;
                        String name = "";
                        while(!name.equals("exit"))
                        {
                            while(a == null && !name.equals("exit"))
                            {
                                name = readLine("Enter the name of the assignment you wish to score, or 'exit' to log out: ");
                                a = c.getAssignment(name);
                            }
                            if(a != null)
                            {
                                c.scoreAssignment(a);
                                a = null;
                                readLine("Press enter to return to your home page. ");
                                c.print();
                            }
                        }
                    }
                    else if(action2.equals("add assignment"))
                    {
                        c.addAssignment(new Assignment());
                        readLine("Press enter to return to your home page. ");
                        c.print();
                    }
                    else if(action2.equals("view"))
                    {
                        c.viewGradebooks();
                        readLine("Press enter to return to your home page. ");
                        c.print();
                    }
                }
            }
        }
    }
}
