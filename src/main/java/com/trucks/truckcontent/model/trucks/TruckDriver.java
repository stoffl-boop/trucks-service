package com.trucks.truckcontent.model.trucks;

import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "truck_drivers")
public class TruckDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    @Email(message = "*Будь ласка вкажіть валідний email")
    @NotEmpty(message = "*Будь ласка вкажіть email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Пароль має містити мінімум 5 символів")
    @NotEmpty(message = "*Будь ласка вкажіть пароль")
    private String password;

    @NotEmpty(message = "*Будь ласка вкажіть ім'я")
    private String firstName;

    @NotEmpty(message = "*Будь ласка вкажіть прізвище")
    private String secondName;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Lob
    private String image;

    @Length(min = 10, message = "*Телефон має містити 10 цифр")
    @NotEmpty(message = "*Будь ласка вкажіть номер телефону")
    private String phone;

    @OneToOne(mappedBy = "truckDriver")
    private Truck truck;

    @OneToMany(
            mappedBy = "truckDriver",
            cascade = CascadeType.ALL
    )
    private List<TruckOrder> truckOrders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<TruckOrder> getTruckOrders() {
        return truckOrders;
    }

    public void setTruckOrders(List<TruckOrder> truckOrders) {
        this.truckOrders = truckOrders;
    }
}
