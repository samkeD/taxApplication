package controller;

import model.Person;
import model.TaxPayer;
import service.imp.TaxCalculator;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SamkeDl on 12/07/2017.
 */

@ManagedBean(name = "taxview" )
public class TaxView implements Serializable{
    private Person person= new TaxPayer();
    TaxCalculator taxCalculator=new TaxCalculator();

    private Integer taxYear;
    private String incomeFreguency;
    private int medicalAidMember;
    private String member;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public int getMedicalAidMember() {
        return medicalAidMember;
    }

    public void setMedicalAidMember(int medicalAidMember) {
        this.medicalAidMember = medicalAidMember;
    }

    public String getIncomeFreguency() {
        return incomeFreguency;
    }

    public void setIncomeFreguency(String incomeFreguency) {
        this.incomeFreguency = incomeFreguency;
    }

    public Integer getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(Integer taxYear) {
        this.taxYear = taxYear;
    }

    public Person getTaxCalculation(){
        System.out.println("Inside getTaxCalculation");
        System.out.println("MEd aid member selected! "+member);
        System.out.println("Medi members "+medicalAidMember);
        if(incomeFreguency.equals("Monthly")) {
            person.setIncome(person.getIncome().multiply(new BigDecimal(12)));
        }
        System.out.println("TAX YEAR: "+taxYear+" P: "+person.getIncome());
         person = taxCalculator.findTax(taxYear,person,incomeFreguency,medicalAidMember);

        if(person!=null)
            payer =true;

       return person;
    }

    private boolean payer;

    public boolean isPayer() {
        return payer;
    }

    public void setPayer(boolean payer) {
        this.payer = payer;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}



