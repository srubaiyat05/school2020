import java.util.ArrayList;
public class AssignmentEntry
{
    public Assignment assignment;
    public int score;
    
    public static final int MISSING = 0; //or not due yet
    public static final int scoreCol = 8;
    public static final int percentCol = 6;
    
    public AssignmentEntry(Assignment a)
    {
        assignment = a;
        score = MISSING;
    }
    //called in Gradebook instance method print()
    public void print()
    {
        String cat = assignment.category.name + "(" + assignment.category.weight + "%)";
        while(cat.length() % MyProgram.colLim != 0 || cat.length()==0)
        {
            cat += " ";
        }
        String name = assignment.name;
        while(name.length() % MyProgram.colLim != 0 || name.length()==0)
        {
            name += " ";
        }
        
        String s;
        if(assignment.date.isAfter(MyProgram.today) && score == MISSING)
        {
            s = "-";
        }
        else
        {
            s = score + "";
        }
        s += "/";
        if(assignment.isExtraCredit())
        {
            s += "(Extra Credit)";
        }
        else
        {
            s += assignment.points;
        }
        while(s.length() % scoreCol != 0)
        {
            s += " ";
        }
        
        String p;
        if(assignment.isExtraCredit())
        {
            p = "100%";
        }
        else if(assignment.date.isAfter(MyProgram.today) && score == MISSING)
        {
            p = "-";
        }
        else
        {
            double res = (double)score/(double)assignment.points;
            res *= 100;
            res = (double) Math.round(res*100);
            res /= 100;
            p = res + "%";
        }
        
        while(p.length() % percentCol != 0)
        {
            p += " ";
        }
        String d = assignment.date.toString();
        while(d.length() % Day.LEN != 0)
        {
            d += " ";
        }
        
        String line = "";
        while(cat.length() > 0 || name.length() > 0 || s.length() > 0 || p.length() > 0 || d.length() > 0)
        {
            if(cat.length() > 0)
            {
                line = cat.substring(0, MyProgram.colLim) + "   ";
                cat = cat.substring(MyProgram.colLim);
            }
            else
            {
                for(int i = 0; i < MyProgram.colLim; i++)
                {
                    line += " ";
                }
                line += "   ";
            }
            if(name.length() > 0)
            {
                line += name.substring(0, MyProgram.colLim) + "   ";
                name = name.substring(MyProgram.colLim);
            }
            else
            {
                for(int i = 0; i < MyProgram.colLim; i++)
                {
                    line += " ";
                }
                line += "   ";
            }
            if(s.length() > 0)
            {
                line += s.substring(0, scoreCol) + "   ";
                s = s.substring(scoreCol);
            }
            else
            {
                for(int i = 0; i < scoreCol; i++)
                {
                    line += " ";
                }
                line += "   ";
            }
            if(p.length() > 0)
            {
                line += p.substring(0, percentCol) + "   ";
                p = p.substring(percentCol);
            }
            else
            {
                for(int i = 0; i < percentCol; i++)
                {
                    line += " ";
                }
                line += "   ";
            }
            if(d.length() > 0)
            {
                line += d.substring(0, Day.LEN);
                d = d.substring(Day.LEN);
            }
            System.out.println(line);
            line = "";
        }
    }
}

