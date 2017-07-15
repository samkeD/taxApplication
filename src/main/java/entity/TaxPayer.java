package entity;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SamkeDl on 12/07/2017.
 */
@Stateful
public class TaxPayer extends Person implements Serializable {
    BigDecimal taxableIncome;
    BigDecimal rebates;
    BigDecimal medicalAid;
    BigDecimal threshold;
    BigDecimal taxBeforeCredits;
    BigDecimal taxAfterCredits;
    BigDecimal netIncome;

    BigDecimal annualTaxBefore;
    BigDecimal monthlyTaxBefore;
    BigDecimal annualTaxAfter;
    BigDecimal monthlyTaxAfter;
    BigDecimal annualNetSalary;
    BigDecimal monthlyNetSalary;

    public BigDecimal getTaxableIncome() {
        return taxableIncome;
    }

    public void setTaxableIncome(BigDecimal taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public BigDecimal getRebates() {
        return rebates;
    }

    public void setRebates(BigDecimal rebates) {
        this.rebates = rebates;
    }

    public BigDecimal getMedicalAid() {
        return medicalAid;
    }

    public void setMedicalAid(BigDecimal medicalAid) {
        this.medicalAid = medicalAid;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getTaxBeforeCredits() {
        return taxBeforeCredits;
    }

    public void setTaxBeforeCredits(BigDecimal taxBeforeCredits) {
        this.taxBeforeCredits = taxBeforeCredits;
    }

    public BigDecimal getTaxAfterCredits() {
        return taxAfterCredits;
    }

    public void setTaxAfterCredits(BigDecimal taxAfterCredits) {
        this.taxAfterCredits = taxAfterCredits;
    }

    public BigDecimal getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(BigDecimal netIncome) {
        this.netIncome = netIncome;
    }

    public BigDecimal getAnnualTaxBefore() {
        return annualTaxBefore;
    }

    public void setAnnualTaxBefore(BigDecimal annualTaxBefore) {
        this.annualTaxBefore = annualTaxBefore;
    }

    public BigDecimal getMonthlyTaxBefore() {
        return monthlyTaxBefore;
    }

    public void setMonthlyTaxBefore(BigDecimal monthlyTaxBefore) {
        this.monthlyTaxBefore = monthlyTaxBefore;
    }

    public BigDecimal getAnnualTaxAfter() {
        return annualTaxAfter;
    }

    public void setAnnualTaxAfter(BigDecimal annualTaxAfter) {
        this.annualTaxAfter = annualTaxAfter;
    }

    public BigDecimal getMonthlyTaxAfter() {
        return monthlyTaxAfter;
    }

    public void setMonthlyTaxAfter(BigDecimal monthlyTaxAfter) {
        this.monthlyTaxAfter = monthlyTaxAfter;
    }

    public BigDecimal getAnnualNetSalary() {
        return annualNetSalary;
    }

    public void setAnnualNetSalary(BigDecimal annualNetSalary) {
        this.annualNetSalary = annualNetSalary;
    }

    public BigDecimal getMonthlyNetSalary() {
        return monthlyNetSalary;
    }

    public void setMonthlyNetSalary(BigDecimal monthlyNetSalary) {
        this.monthlyNetSalary = monthlyNetSalary;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TaxPayer{");
        sb.append("taxableIncome=").append(taxableIncome);
        sb.append(", rebates=").append(rebates);
        sb.append(", medicalAid=").append(medicalAid);
        sb.append(", threshold=").append(threshold);
        sb.append(", taxBeforeCredits=").append(taxBeforeCredits);
        sb.append(", taxAfterCredits=").append(taxAfterCredits);
        sb.append(", netIncome=").append(netIncome);
        sb.append(", annualTaxBefore=").append(annualTaxBefore);
        sb.append(", monthlyTaxBefore=").append(monthlyTaxBefore);
        sb.append(", annualTaxAfter=").append(annualTaxAfter);
        sb.append(", monthlyTaxAfter=").append(monthlyTaxAfter);
        sb.append(", annualNetSalary=").append(annualNetSalary);
        sb.append(", monthlyNetSalary=").append(monthlyNetSalary);
        sb.append('}');
        return sb.toString();
    }
}
