import java.util.ArrayList;
public class Day extends ConsoleProgram
{
    private int m; //month
    private int d; //day
    private int y; //year
    
    //The earliest possible day entered into the
    public static final Day MIN_DAY = new Day(1, 1, 0);
    public static final int LEN = 10;
    
    public Day(int month, int day, int year)
    {
        m = month;
        d = day;
        y = year;
    }
    //readDate
    public Day(String date)
    {
        String month = date.substring(0, date.indexOf("/"));
        m = 0;
        for(int i = 0; i < month.length(); i++)
        {
            m = m*10 + ((int)month.charAt(i) - 48);
        }
        
        String day = date.substring(date.indexOf("/")+1);
        day = day.substring(0, day.indexOf("/"));
        d = 0;
        for(int i = 0; i < day.length(); i++)
        {
            d = d*10 + ((int)day.charAt(i) - 48);
        }
        
        String year = date.substring(date.indexOf("/")+1).substring(date.indexOf("/")+1);
        y = 0;
        for(int i = 0; i < year.length(); i++)
        {
            y = y*10 + ((int)year.charAt(i) - 48);
        }
    }
    
    //returns true if this is before other
    public boolean isBefore(Day other)
    {
        if(y < other.y)
        {
            return true;
        }
        else if(y == other.y)
        {
            if(m < other.m)
            {
                return true;
            }
            else if(m == other.m)
            {
                if(d < other.d)
                {
                    return true;
                }
            }
        }
        return false;
    }
    //returns true if this is after other
    public boolean isAfter(Day other)
    {
        if(y > other.y)
        {
            return true;
        }
        else if(y == other.y)
        {
            if(m > other.m)
            {
                return true;
            }
            else if(m == other.m)
            {
                if(d > other.d)
                {
                    return true;
                }
            }
        }
        return false;
    }
    //returns true if this is the same as other
    public boolean equals(Day other)
    {
        return this.toString().equals(other.toString());
    }
    //formats date with slashes
    public String toString()
    {
        return m + "/" + d + "/" + y;
    }
    //returns true if String date is a properly formatted day with slashes
    public static boolean valid(String date)
    {
        String slashes = new String(date);
        int s = 0;
        while(slashes.indexOf("/") != -1)
        {
            slashes = slashes.substring(slashes.indexOf("/")+1);
            s++;
        }
        if(s != 2)
        {
            return false;
        }
        
        String month = date.substring(0, date.indexOf("/"));
        int m = 0;
        for(int i = 0; i < month.length(); i++)
        {
            m = m*10 + ((int)month.charAt(i) - 48);
        }
        if(m < 1 || m  > 12)
        {
            return false;
        }
        
        String day = date.substring(date.indexOf("/")+1);
        day = day.substring(0, day.indexOf("/"));
        int d = 0;
        for(int i = 0; i < day.length(); i++)
        {
            d = d*10 + ((int)day.charAt(i) - 48);
        }
        if(d < 1 || d  > 12)
        {
            return false;
        }
        
        String year = date.substring(date.indexOf("/")+1).substring(date.indexOf("/")+1);
        if(year.length() <= 0)
        {
            return false;
        }
        for(int i = 0; i < year.length(); i++)
        {
            if(!Character.isDigit(year.charAt(i)))
            {
                return false;
            }
        }
        
        int[] days = {31,29,31,30,31,30,31,31,30,31,30,31};
        Day num = new Day(date);
        if(num.d == 0)
        {
            return false;
        }
        if(num.m == 2)
        {
            if(num.y % 4 == 0)
            {
                if(num.d > 29)
                {
                    return false;
                }
            }
            else
            {
                if(num.d > 28)
                {
                    return false;
                }
            }
        }
        else 
        {
            if(num.d > days[num.m-1])
            {
                return false;
            }
        }
        return true;
    }
}

