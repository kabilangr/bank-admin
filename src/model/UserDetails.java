package model;

import java.time.LocalDate;

public class UserDetails {
private String name;
private String password;
private String email;
private String address;
private String state;
private String city;
private int active;
private long id;
private long zip;
private long phone;
private double balance;
private LocalDate date;
public long getPhone() {
	return phone;
}
public void setPhone(long phone) {
	this.phone = phone;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public int getActive() {
	return active;
}
public void setActive(int active) {
	this.active = active;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public long getZip() {
	return zip;
}
public void setZip(long zip) {
	this.zip = zip;
}
public double getBalance() {
	return balance;
}
public void setBalance(double balance) {
	this.balance = balance;
}
public LocalDate getDate() {
	return date;
}
public void setDate(LocalDate date) {
	this.date = date;
}

}
