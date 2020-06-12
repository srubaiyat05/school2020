import java.util.ArrayList;
import java.util.*;
public class Gradebook
{
    private ArrayList<AssignmentEntry> entries;
    private boolean active;
    
    public Gradebook()
    {
        entries = new ArrayList<AssignmentEntry>();
        active = true;
    }
    //getter method for instance variable active
    public boolean active()
    {
        return active;
    }
    //sets active to false
    public void deactivate()
    {
        active = false;
    }
    public void addAssignment(Assignment a)
    {
        int index = 0;
        while(index < entries.size() && entries.get(index).assignment.date.isBefore(a.date))
        {
            index++;
        }
        entries.add(index, new AssignmentEntry(a));
    }
    public void removeAssignment(Assignment a)
    {
        for(int i = entries.size()-1; i >= 0; i--)
        {
            if(entries.get(i).assignment == a)
            {
                entries.remove(i);
                break;
            }
        }
    }
    public void addScore(Assignment a, int s)
    {
        for(AssignmentEntry e: entries)
        {
            if(a == e.assignment)
            {
                e.score = s;
            }
        }
    }
    //helper method for catWorth() and catPoints()
    public HashMap<AssignmentCategory, Integer> categories()
    {
        HashMap<AssignmentCategory, Integer> res = new HashMap<AssignmentCategory, Integer>();
        for(AssignmentEntry e: entries)
        {
            if(!res.containsKey(e.assignment.category))
            {
                res.put(e.assignment.category, 0);
            }
        }
        return res;
    }
    //returns HashMap of AssignmentCategory objects and their corresponding total point value
    public HashMap<AssignmentCategory, Integer> catWorth()
    {
        HashMap<AssignmentCategory, Integer> res = categories();
        for(AssignmentEntry e: entries)
        {
            if(e.score > 0 || e.assignment.date.isBefore(MyProgram.today))
            {
                AssignmentCategory cat = e.assignment.category;
                res.put(cat, res.get(cat) + e.assignment.points);
            }
        }
        return res;
    }
    //returns HashMap of AssignmentCategory objects and their corresponding points earned
    public HashMap<AssignmentCategory, Integer> catPoints()
    {
        HashMap<AssignmentCategory, Integer> res = categories();
        for(AssignmentEntry e: entries)
        {
            AssignmentCategory cat = e.assignment.category;
            res.put(cat, res.get(cat) + e.score);
        }
        return res;
    }
    //returns HashMap of AssignmentCategory objects and their corresponding percent
    public HashMap<AssignmentCategory, Double> catPercents()
    {
        HashMap<AssignmentCategory, Double> res = new HashMap<AssignmentCategory, Double>();
        HashMap<AssignmentCategory, Integer> scores = catPoints();
        HashMap<AssignmentCategory, Integer> totals = catWorth();
        for(AssignmentCategory cat: categories().keySet())
        {
            double p = 100;
            if(totals.get(cat) > 0)
            {
                p = (double)scores.get(cat)/(double)totals.get(cat);
                p *= 100;
                p = (double) Math.round(p*100);
                p /= 100;
            }
            res.put(cat, p);
        }
        return res;
    }
    //returns total percentage for this class
    public double percent()
    {
        double res = 0;
        HashMap<AssignmentCategory, Double> p = catPercents();
        for(AssignmentCategory cat: p.keySet())
        {
            res += p.get(cat)*cat.weight;
        }
        return res/100;
    }
    //returns letter grade based on percentage
    public static String grade(double percent)
    {
        if(percent >= 90)
        {
            return "A";
        }
        else if(percent >= 80)
        {
            return "B";
        }
        else if(percent >= 70)
        {
            return "C";
        }
        else if(percent >= 60)
        {
            return "D";
        }
        else
        {
            return "F";
        }
    }
    //returns grade points, to calculate gpa
    public double gp()
    {
        double percent = percent();
        if(percent >= 90)
        {
            return 4;
        }
        else if(percent >= 80)
        {
            return 3;
        }
        else if(percent >= 70)
        {
            return 2;
        }
        else if(percent >= 60)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    //printing gradebook detail breakdown
    public void print()
    {
        System.out.println("CATEGORY BREAKDOWN: ");
        System.out.println();
        System.out.println("CATEGORY          GRADE PERCENT      POINTS");
        for(AssignmentCategory cat: categories().keySet())
        {
            String name = cat.name + "(" + cat.weight + "%)";
            while(name.length() % 17 != 0)
            {
                name += " ";
            }
            int score = catPoints().get(cat);
            int total = catWorth().get(cat);
            String percent = catPercents().get(cat) + "%";
            while(percent.length() % 10 != 0)
            {
                percent += " ";
            }
            
            String line = name.substring(0, 17) + "   ";
            line = line + grade(catPercents().get(cat)) + "   ";
            line = line + percent.substring(0, 10) + "   ";
            line = line + score + "/" + total;
            System.out.println(line);
            name = name.substring(17);
            percent = percent.substring(10);
            line = "";
            while(name.length() > 0 || percent.length() > 0)
            {
                if(name.length() > 0)
                {
                    line = name.substring(0, 17) + "   ";
                    name = name.substring(17);
                }
                else
                {
                    for(int i = 0; i < 17; i++)
                    {
                        line += " ";
                    }
                    line += "   ";
                }
                line = line + " " + "   ";
                if(percent.length() > 0)
                {
                    line = line + percent.substring(0, 10);
                    percent = percent.substring(10);
                }
                System.out.println(line);
                line = "";
            }
        }
        System.out.println();
        System.out.println("ASSIGNMENT LIST:");
        System.out.println();
        System.out.println("CATEGORY      ASSIGNMENT    SCORE      PERCENT  DUE DATE  ");
        for(AssignmentEntry e: entries)
        {
            e.print();
        }
    }
}
