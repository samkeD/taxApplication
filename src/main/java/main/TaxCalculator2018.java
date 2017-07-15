package main;

import entity.Person;
import entity.TaxPayer;

import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Created by SamkeDl on 15/07/2017.
 */
@Stateful
public class TaxCalculator2018 implements Calculator,Serializable {
    @Override
    public double calculateTaxableIncome(double income, int medicalAidDeduction) {
        return income-medicalAidDeduction;
    }

    @Override
    public double calculateTaxRate(double taxableIncome) {
        System.out.println("Calculating tax on "+taxableIncome+" income");
        double tax = 0;
        if (taxableIncome <= 189880) {
            tax = taxableIncome * 0.18;
            System.out.println("TAX calculated on 18%");
        }
        else if (taxableIncome>=189881 && taxableIncome<=296540) {
            tax = 34178 + (taxableIncome - 189880) * 0.26;
            System.out.println("TAX calculated on 26%");
        }
        else if (taxableIncome>=296541 && taxableIncome<=410460) {
            tax = 61910 + (taxableIncome - 296540) * 0.31;
            System.out.println("TAX calculated on 31%");
        }
        else if (taxableIncome>=410461 && taxableIncome<=555600) {
            tax = 97225 + (taxableIncome - 410460) * 0.36;
            System.out.println("TAX calculated on 36%");
        }
        else if (taxableIncome>=555601 && taxableIncome<=708310) {
            tax = 149475 + (taxableIncome - 555600) * 0.39;
            System.out.println("TAX calculated on 39%");
        }
        else if (taxableIncome>=708311 && taxableIncome<=1500000) {
            tax = 209032 + (taxableIncome - 708310) * 0.41;
            System.out.println("TAX calculated on 41%");
        }
        else if(taxableIncome>=1500001){
            tax = 533625 + (taxableIncome - 1500000) * 0.45;
            System.out.println("TAX calculated on 41%");
        }
        return tax;
    }

    @Override
    public int calculateTaxCredits(int age) {
        if(age < AGE65){
            int newPrimary = (int)(PRIMARY_REBATE*0.01)+PRIMARY_REBATE;
            System.out.println("2018 PRIMARY REBATE = "+newPrimary);
            return (int)(PRIMARY_REBATE*0.01)+PRIMARY_REBATE ;
        }
        else if (age >= AGE65 && age < AGE75) {
            int newPrimary = (int) (PRIMARY_REBATE * 0.01) + PRIMARY_REBATE + (int) (SECONDARY_REBATE * 0.01) + SECONDARY_REBATE;//(int)(PRIMARY_REBATE*0.01)+PRIMARY_REBATE;
            System.out.println("2018 PRIMARY REBATE = "+newPrimary);

            return (int) (PRIMARY_REBATE * 0.01) + PRIMARY_REBATE + (int) (SECONDARY_REBATE * 0.01) + SECONDARY_REBATE;
        }
        else if (age >= AGE75) {
            int newPrimary = (int) (PRIMARY_REBATE * 0.01) + PRIMARY_REBATE + (int) (TERTIARY_REBATE * 0.01) + TERTIARY_REBATE;
            System.out.println("2018 PRIMARY REBATE = "+newPrimary);
            return (int) (PRIMARY_REBATE * 0.01) + PRIMARY_REBATE + (int) (TERTIARY_REBATE * 0.01) + TERTIARY_REBATE;
        }
        else
            return 0;
    }

    @Override
    public int calculateTaxThreshold(int age) {
        if(age < AGE65)
            return (int)(PRIMARY_THRESHOLD*0.01) + PRIMARY_THRESHOLD;
        else if(age >= AGE65 && age < AGE75)
            return (int)(SECONDARY_THRESHOLD*0.01) + SECONDARY_THRESHOLD;
        else if (age >= AGE75)
            return (int)(TERTIARY_THRESHOLD*0.01) +TERTIARY_THRESHOLD;
        else
            return 0;
    }

    @Override
    public int calculateMedicalAidCredits(int members) {
        if(members>0) {
            int sum = ((int) (MEMBER * 0.01) + MEMBER)*12;
            if (members >= 2)
                sum += ((int) (FIRST_DEPENDENT * 0.01) + FIRST_DEPENDENT)*12;
            for (int i = 3; i <= members; i++)
                sum += ((int) (ADDITIONAL_DEPENTANT * 0.01) + ADDITIONAL_DEPENTANT)*12;
            return sum;
        }
        else
            return 0;
    }

    public TaxPayer calculateTax (Person person, String incomeFrequency, int medicalAidDeduction){
        TaxCalculator2018 taxCalculator2018 = new TaxCalculator2018();
        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setIncome(person.getIncome());
        taxPayer.setMedicalAid(taxCalculator2018.calculateMedicalAidCredits(medicalAidDeduction));
        taxPayer.setTaxableIncome(taxCalculator2018.calculateTaxableIncome(taxPayer.getIncome(),taxPayer.getMedicalAid()));

        //tax before credits
        double taxBeforeCredits = taxCalculator2018.calculateTaxRate(taxPayer.getTaxableIncome());
        taxPayer.setAnnualTaxBefore(taxBeforeCredits);
        taxPayer.setMonthlyTaxBefore(taxBeforeCredits/12);
        taxPayer.setTaxBeforeCredits(taxBeforeCredits);

        //tax rebates
        double rebates = taxCalculator2018.calculateTaxCredits(person.getAge());
        taxPayer.setRebates(rebates);


        //tax threshold
        taxPayer.setThreshold(taxCalculator2018.calculateTaxThreshold(person.getAge()));

        //tax after credits
        double taxAfterCredits = taxBeforeCredits - rebates;
        if(taxAfterCredits < taxPayer.getThreshold()){
            taxPayer.setAnnualTaxAfter(taxAfterCredits);
            taxPayer.setMonthlyTaxAfter(taxAfterCredits/12);
            taxPayer.setAnnualNetSalary(person.getIncome() - taxAfterCredits);
            taxPayer.setMonthlyNetSalary(taxPayer.getAnnualNetSalary()/12);

            taxPayer.setTaxAfterCredits(taxAfterCredits);
            taxPayer.setNetIncome(taxPayer.getAnnualNetSalary());
        }
        else{
            taxPayer.setAnnualTaxAfter(taxPayer.getThreshold());
            taxPayer.setMonthlyTaxAfter(taxPayer.getThreshold()/12);
            taxPayer.setAnnualNetSalary(person.getIncome()- taxPayer.getThreshold());
            taxPayer.setMonthlyNetSalary(taxPayer.getAnnualNetSalary()/12);
            //annuals
            taxPayer.setTaxAfterCredits(taxPayer.getThreshold());
            taxPayer.setNetIncome(taxPayer.getAnnualNetSalary());
        }
        if(incomeFrequency.equals("Monthly")){
            taxPayer.setTaxBeforeCredits(taxPayer.getTaxBeforeCredits()/12);
            taxPayer.setTaxAfterCredits(taxPayer.getTaxAfterCredits()/12);
            taxPayer.setNetIncome(taxPayer.getNetIncome()/12);
            taxPayer.setRebates(taxPayer.getRebates()/12);
        }



        return taxPayer;
    }
}
