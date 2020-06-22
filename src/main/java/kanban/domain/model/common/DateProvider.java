package kanban.domain.model.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateProvider {
    private static Date date = null;

    public static Date now(){
        if(null == date)
            return new Date();
        else
            return date;
    }

    public static void setDate(Date now)
    {
        date = now;
    }

    public static void setDate(String date) throws ParseException {
        Date _date = DateFormat.getDateInstance().parse(date);
        setDate(_date);
    }

    public static void resetDate()
    {
        date = null;
    }
}
