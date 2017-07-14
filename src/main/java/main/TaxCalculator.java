package main;

import entity.*;

import javax.ejb.Stateless;
import java.io.Serializable;


/**
 * Created by SamkeDl on 10/07/2017.
 */
@Stateless
public class TaxCalculator implements Year,Serializable {
    public static TaxPayer findTax(Integer year,Person person){
        TaxPayer taxPayer = new TaxPayer() ;
                if (year.equals(2017))
                 taxPayer  = TaxYear2017.calculateTax(person);
                else if(year == 2018)
                    taxPayer = TaxYear2018.calculateTax(person);
                else
                    System.out.println("No TAX information for year "+year);

         System.out.println("Taxable income: " +person.getIncome());
                System.out.println("Tax before credits "+taxPayer.getAnnualTaxBefore()+"(annually)");
                System.out.println("Tax before credits "+taxPayer.getMonthlyTaxBefore()+"(monthly)");

                System.out.println("Tax credits "+taxPayer.getRebates());

                System.out.println("Tax after credits "+taxPayer.getAnnualTaxAfter()+"(annually)");
                System.out.println("Tax after credits "+taxPayer.getMonthlyTaxAfter()+"(monthly)");

                System.out.println("net salary  "+taxPayer.getAnnualNetSalary()+"(annually)");
                System.out.println("net salary  "+taxPayer.getMonthlyNetSalary()+"(monthly)");

        return taxPayer;
        }

    public static void main(String [] s){
        Person person = new TaxPayer() ;

        int income = 360000;
        int age = 66;
        int year = 2017;
        person.setIncome(income);
        person.setAge(age);


    }

}
