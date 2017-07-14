package entity;

import javax.ejb.Stateful;
import java.io.Serializable;

/**
 * Created by SamkeDl on 12/07/2017.
 */
@Stateful
public abstract class Person implements Serializable{
    private int age;
    private int income;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
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
