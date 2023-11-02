package eu.deltasource.training.library.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Author {

    private String first_name;
    private String last_name;
    private LocalDate birthdate;

    public Author() {
    }

    public Author(String first_name, String last_name, LocalDate birthday) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.birthdate = birthday;
    }

    public String getName() {
        return first_name + " " + last_name;
    }

    public void setFirst_name(String first_name) {
        if(first_name != null) {
            this.first_name = first_name;
        }
    }

    public void setLast_name(String last_name) {
        if(last_name != null) {
            this.last_name = last_name;
        }
    }

    public void setBirthday(LocalDate birthdate) {
        if(birthdate != null) {
            this.birthdate = birthdate;
        }
    }

    @Override
    public String toString() {
        return first_name + last_name + ", birthday=" + birthdate;
    }
}
