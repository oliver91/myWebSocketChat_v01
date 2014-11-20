package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;
import java.util.Date;

@Entity
public class User extends Model {

    @Id
    public String email;

    public int id;
    public String name;
    public String country;
    public String city;
    public String birthDate;
    public int userType;        // тип пользователя (1 - админ, 2 - студент, 3 - препод )
    public String password;


    public Date lastVisit;



    public User(String email, String name, int userType, String password, String country, String birthDate, String city, int id) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.city = city;
        this.country = country;
        this.userType = userType;
        this.password = password;
        this.id = id;
        this.lastVisit = Calendar.getInstance().getTime();
    }

    public static User authenticate(String email, String password) {
        return find.where().eq("email", email).eq("password", password)
                .findUnique();
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
}