import java.util.ArrayList;
public class MyProgram extends ConsoleProgram
{
    public static Day today = Day.MIN_DAY;
    public static final int colLim = 11;
    public static ConsoleProgram cp = new ConsoleProgram();
    
    public void run()
    {
        Administrator.password = cp.readLine("Admin password: ");
        String role = "";
        today = getDay("Enter today's date: ");
        while(true) //runs forever, there's no break statement
        {
            role = cp.readLine("Enter your role (admin, teacher or student): ");
            if(role.equals("admin") && cp.readLine("Enter admin password: ").equals(Administrator.password))
            {
                Administrator.portal();
                today = getDay("Enter today's date: ");
            }
            else if(role.equals("teacher"))
            {
                String name = "";
                Teacher t;
                while(!name.equals("exit"))
                {
                    name = cp.readLine("Enter your name (or 'exit' to reject this option): ");
                    t = Teacher.object(name);
                    if(t != null && t.successfulLogin(name, readInt("ID: ")))
                    {
                        t.portal();
                        break;
                    }
                }
                today = getDay("Enter today's date: ");
            }
            else if(role.equals("student"))
            {
                String name = "";
                Student s;
                while(!name.equals("exit"))
                {
                    name = cp.readLine("Enter your name (or 'exit' to reject this option): ");
                    s = Student.object(name);
                    if(s != null && s.successfulLogin(name, readInt("ID: ")))
                    {
                        s.portal();
                        break;
                    }
                }
                today = getDay("Enter today's date: ");
            }
        }
    }
    
    public static Day getDay(String prompt)
    {
        String str = cp.readLine(prompt);
        while(!Day.valid(str) || MyProgram.today.isAfter(new Day(str)))
        {
            str = cp.readLine(prompt);
        }
        return new Day(str);
    }
}
