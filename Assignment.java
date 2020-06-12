import java.util.ArrayList;
public class Assignment extends ConsoleProgram
{
    public String name;
    public int points;
    public Day date;
    public AssignmentCategory category;
    
    public static AssignmentCategory UNNASSIGNED = new AssignmentCategory("Uncategorized", 100);

    public Assignment(String n, int p, Day d)
    {
        name = n;
        points = p;
        date = d;
        category = UNNASSIGNED;
    }
    //user input construstor
    public Assignment()
    {
        name = readLine("Name: ");
        points = readInt("Points: ");
        date = MyProgram.getDay("Due date:");
        category = UNNASSIGNED;
    }
    //returns true if assignment is not worth points but only adds points to gradebook
    public boolean isExtraCredit()
    {
        return points == 0;
    }
    //called in Teacher instance method portal() and in Classroom instance method print()
    public void print()
    {
        String n = name;
        while(n.length() % MyProgram.colLim != 0 || n.length() == 0)
        {
            n += " ";
        }
        String c = category.name + " (" + category.weight + "%)";
        while(c.length() % MyProgram.colLim != 0)
        {
            c += " ";
        }
        String p = points + "";
        while(p.length() % 10 != 0 || p.length()==0)
        {
            p += " ";
        }
        String d = date + "";
        while(d.length() % 16 != 0 || d.length()==0)
        {
            d += " ";
        }
        String line = "";
        while(n.length() + p.length() + c.length() + d.length() > 0)
        {
            if(c.length() > 0)
            {
                line += c.substring(0, MyProgram.colLim);
                c = c.substring(MyProgram.colLim);
            }
            else
            {
                for(int i = 0; i < MyProgram.colLim; i++)
                {
                    line += " ";
                }
            }
            line += "   ";
            if(n.length() > 0)
            {
                line += n.substring(0, MyProgram.colLim);
                n = n.substring(MyProgram.colLim);
            }
            else
            {
                for(int i = 0; i < MyProgram.colLim; i++)
                {
                    line += " ";
                }
            }
            line += "   ";
            if(p.length() > 0)
            {
                line += p.substring(0,10);
                p = p.substring(10);
            }
            else
            {
                for(int i = 0; i < 10; i++)
                {
                    line += " ";
                }
            }
            line += "   ";
            if(d.length() > 0)
            {
                line += d.substring(0,16);
                d = d.substring(16);
            }
            
            System.out.println(line);
            line = "";
        }
    }
}
