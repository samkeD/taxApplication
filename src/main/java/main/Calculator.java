package main;

import java.math.BigDecimal;

/**
 * Created by SamkeDl on 15/07/2017.
 */
public interface Calculator {
    //ages
    int AGE65 = 65;
    int AGE75 = 75;

    //rebates
    int PRIMARY_REBATE = 13500;
    int SECONDARY_REBATE = 7407;
    int TERTIARY_REBATE = 2466;

    //thresholds
    int PRIMARY_THRESHOLD = 75000;
    int SECONDARY_THRESHOLD = 116150;
    int TERTIARY_THRESHOLD = 129850;

    //medical aid
    int MEMBER = 286;
    int FIRST_DEPENDENT = 286;
    int ADDITIONAL_DEPENTANT = 192;

    BigDecimal calculateTaxableIncome(BigDecimal income, int medicalAidDeduction);
    BigDecimal calculateTaxRate(BigDecimal taxableIncome);
    int calculateTaxCredits(int age);
    int calculateTaxThreshold(int age);
    int calculateMedicalAidCredits(int members);

}
