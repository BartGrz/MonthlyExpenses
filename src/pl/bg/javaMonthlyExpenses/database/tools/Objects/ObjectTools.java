package pl.bg.javaMonthlyExpenses.database.tools.Objects;

import java.util.ArrayList;
import java.util.List;

public class ObjectTools {
    
    public static String findObjectType(Object val) {
        
        String result = val.getClass().getSimpleName();
        
        if (val.toString().contains("-")) {
            result = "date";
            return result;
        }
        return result;
    }
    
    public Object returnValue(List values) {
        
        Object obj;
        
        for (int i = 0; i < values.size(); i++) {
            
            obj = values.get(i);
            values.remove(i);
            return obj;
        }
        
        return null;
    }
    public List findTypeFromList(List test) {
        
        List list = new ArrayList();
        
        for (int i = 0; i < test.size(); i++) {
            list.add(test.get(i).getClass().getSimpleName());
        }
        
        return list;
    }
    
}
