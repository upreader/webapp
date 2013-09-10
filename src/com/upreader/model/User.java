package com.upreader.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.upreader.UpreaderConstants;
import com.upreader.helper.StringHelper;

/**
 * An user of the UpReader platform.
 * The user is identified uniquely by his email address.
 */
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = {"email"})})
@NamedQueries({
        @NamedQuery(name = User.NQ_FIND_BY_EMAIL, query = "SELECT u from User u where u.email = :email"),
        @NamedQuery(name = User.NQ_FIND_ALL, query = "SELECT u from User u")})
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User implements Serializable {
    public static final String NQ_FIND_BY_EMAIL = "User_findByEmail";
    public static final String NQ_FIND_ALL = "User_findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(name = "email", unique = true)
    private String email;

    /**
     * see com.upreader.util.PasswordUtil.encryptPassword()
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * comma-separated list of roles:
     *
     * UpreaderConstants.ROLE_PROSPECTOR
     * UpreaderConstants.ROLE_UPREADER
     * UpreaderConstants.ROLE_AUTHOR
     * UpreaderConstants.ROLE_PUBLISHER
     * UpreaderConstants.ROLE_EDITOR
     * UpreaderConstants.ROLE_ADMIN
     */
    @Column(name = "roles", nullable = false)
    private String roles;

    /**
     * if the account is locked
     */
    @Column(name = "locked")
    private Boolean locked;

    /**
     * why the account was locked
     */
    @Column(name = "locked_reason")
    private String lockedReason;

    /**
     * rating can be from 1 to 6
     */
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "country")
    private String country;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    /**
     * street address and number
     */
    @Column(name = "address")
    private String address;

    @Column(name = "zip")
    private String zip;

    @Column(name = "occupation")
    private String occupation;

    /**
     * Having another table for this does not make sense.
     * Values will be separated by commas:
     *     MBA Harvard 8/1987 2/2002, Stanford 4/2000 12/2004)
     */
    @Column(name = "education")
    private String education;

    /**
     * Having another table for this does not make sense.
     * Values will be separated by commas:
     *     3 novel writer, 1 poem writer
     */
    @Column(name = "experience")
    private String experience;

    @Column(name = "motivation")
    private String motivation;

    /**
     * Having another table for this does not make sense.
     * Values will be separated by commas:
     *     J.R.R. Tolkien, Jules Verne
     */
    @Column(name = "inspiration")
    private String inspiration;

    @Column(name = "bio")
    private String bio;

    /**
     * Whether to receive emails from Upreader
     */
    @Column(name = "update_me")
    private Boolean updateMe;

    /**
     * User confirmed his email address or not
     */
    @Column(name = "email_confirmed")
    private Boolean emailConfirmed;

    /**
     * By when the user needs to confirm email address
     */
    @Column(name = "confirm_deadline")
    private Date emailConfirmDeadline;

    /**
     * Unique token used in email confirmation
     */
    @Column(name = "cofirm_uuid")
    private String confirmUUID;

    /**
     * Cookie used to login the user using it's stored browser cookie.
     * The cookie is set by the UpreaderAuthenticator
     */
    @Column(name = "cookie")
    private String loginCookie;

    /**
     * no cascading for projects
     * if user is delete, project is allowed to stay as orphan for historical
     *  reasons
     */
    @OneToMany(mappedBy = "author", orphanRemoval = false, fetch = FetchType.LAZY)
    protected List<Project> ownedProjects;

    /**
     * projects the user is a shareholder in
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectMembership> memberProjects;

    /**
     * serial stories the user subscribed to
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectSubscription> subscribedProjects;

    /**
     * serial stories the user subscribed to
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<PinnedProject> pinnedProjects;

    /**
     * login activity
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<UserLogin> logins;

    /**
     * notifications received
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "receiver")
    private List<Notification> notifications;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getRating() {
        return rating;
    }

    public String getRatingAsString() {
        return rating.toString();
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public List<ProjectMembership> getMemberProjects() {
        return memberProjects;
    }

    public void setMemberProjects(List<ProjectMembership> memberProjects) {
        this.memberProjects = memberProjects;
    }

    public List<ProjectSubscription> getSubscribedProjects() {
        return subscribedProjects;
    }

    public void setSubscribedProjects(
            List<ProjectSubscription> subscribedProjects) {
        this.subscribedProjects = subscribedProjects;
    }

    public boolean isAuthor() {
        return StringHelper.containsNullSafe(this.roles,
                UpreaderConstants.ROLE_AUTHOR);
    }

    public boolean isProspector() {
        return StringHelper.containsNullSafe(this.roles,
                UpreaderConstants.ROLE_PROSPECTOR);
    }

    public boolean isUpreader() {
        return StringHelper.containsNullSafe(this.roles,
                UpreaderConstants.ROLE_UPREADER);
    }

    public boolean isEditor() {
        return StringHelper.containsNullSafe(this.roles,
                UpreaderConstants.ROLE_EDITOR);
    }

    public boolean isAdmin() {
        return StringHelper.containsNullSafe(this.roles,
                UpreaderConstants.ROLE_ADMIN);
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<PinnedProject> getPinnedProjects() {
        return pinnedProjects;
    }

    public void setPinnedProjects(List<PinnedProject> pinnedProjects) {
        this.pinnedProjects = pinnedProjects;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getInspiration() {
        return inspiration;
    }

    public void setInspiration(String inspiration) {
        this.inspiration = inspiration;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<UserLogin> getLogins() {
        return logins;
    }

    public void setLogins(List<UserLogin> logins) {
        this.logins = logins;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockedReason() {
        return lockedReason;
    }

    public void setLockedReason(String lockedReason) {
        this.lockedReason = lockedReason;
    }

    public Boolean getUpdateMe() {
        return updateMe;
    }

    public void setUpdateMe(Boolean updateMe) {
        this.updateMe = updateMe;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public Date getEmailConfirmDeadline() {
        return emailConfirmDeadline;
    }

    public void setEmailConfirmDeadline(Date emailConfirmDeadline) {
        this.emailConfirmDeadline = emailConfirmDeadline;
    }

    public String getConfirmUUID() {
        return confirmUUID;
    }

    public void setConfirmUUID(String confirmUUID) {
        this.confirmUUID = confirmUUID;
    }

    public String getLoginCookie() {
        return loginCookie;
    }

    public void setLoginCookie(String loginCookie) {
        this.loginCookie = loginCookie;
    }
}
