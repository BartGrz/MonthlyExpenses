package pl.bg.javaMonthlyExpenses.csvReader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class CsvReader {
    public List<Bill> csvReader () throws FileNotFoundException {

        FileReader fileReader = new FileReader(System.getProperty("user.home")+"\\Desktop\\paragon_auchan_02.02.csv");

        CsvToBean<Bill> csvToBean = new CsvToBeanBuilder<Bill>(fileReader)
                .withSeparator(';')
                .withSkipLines(1)
                .withType(Bill.class)
                .build();


        return csvToBean.parse();

    }
}
