public class Administrator extends ConsoleProgram
{
    public static String password;
    private static ConsoleProgram a = new ConsoleProgram(); //used to call readLine on
    
    public static void setPassword()
    {
        password = a.readLine("NEW PASSWORD: ");
        System.out.println("PASSWORD RESET");
    }
    public static void addStudent()
    {
        Student s = new Student();
        System.out.println("STUDENT ADDED");
    }
    public static void editStudent() 
    {
        String name = "";
        Student s;
        while(!name.equals("exit"))
        {
            s = null;
            while(!name.equals("exit") && s == null)
            {
                name = a.readLine("Enter name of student (or 'exit' to reject this option): ");
                s = Student.object(name);
            }
            if(s != null)
            {
                System.out.println(s.name + "(" + s.id +")");
                String action = "";
                while(!action.equals("exit"))
                {
                    action = a.readLine("Enter 'name' to alter this student's name, 'id' to alter their id, 'remove' to remove them, or 'exit' to return to the previous option: ");
                    if(action.equals("name"))
                    {
                        String n = a.readLine("New name: ");
                        while(Student.object(n) != null)
                        {
                             n = a.readLine("New name: ");
                        }
                        s.name = n;
                        System.out.println(s.name + "(" + s.id +")");
                    }
                    else if(action.equals("id"))
                    {
                        s.id = a.readInt("New id: ");
                        System.out.println(s.name + "(" + s.id +")");
                    }
                    else if(action.equals("remove"))
                    {
                        s.remove();
                        System.out.println("STUDENT REMOVED");
                    }
                }
                name = a.readLine("Enter 'exit' to return to the previous option, or anything else to edit other students: ");
            }
        }
    }
    public static void addTeacher()
    {
        Teacher t = new Teacher();
        System.out.println("TEACHER ADDED");
    }
    public static void editTeacher() 
    {
        String name = "";
        Teacher t;
        while(!name.equals("exit"))
        {
            t = null;
            while(!name.equals("exit") && t == null)
            {
                name = a.readLine("Enter name of teacher (or 'exit' to reject this option): ");
                t = Teacher.object(name);
            }
            if(t != null)
            {
                System.out.println(t.name + "(" + t.id +")");
                String action = "";
                while(!action.equals("exit"))
                {
                    action = a.readLine("Enter 'name' to alter this teacher's name, 'id' to alter their id, 'remove' to remove them, or 'exit' to return to the previous option: ");
                    if(action.equals("name"))
                    {
                        String n = a.readLine("New name: ");
                        while(Teacher.object(n) != null)
                        {
                             n = a.readLine("New name: ");
                        }
                        t.name = n;
                        System.out.println(t.name + "(" + t.id +")");
                    }
                    else if(action.equals("id"))
                    {
                        t.id = a.readInt("New id: ");
                        System.out.println(t.name + "(" + t.id +")");
                    }
                    else if(action.equals("remove"))
                    {
                        t.remove();
                        System.out.println("TEACHER REMOVED");
                    }
                }
                name = a.readLine("Enter 'exit' to return to the previous option, or anything else to edit other teachers: ");
            }
                
        }   
    }
    public static void addClass()
    {
        Classroom c = new Classroom();
        System.out.println("CLASS ADDED");
    }
    public static void editClass()
    {
        String name = "";
        Classroom c = null;
        while(!name.equals("exit"))
        {
            c = null;
            if(name.equals("exit"))
            {
                break;
            }
            while(!name.equals("exit") && c == null)
            {
                name = a.readLine("Enter class name (or 'exit' to reject this option): ");
                c = Classroom.object(name);
            }
            if(c != null)
            {
                System.out.println(c.name + "(" + c.teacher.name +")");
                String action = "";
                while(true)
                {
                    action = a.readLine("Enter 'name' to alter this class's name, 'teacher' to change its teacher, 'add students' to add students, 'remove students' to remove students, 'remove' to remove it, or 'exit' to return to the previous option: ");
                    if(action.equals("exit"))
                    {
                        break;
                    }
                    else if(action.equals("name"))
                    {
                        String n = "";
                        Classroom cl = null;
                        while(c != null)
                        {
                            n = a.readLine("New name: ");
                            cl = Classroom.object(n);
                        }
                        c.name = n;
                        System.out.println(c.name + "(" + c.teacher.name +")");
                    }
                    else if(action.equals("remove"))
                    {
                        c.remove();
                        System.out.println("CLASS REMOVED");
                    }
                    else if(action.equals("teacher"))
                    {
                        String n = "";
                        c.teacher.removeClass(c);
                        Teacher t = null;
                        while(t == null)
                        {
                            n = a.readLine("New teacher: ");
                            t = Teacher.object(n);
                        }
                        c.teacher = t;
                        t.addClass(c);
                        System.out.println(c.name + "(" + c.teacher.name +")");
                    }
                    else if(action.equals("add students"))
                    {
                        System.out.println("STUDENTS");
                        for(Student s: c.students.keySet())
                        {
                            System.out.println(s.name);
                        }
                        String n = "";
                        Student s = null;
                        while(s == null)
                        {
                            n = a.readLine("New student: ");
                            s = Student.object(n);
                        }
                        if(!c.students.containsKey(s))
                        {
                            c.addStudent(s);
                        }
                        System.out.println("STUDENTS");
                        for(Student stu: c.students.keySet())
                        {
                            System.out.println(stu.name);
                        }
                    }
                    else if(action.equals("remove students"))
                    {
                        System.out.println("STUDENTS");
                        for(Student s: c.students.keySet())
                        {
                            System.out.println(s.name);
                        }
                        String n = "";
                        Student s = null;
                        while(s == null)
                        {
                            n = a.readLine("Student: ");
                            s = Student.object(n);
                        }
                        if(c.students.containsKey(s))
                        {
                            c.removeStudent(s);
                        }
                        System.out.println("STUDENTS");
                        for(Student stu: c.students.keySet())
                        {
                            System.out.println(stu.name);
                        }
                    }
                }
            }
        }
    }
    public static void viewStudents()
    {
        System.out.println("STUDENTS");
        for(Student s: Student.students)
        {
            System.out.println(s.name);
        }
    }
    public static void viewTeachers()
    {
        System.out.println("TEACHERS");
        for(Teacher t: Teacher.teachers)
        {
            System.out.println(t.name);
        }
    }
    public static void viewClasses()
    {
        String action = "";
        System.out.println("CLASSES");
        for(Classroom c: Classroom.classes)
        {
            System.out.println(c.name + "(" + c.teacher.name +")");
        }
        while(!action.equals("exit"))
        {
            action = a.readLine("Enter name of class whose roster you wish to view (or exit to reject this option): ");
            if(Classroom.object(action) != null)
            {
                System.out.println("STUDENTS");
                for(Student s: Classroom.object(action).students.keySet())
                {
                    System.out.println(s.name);
                }
                a.readLine("Press enter to return to the classes homepage.");
                System.out.println("CLASSES");
                for(Classroom c: Classroom.classes)
                {
                    System.out.println(c.name + "(" + c.teacher.name +")");
                }
            }
        }
        
        
    }
    //the Administrator role
    public static void portal()
    {
        String action = "";
        while(!action.equals("exit"))
        {
            action = a.readLine("Enter 'pw' to reset the admin password, 'add' to add any students, teachers or classes to the system, 'edit' to edit any teachers, students or classes, 'view' to view a list of teachers, students or classes, or 'exit' if you would like to log out: ");
            if(action.equals("pw"))
            {
                setPassword();
            }
            else if("add".equals(action))
            {
                String role = "";
                while(!role.equals("exit"))
                {
                    role = a.readLine("Enter 'students' to add students, 'teachers' to add teachers, 'classes' to add classes, or 'exit' to reject this option: ");
                    if(role.equals("students"))
                    {
                        addStudent();
                    }
                    else if(role.equals("teachers"))
                    {
                        addTeacher();
                    }
                    else if(role.equals("classes"))
                    {
                        addClass();
                    }
                }
            }
            else if("edit".equals(action))
            {
                String role = "";
                while(!role.equals("exit"))
                {
                    role = a.readLine("Enter 'students' to edit students, 'teachers' to edit teachers, 'classes' to edit classes, or 'exit' to reject this option: ");
                    if(role.equals("students"))
                    {
                        editStudent();
                    }
                    else if(role.equals("teachers"))
                    {
                        editTeacher();
                    }
                    else if(role.equals("classes"))
                    {
                        editClass();
                    }
                }
            }
            else if("view".equals(action))
            {
                String role = "";
                while(!role.equals("exit"))
                {
                    role = a.readLine("Enter 'students' to view students, 'teachers' to view teachers, 'classes' to view classes, or 'exit' to reject this option: ");
                    if(role.equals("students"))
                    {
                        System.out.println();
                        viewStudents();
                        System.out.println();
                    }
                    else if(role.equals("teachers"))
                    {
                        System.out.println();
                        viewTeachers();
                        System.out.println();
                    }
                    else if(role.equals("classes"))
                    {
                        System.out.println();
                        viewClasses();
                        System.out.println();
                    }
                }
            }
        }
    }
}
