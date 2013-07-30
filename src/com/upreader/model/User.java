package com.upreader.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.upreader.UpreaderConstants;
import com.upreader.helper.StringHelper;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    private String roles;

    @Column(name = "rating")
    private Integer rating;

    // addresses that a user have
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<UserAddress> userAddresses;

    // no cascading for projects
// if user is delete, project is allowed to stay as orphan for historical
// reasons
    @OneToMany(mappedBy = "user", orphanRemoval = false, fetch = FetchType.LAZY)
    protected List<ProjectOwnership> ownedProjects;

    // projects the user is a shareholder in
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectMembership> memberProjects;

    // serial stories the user subscribed to
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectSubscription> subscribedProjects;

    // serial stories the user subscribed to
    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<PinnedProject> pinnedProjects;

    // notifications received
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<Inbox> messages;

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

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<ProjectOwnership> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<ProjectOwnership> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
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

    public List<Inbox> getMessages() {
        return messages;
    }

    public void setMessages(List<Inbox> messages) {
        this.messages = messages;
    }

    public List<PinnedProject> getPinnedProjects() {
        return pinnedProjects;
    }

    public void setPinnedProjects(List<PinnedProject> pinnedProjects) {
        this.pinnedProjects = pinnedProjects;
    }
}
