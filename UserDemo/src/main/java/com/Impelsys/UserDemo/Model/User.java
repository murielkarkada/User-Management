//JPA Entity : Creating the data model
package com.Impelsys.UserDemo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity   //specifies that the class is an entity
@Table(name = "userdata")//Specify the table name
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User
{
    @Id //specifies the primary key of an entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) //provides for the specification of generation strategies for the values of primary keys
    private  long id;

    @Column(name = "first_name")
    @NotBlank(message = "Enter valid first name")
    // Specifies the mapped column for a persistent property or field.
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Enter valid last name")
    private String lastName;

    @Column(name = "email")
    @Email(message = "Enter valid email address")
    private String email;

    @Column(name = "phone")
    @NotBlank( message = "Enter valid phone number")
    @Size(min = 10, message = "Enter 10 digit number")
    private String phone;


    //Getters and Setters for all the fields
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String  phone)
    {
        this.phone = phone;
    }
}

