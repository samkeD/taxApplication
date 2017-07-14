package entity;

import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Created by SamkeDl on 12/07/2017.
 */
@Stateful
public class TaxPayer extends Person implements Serializable {
    double taxableIncome;
    double rebates;
    double medicalAid;
    double threshold;
    double annualTaxBefore;
    double monthlyTaxBefore;
    double annualTaxAfter;
    double monthlyTaxAfter;
    double annualNetSalary;
    double monthlyNetSalary;

    public TaxPayer(){

    }

    public double getTaxableIncome() {
        return taxableIncome;
    }

    public void setTaxableIncome(double taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public double getRebates() {
        return rebates;
    }

    public void setRebates(double rebates) {
        this.rebates = rebates;
    }

    public double getMedicalAid() {
        return medicalAid;
    }

    public void setMedicalAid(double medicalAid) {
        this.medicalAid = medicalAid;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getAnnualTaxBefore() {
        return annualTaxBefore;
    }

    public void setAnnualTaxBefore(double annualTaxBefore) {
        this.annualTaxBefore = annualTaxBefore;
    }

    public double getMonthlyTaxBefore() {
        return monthlyTaxBefore;
    }

    public void setMonthlyTaxBefore(double monthlyTaxBefore) {
        this.monthlyTaxBefore = monthlyTaxBefore;
    }

    public double getAnnualTaxAfter() {
        return annualTaxAfter;
    }

    public void setAnnualTaxAfter(double annualTaxAfter) {
        this.annualTaxAfter = annualTaxAfter;
    }

    public double getMonthlyTaxAfter() {
        return monthlyTaxAfter;
    }

    public void setMonthlyTaxAfter(double monthlyTaxAfter) {
        this.monthlyTaxAfter = monthlyTaxAfter;
    }

    public double getAnnualNetSalary() {
        return annualNetSalary;
    }

    public void setAnnualNetSalary(double annualNetSalary) {
        this.annualNetSalary = annualNetSalary;
    }

    public double getMonthlyNetSalary() {
        return monthlyNetSalary;
    }

    public void setMonthlyNetSalary(double monthlyNetSalary) {
        this.monthlyNetSalary = monthlyNetSalary;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TaxPayer{");
        sb.append("incomeTax=").append(taxableIncome);
        sb.append(", rebates=").append(rebates);
        sb.append(", medicalAid=").append(medicalAid);
        sb.append(", threshold=").append(threshold);
        sb.append('}');
        return sb.toString();
    }
}
