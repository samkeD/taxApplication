package main;

import entity.*;
import main.imp.TaxCalculator2017;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by SamkeDl on 10/07/2017.
 */
@Stateful
public class TaxCalculator implements Year,Serializable {
    public  TaxPayer findTax(int year,Person person,String incomeFrequency,int medicalAidDeduction){
        TaxPayer taxPayer = new TaxPayer() ;
                if (year==2017) {
                    TaxCalculator2017 taxCalculator2017 = new TaxCalculator2017();
                    taxPayer = taxCalculator2017.calculateTax(person, incomeFrequency, medicalAidDeduction);
                }
                else if(year == 2018) {
                    TaxCalculator2018 taxCalculator2018=new TaxCalculator2018();
                    taxPayer = taxCalculator2018.calculateTax(person, incomeFrequency, medicalAidDeduction);
                }
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




}
