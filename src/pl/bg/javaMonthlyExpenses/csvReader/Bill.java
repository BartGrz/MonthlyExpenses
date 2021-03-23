package pl.bg.javaMonthlyExpenses.csvReader;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {

    @CsvBindByPosition(position = 0)
    private double expense;
    @CsvBindByPosition(position = 2)
    private String category;
    @CsvBindByPosition(position = 1)
    private String note;
    @CsvBindByPosition(position = 3)
    private boolean common;

}
