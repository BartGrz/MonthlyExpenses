package pl.bg.javaMonthlyExpenses.dummy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class AbstractLambda<T,V> extends Abstract<T,V>
{
    private final Supplier<? extends T> supplier;
    private final Supplier<? extends V> suplier_sec;

    public AbstractLambda(Supplier<? extends T> supplier, Supplier<? extends V> suplier_sec)
    {
        this.supplier = supplier;
        this.suplier_sec = suplier_sec;
    }

    @Override
    public T getSomething_first() {
        return this.supplier.get();
    }

    @Override
    public V getSomething_second() {
        return this.suplier_sec.get();
    }
}
 abstract class Abstract<T,V>  {

    public abstract T getSomething_first();
     public abstract V getSomething_second();

}

class Main {


     public static void main(String[] args) {
          List<String> list_test_String = Arrays.asList("Gowno", "dupadupa","cycki");
          List<Double> list_test_Double = Arrays.asList(10.0,15.5,20.0,30.23);

       //  AbstractLambda<String,Record> a = new AbstractLambda<>(()->,()->)
       //  Logger.warn(a.getSomething_first() + " double = " + a.getSomething_second());


     }
     }
