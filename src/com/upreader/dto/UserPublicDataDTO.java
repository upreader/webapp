package com.upreader.dto;

import com.upreader.model.User;

import java.util.Date;

public class UserPublicDataDTO {
    private String firstName;
    private String lastName;
    private String name;
    private Date birthDay;
    private String occupation;
    private String email;
    private Integer rating;
    private String bio;
    private String education;
    private String experience;
    private String motivation;
    private String inspiration;
    private Boolean isEmailConfirmed;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String address;
    private Date emailConfirmationDeadline;

    public  UserPublicDataDTO(){
    }

    public UserPublicDataDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.name = this.firstName + " " + this.lastName;
        this.birthDay = user.getBirthday();
        this.occupation = user.getOccupation();
        this.inspiration = user.getInspiration();
        this.email = user.getEmail();
        this.rating = user.getRating();
        this.bio = user.getBio();
        this.education = user.getEducation();
        this.experience = user.getExperience();
        this.motivation = user.getMotivation();
        this.isEmailConfirmed = user.getEmailConfirmed();
        this.city = user.getCity();
        this.state = user.getState();
        this.country = user.getCountry();
        this.zip = user.getZip();
        this.address = user.getAddress();
        this.emailConfirmationDeadline = user.getEmailConfirmDeadline();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getEmail() {
        return email;
    }

    public Integer getRating() {
        return rating;
    }

    public String getBio() {
        return bio;
    }

    public String getEducation() {
        return education;
    }

    public String getExperience() {
        return experience;
    }

    public String getMotivation() {
        return motivation;
    }

    public String getInspiration() {
        return inspiration;
    }

    public Boolean getEmailConfirmed() {
        return isEmailConfirmed;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public Date getEmailConfirmationDeadline() {
        return emailConfirmationDeadline;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void setInspiration(String inspiration) {
        this.inspiration = inspiration;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        isEmailConfirmed = emailConfirmed;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailConfirmationDeadline(Date emailConfirmationDeadline) {
        this.emailConfirmationDeadline = emailConfirmationDeadline;
    }
}
