package io.ganguo.incubator.entity.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Wilson on 10/12/15.
 */
@Table(name = "group_t", id = "_id")
public class Group extends Model {
    @Column(name = "name")
    public String name;

    @Column(name = "year")
    public int year;

    @Override
    public String toString() {
        return "Group{" +
                "year=" + year +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static Group queryByName(String groupName) {
        return new Select().from(Group.class).where("name=?", groupName).orderBy("_id ASC").executeSingle();
    }

    public List<Employee> queryEmployees() {
        return new Select().from(Employee.class).where("in_group=?", getId()).orderBy("_id desc").execute();
    }

    public void clearEmployees() {
        new Delete().from(Employee.class).where("in_group=?", getId()).execute();
    }


}
