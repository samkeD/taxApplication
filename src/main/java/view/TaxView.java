package view;

import entity.Person;
import entity.TaxPayer;
import main.TaxCalculator;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
/**
 * Created by SamkeDl on 12/07/2017.
 */

@ManagedBean(name = "taxview" ,eager = true)
public class TaxView implements Serializable{
    private Person person= new TaxPayer();
    TaxCalculator taxCalculator=new TaxCalculator();

    private int taxYear;
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

    public int getTaxYear() {
        return taxYear;
    }

    public void setTaxYear(int taxYear) {
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

       return person;
    }



    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}



