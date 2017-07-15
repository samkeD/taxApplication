package main;

import entity.Person;
import entity.TaxPayer;

import javax.ejb.Stateless;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SamkeDl on 15/07/2017.
 */
@Stateless
public class TaxCalculator2018 implements Calculator,Serializable {
    @Override
    public BigDecimal calculateTaxableIncome(BigDecimal income, int medicalAidDeduction) {
        return income.subtract(new BigDecimal(medicalAidDeduction));
    }

    @Override
    public BigDecimal calculateTaxRate(BigDecimal taxableIncome) {
        System.out.println("Calculating tax on "+taxableIncome+" income");
        BigDecimal tax = new BigDecimal(0);
        BigDecimal taxableIncomeAbove;
        if (taxableIncome.compareTo(new BigDecimal(189880)) <= 0) {
            tax = taxableIncome.multiply(new BigDecimal(0.18));
            System.out.println("TAX calculated on 18%");
        }
        else if (taxableIncome.compareTo(new BigDecimal(189881))>=0
                && taxableIncome.compareTo(new BigDecimal(296540))<=0) {
           taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(189880))
                   .multiply(new BigDecimal(0.26));
            tax = taxableIncomeAbove.add(new BigDecimal(34178));
            System.out.println("TAX calculated on 26%");
        }
        else if (taxableIncome.compareTo(new BigDecimal(296541))>=0
                && taxableIncome.compareTo(new BigDecimal(410460))<=0) {
            taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(296540))
            .multiply(new BigDecimal(0.31));
            tax =  taxableIncomeAbove.add(new BigDecimal(61910));
            System.out.println("TAX calculated on 31%");
        }
        else if (taxableIncome.compareTo(new BigDecimal(410461))>=0
                && taxableIncome.compareTo(new BigDecimal(555600))<=0) {
            taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(410460))
                    .multiply(new BigDecimal(0.36));
            tax =  taxableIncomeAbove.add(new BigDecimal(97225));
            System.out.println("TAX calculated on 36%");
        }
        else if (taxableIncome.compareTo(new BigDecimal(555601))>= 0
                && taxableIncome.compareTo(new BigDecimal(708310))<=0) {
            taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(555600))
                    .multiply(new BigDecimal(0.39));
            tax =  taxableIncomeAbove.add(new BigDecimal(149475));
            System.out.println("TAX calculated on 39%");
        }
        else if (taxableIncome.compareTo(new BigDecimal(708311))>=0
                && taxableIncome.compareTo(new BigDecimal(1500000))<=0) {
            taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(708310))
                    .multiply(new BigDecimal(0.41));
            tax =  taxableIncomeAbove.add(new BigDecimal(209032));
            System.out.println("TAX calculated on 41%");
        }
        else if(taxableIncome.compareTo(new BigDecimal(1500001))>=0){
            taxableIncomeAbove = taxableIncome.subtract(new BigDecimal(1500000))
                    .multiply(new BigDecimal(0.45));
            tax =  taxableIncomeAbove.add(new BigDecimal(533625));
            System.out.println("TAX calculated on 41%");
        }
        return tax.setScale(2,BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public int calculateTaxCredits(int age) {
        if(age < AGE65){
            int newPrimary = (int)Math.round(PRIMARY_REBATE*0.01)+PRIMARY_REBATE;
            System.out.println("2018 PRIMARY REBATE = "+newPrimary);
            return (int)Math.round(PRIMARY_REBATE*0.01)+PRIMARY_REBATE ;
        }
        else if (age >= AGE65 && age < AGE75) {
            double newPrimary = ((PRIMARY_REBATE * 0.01) + PRIMARY_REBATE)+
                    ((SECONDARY_REBATE * 0.01) + SECONDARY_REBATE);//((int)Math.round(PRIMARY_REBATE * 0.01) + PRIMARY_REBATE )+ ((int)Math.round (SECONDARY_REBATE * 0.01) + SECONDARY_REBATE);

            System.out.println("2018 SECONDARY REBATE  = "+newPrimary);

            return ((int) Math.round(PRIMARY_REBATE * 0.01) + PRIMARY_REBATE) + ((int)Math.round(SECONDARY_REBATE * 0.01) + SECONDARY_REBATE);
        }
        else if (age >= AGE75) {
            int newPrimary = ((int)Math.round(PRIMARY_REBATE * 0.01) + PRIMARY_REBATE)+ ((int)Math.round(SECONDARY_REBATE * 0.01) + SECONDARY_REBATE) + ((int)Math.round(TERTIARY_REBATE * 0.01) + TERTIARY_REBATE);
            System.out.println("2018 TERTIARY REBATE  = "+newPrimary);
            return ((int)Math.round(PRIMARY_REBATE * 0.01) + PRIMARY_REBATE) +
                    ((int)Math.round(SECONDARY_REBATE * 0.01) + SECONDARY_REBATE) +
                    ((int)Math.round(TERTIARY_REBATE * 0.01) + TERTIARY_REBATE);
        }
        else
            return 0;
    }

    @Override
    public int calculateTaxThreshold(int age) {
        if(age < AGE65)
            return (int)Math.round(PRIMARY_THRESHOLD*0.01) + PRIMARY_THRESHOLD;
        else if(age >= AGE65 && age < AGE75)
            return (int)Math.round(SECONDARY_THRESHOLD*0.01) + SECONDARY_THRESHOLD;
        else if (age >= AGE75)
            return (int)Math.round(TERTIARY_THRESHOLD*0.01) +TERTIARY_THRESHOLD;
        else
            return 0;
    }

    @Override
    public int calculateMedicalAidCredits(int members) {
        if(members>0) {
            int sum = ((int)Math.round(MEMBER * 0.01) + MEMBER)*12;
            if (members >= 2)
                sum += ((int) Math.round(FIRST_DEPENDENT * 0.01) + FIRST_DEPENDENT)*12;
            for (int i = 3; i <= members; i++)
                sum += ((int)Math.round (ADDITIONAL_DEPENTANT * 0.01) + ADDITIONAL_DEPENTANT)*12;
            return sum;
        }
        else
            return 0;
    }

    public static TaxPayer calculateTax (Person person, String incomeFrequency, int medicalAidDeduction){
        TaxCalculator2018 taxCalculator2018 = new TaxCalculator2018();
        TaxPayer taxPayer = new TaxPayer();
        taxPayer.setIncome(person.getIncome());
        taxPayer.setMedicalAid(new BigDecimal(taxCalculator2018.calculateMedicalAidCredits(medicalAidDeduction)));
        taxPayer.setTaxableIncome(taxCalculator2018.calculateTaxableIncome(taxPayer.getIncome(),
                taxPayer.getMedicalAid().intValue()));

        //tax before credits
        BigDecimal taxBeforeCredits = taxCalculator2018.calculateTaxRate(taxPayer.getTaxableIncome());
        taxPayer.setAnnualTaxBefore(taxBeforeCredits);
        taxPayer.setTaxBeforeCredits(taxBeforeCredits.setScale(2,BigDecimal.ROUND_HALF_EVEN));

        //tax rebates
        BigDecimal rebates = new BigDecimal(taxCalculator2018.calculateTaxCredits(person.getAge()));
        taxPayer.setRebates(rebates);


        //tax threshold
        taxPayer.setThreshold(new BigDecimal(taxCalculator2018.calculateTaxThreshold(person.getAge())));

        //tax after credits
        BigDecimal taxAfterCredits = taxBeforeCredits.subtract(rebates);
        if(taxAfterCredits.compareTo(taxPayer.getThreshold()) < 0){
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
            taxPayer.setTaxBeforeCredits(taxPayer.getTaxBeforeCredits().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            taxPayer.setTaxAfterCredits(taxPayer.getTaxAfterCredits().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            taxPayer.setNetIncome(taxPayer.getNetIncome().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
            taxPayer.setRebates(taxPayer.getRebates().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_EVEN));
        }



        return taxPayer;
    }

}
