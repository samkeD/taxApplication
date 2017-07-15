package entity;

import javax.ejb.Stateful;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by SamkeDl on 12/07/2017.
 */
@Stateful
public abstract class Person implements Serializable{
    private int age;
    private BigDecimal income;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Person{");
        sb.append("age=").append(age);
        sb.append(", income=").append(income);
        sb.append('}');
        return sb.toString();
    }
}
