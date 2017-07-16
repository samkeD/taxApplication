package controller;

import model.Person;
import model.TaxPayer;
import service.imp.TaxCalculator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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
        if(member.equals("yes") && medicalAidMember <=0)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Warning!","Must have at least one medical aid member"));
        else if(member.equals("no") && medicalAidMember>0)
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Warning!","Please select that you are medical aid member"));
        else {
            if(incomeFreguency.equals("Monthly")) {
                person.setIncome(person.getIncome().multiply(new BigDecimal(12)));
            }
            person = taxCalculator.findTax(taxYear, person, incomeFreguency, medicalAidMember);
            if (person != null)
                payer = true;
        }
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



