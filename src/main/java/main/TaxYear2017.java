package main;


import entity.Person;
import entity.TaxPayer;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by SamkeDl on 12/07/2017.
 */
@Stateful
public class TaxYear2017 implements Serializable {
    public static TaxPayer calculateTax(Person person){
        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setIncome(person.getIncome());
        taxPayer.setTaxableIncome(person.getIncome());

        //tax before credits
        double taxBeforeCredits = getTaxRate(person.getIncome());
        taxPayer.setAnnualTaxBefore(taxBeforeCredits);
        taxPayer.setMonthlyTaxBefore(taxBeforeCredits/12);

        //tax rebates
        double rebates = getTaxCredits(person.getAge());
        taxPayer.setRebates(rebates);


        //tax threshold
        taxPayer.setThreshold(getTaxThresholds(person.getAge()));

        //tax after credits
        double taxAfterCredits = taxBeforeCredits - rebates;
        if(taxAfterCredits < taxPayer.getThreshold()){
            taxPayer.setAnnualTaxAfter(taxAfterCredits);
            taxPayer.setMonthlyTaxAfter(taxAfterCredits/12);
            taxPayer.setAnnualNetSalary(person.getIncome() - taxAfterCredits);
            taxPayer.setMonthlyNetSalary(taxPayer.getAnnualNetSalary()/12);

        }
        else{
            taxPayer.setAnnualTaxAfter(taxPayer.getThreshold());
            taxPayer.setMonthlyTaxAfter(taxPayer.getThreshold()/12);
            taxPayer.setAnnualNetSalary(taxPayer.getAnnualTaxBefore()- taxPayer.getThreshold());
            taxPayer.setMonthlyNetSalary(taxPayer.getAnnualNetSalary()/12);


        }



        return taxPayer;
    }
    private static double getTaxRate(int income){
        System.out.println("Calculating tax on "+income+" income");
        double tax = 0;
        if (income <= 188000) {
            tax = income * 0.18;
            System.out.println("TAX calculated on 18%");
        }
        else if (income>=188001 && income<=293600) {
            tax = 33840 + (income - 188000) * 0.26;
            System.out.println("TAX calculated on 26%");
        }
        else if (income>=293601 && income<=406400) {
            tax = 61296 + (income - 293600) * 0.31;
            System.out.println("TAX calculated on 31%");
        }
        else if (income>=406401 && income<=550100) {
            tax = 96264 + (income - 406400) * 0.36;
            System.out.println("TAX calculated on 36%");
        }
        else if (income>=550101 && income<=701300) {
            tax = 147996 + (income - 293600) * 0.39;
            System.out.println("TAX calculated on 39%");
        }
        else if (income>=701301) {
            tax = 206964 + (income - 701300) * 0.41;
            System.out.println("TAX calculated on 41%");
        }

        return tax;
    }

    private static int getTaxCredits(int age){
        final int PRIMARY = 13500;
        final int SECONDARY = 7407;
        final int TERTIARY = 2466;
        if(age <65)
            return PRIMARY;
        else if(age >=65 && age <75)
            return PRIMARY +SECONDARY;
        else if(age >= 75)
            return PRIMARY + TERTIARY;

        return 0;
    }

    private static int getTaxThresholds(int age){
        final int PRIMARY = 75000;
        final int SECONDARY = 116150;
        final int TERTIARY = 129850;
        if(age <65)
            return PRIMARY;
        else if(age >=65 && age <75)
            return SECONDARY;
        else if(age >= 75)
            return  TERTIARY;
        return 0;
    }

}
