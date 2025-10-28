package com.example.aiacademy.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;


    private String fullname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public User(String email, String password, String fullname, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.roles = roles != null ? roles : new HashSet<>();
    }
    public static class Builder {
        private String email;
        private String password;
        private String fullname;
        private Set<Role> roles = new HashSet<>();

        public Builder email(String email) { this.email = email; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder fullname(String fullname) { this.fullname = fullname; return this; }
        public Builder roles(Set<Role> roles) { this.roles = roles; return this; }

        public User build() {
            return new User(email, password, fullname, roles);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
