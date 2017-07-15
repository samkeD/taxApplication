package main.imp;

import entity.Person;
import entity.TaxPayer;
import main.Calculator;

import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Created by SamkeDl on 15/07/2017.
 */
@Stateful
public class TaxCalculator2017 implements Calculator,Serializable {
    @Override
    public double calculateTaxableIncome(double income,int medicalAidDeduction) {
        return income-medicalAidDeduction;
    }

    @Override
    public double calculateTaxRate(double taxableIncome) {
            System.out.println("Calculating tax on "+taxableIncome+" taxableIncome");
            double tax = 0;
            if (taxableIncome <= 188000) {
                tax = taxableIncome * 0.18;
                System.out.println("TAX calculated on 18%");
            }
            else if (taxableIncome>=188001 && taxableIncome<=293600) {
                tax = 33840 + (taxableIncome - 188000) * 0.26;
                System.out.println("TAX calculated on 26%");
            }
            else if (taxableIncome>=293601 && taxableIncome<=406400) {
                tax = 61296 + (taxableIncome - 293600) * 0.31;
                System.out.println("TAX calculated on 31%");
            }
            else if (taxableIncome>=406401 && taxableIncome<=550100) {
                tax = 96264 + (taxableIncome - 406400) * 0.36;
                System.out.println("TAX calculated on 36%");
            }
            else if (taxableIncome>=550101 && taxableIncome<=701300) {
                tax = 147996 + (taxableIncome - 550100) * 0.39;
                System.out.println("TAX calculated on 39%");
            }
            else if (taxableIncome>=701301) {
                tax = 206964 + (taxableIncome - 701300) * 0.41;
                System.out.println("TAX calculated on 41%");
            }
            return tax;
    }

    @Override
    public int calculateTaxCredits(int age) {
        if(age < AGE65)
            return PRIMARY_REBATE;
        else if (age >= AGE65 && age < AGE75)
            return PRIMARY_REBATE+SECONDARY_REBATE;
        else if (age >= AGE75)
            return PRIMARY_REBATE+TERTIARY_REBATE;
        else
        return 0;
    }

    @Override
    public int calculateTaxThreshold(int age) {
        if(age < AGE65)
            return PRIMARY_THRESHOLD;
        else if(age >= AGE65 && age < AGE75)
            return SECONDARY_THRESHOLD;
        else if (age >= AGE75)
            return TERTIARY_THRESHOLD;
        else
        return 0;
    }

    @Override
    public int calculateMedicalAidCredits(int members) {
        if(members>0) {
            int sum = MEMBER*12;
            if (members >= 2)
                sum += FIRST_DEPENDENT*12;
            for (int i = 3; i <= members; i++)
                sum += ADDITIONAL_DEPENTANT*12;
            return sum;
        }
        else
            return 0;
    }

    public static TaxPayer calculateTax (Person person, String incomeFrequency, int medicalAidDeduction){
        TaxCalculator2017 taxCalculator2017 = new TaxCalculator2017();
        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setIncome(person.getIncome());
        taxPayer.setMedicalAid(taxCalculator2017.calculateMedicalAidCredits(medicalAidDeduction));
        taxPayer.setTaxableIncome(taxCalculator2017.calculateTaxableIncome(taxPayer.getIncome(),taxPayer.getMedicalAid()));

        //tax before credits
        double taxBeforeCredits = taxCalculator2017.calculateTaxRate(taxPayer.getTaxableIncome());
        taxPayer.setAnnualTaxBefore(taxBeforeCredits);
        taxPayer.setMonthlyTaxBefore(taxBeforeCredits/12);
        taxPayer.setTaxBeforeCredits(taxBeforeCredits);

        //tax rebates
        double rebates = taxCalculator2017.calculateTaxCredits(person.getAge());
        taxPayer.setRebates(rebates);


        //tax threshold
        taxPayer.setThreshold(taxCalculator2017.calculateTaxThreshold(person.getAge()));

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
