package view;

import entity.Person;
import entity.TaxPayer;
import entity.Year;
import main.TaxCalculator;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


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
        if(incomeFreguency.equals("Monthly")) {
            person.setIncome(person.getIncome() * 12);
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



