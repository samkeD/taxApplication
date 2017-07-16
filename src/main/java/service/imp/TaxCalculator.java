package service.imp;

import model.*;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by SamkeDl on 10/07/2017.
 */
@Stateless
public class TaxCalculator implements Serializable {
    public  TaxPayer findTax(int year,Person person,String incomeFrequency,int medicalAidDeduction){
        TaxPayer taxPayer = new TaxPayer() ;
        taxPayer.setAge(person.getAge());
        if (year==2017) {
            TaxCalculator2017 taxCalculator2017 = new TaxCalculator2017();
            taxPayer = taxCalculator2017.calculateTax(person, incomeFrequency, medicalAidDeduction);
        }
        else if(year == 2018) {
            TaxCalculator2018 taxCalculator2018 = new TaxCalculator2018();
            taxPayer = taxCalculator2018.calculateTax(person, incomeFrequency, medicalAidDeduction);
        }
        else
            System.out.println("No TAX information for year "+year);

        return taxPayer;
    }




}
