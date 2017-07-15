package main.imp;

import entity.Person;
import entity.TaxPayer;
import main.Calculator;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SamkeDl on 15/07/2017.
 */
@Stateful
public class TaxCalculator2017 implements Calculator,Serializable {
    @Override
    public BigDecimal calculateTaxableIncome(BigDecimal income, int medicalAidDeduction) {
        return income.subtract(new BigDecimal(medicalAidDeduction));
    }

    @Override
    public BigDecimal calculateTaxRate(BigDecimal taxableIncome) {
            System.out.println("Calculating tax on "+taxableIncome+" taxableIncome");
            BigDecimal tax = new BigDecimal(0);
        BigDecimal taxableIncomeAbove ;
            if (taxableIncome.compareTo(new BigDecimal(188000)) <= 0) {
                tax = taxableIncome.multiply(new BigDecimal(0.18)).setScale(2,BigDecimal.ROUND_HALF_EVEN);
                System.out.println("TAX calculated on 18%");
            }
            else if (taxableIncome.compareTo(new BigDecimal(188001))>= 0 && taxableIncome.compareTo(new BigDecimal(293600))<=0) {
                taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(188000)).multiply(new BigDecimal(0.26));
                tax = taxableIncomeAbove.setScale(2,BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(33840));
                System.out.println("TAX calculated on 26%");
            }
            else if (taxableIncome.compareTo(new BigDecimal(293601))>=0 && taxableIncome.compareTo(new BigDecimal(406400))<=0) {
                taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(293600)).multiply(new BigDecimal(0.31));
                tax = taxableIncomeAbove.setScale(2,BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(61296));
                System.out.println("TAX calculated on 31%" +tax);
            }
            else if (taxableIncome.compareTo(new BigDecimal(406401))>=0 && taxableIncome.compareTo(new BigDecimal(550100))<=0) {
                taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(406400)).multiply(new BigDecimal(0.36));
                tax = taxableIncomeAbove.setScale(2,BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(96264));
                System.out.println("TAX calculated on 36%");
            }
            else if (taxableIncome.compareTo(new BigDecimal(550101))>=0 && taxableIncome.compareTo(new BigDecimal(701300))<=0) {
                taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(550100)).multiply(new BigDecimal(0.39));
                tax = taxableIncomeAbove.setScale(2,BigDecimal.ROUND_HALF_EVEN).add( new BigDecimal(147996));
                System.out.println("TAX calculated on 39%");
            }
            else if (taxableIncome.compareTo(new BigDecimal(701301))>=0) {
                taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(701300)).multiply(new BigDecimal(0.41));
                tax = taxableIncomeAbove.setScale(2,BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(206964));
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
            return PRIMARY_REBATE+SECONDARY_REBATE+TERTIARY_REBATE;
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
        taxPayer.setMedicalAid(new BigDecimal(taxCalculator2017.calculateMedicalAidCredits(medicalAidDeduction)));
        taxPayer.setTaxableIncome(taxCalculator2017.calculateTaxableIncome(taxPayer.getIncome(),
                taxPayer.getMedicalAid().intValue()));
        System.out.println("TAxable income:"+taxPayer.getTaxableIncome());
        //tax before credits
        BigDecimal taxBeforeCredits = taxCalculator2017.calculateTaxRate(taxPayer.getTaxableIncome());
        System.out.println("Tax before credits: "+taxBeforeCredits);
        taxPayer.setAnnualTaxBefore(taxBeforeCredits);
        taxPayer.setTaxBeforeCredits(taxBeforeCredits.setScale(2,BigDecimal.ROUND_HALF_EVEN));

        //tax rebates
        BigDecimal rebates = new BigDecimal(taxCalculator2017.calculateTaxCredits(person.getAge()));
        System.out.println("retates: "+rebates);
        taxPayer.setRebates(rebates);


        //tax threshold
        taxPayer.setThreshold(new BigDecimal(taxCalculator2017.calculateTaxThreshold(person.getAge())));

        //tax after credits
        BigDecimal taxAfterCredits = taxBeforeCredits.subtract(rebates);
        System.out.println("Tax after credits:"+taxAfterCredits);
        if(taxAfterCredits.compareTo(taxPayer.getThreshold()) < 0){
            System.out.println("TAX LESS THEN THRESHOLD");
            taxPayer.setAnnualTaxAfter(taxAfterCredits);
            taxPayer.setTaxAfterCredits(taxAfterCredits);
            taxPayer.setNetIncome(person.getIncome().subtract(taxAfterCredits));
        }
        else{
            taxPayer.setAnnualTaxAfter(taxPayer.getThreshold());
            //annuals
            taxPayer.setTaxAfterCredits(taxPayer.getThreshold());
            taxPayer.setNetIncome(person.getIncome().subtract(taxPayer.getThreshold()));
        }
        if(incomeFrequency.equals("Monthly")){
            System.out.println("Inside Monthly");
            taxPayer.setTaxBeforeCredits(taxPayer.getTaxBeforeCredits().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            System.out.println("setTaxBeforeCredits = "+taxPayer.getTaxBeforeCredits());
            taxPayer.setTaxAfterCredits(taxPayer.getTaxAfterCredits().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            System.out.println("setTaxAfterCredits = "+taxPayer.getTaxAfterCredits());

            taxPayer.setNetIncome(taxPayer.getNetIncome().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            System.out.println("setNetIncome = "+taxPayer.getNetIncome());

            taxPayer.setRebates(taxPayer.getRebates().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            System.out.println("setRebates = "+taxPayer.getRebates());

        }



        return taxPayer;
    }
}
