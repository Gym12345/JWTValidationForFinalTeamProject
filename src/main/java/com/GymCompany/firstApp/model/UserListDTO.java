package com.GymCompany.firstApp.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "USERLIST")
public class UserListDTO implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userListSeqGen")
    @SequenceGenerator(name = "userListSeqGen", sequenceName = "user_list_seq", allocationSize = 1)
    @Column(name = "ULID")
    private int ulid;

    @Column(name = "USERID", unique = true)
    @NotNull
    @Size(max = 50)
    private String userId;

    @Column(name = "USERPW")
    @NotNull
    @Size(max = 100)
    private String userPw;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 100)
    private String userName;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "Last_Login_Time")
    private LocalDateTime lastLoginTime;

    @Column(name = "GENDER")
    @NotNull
    @Size(max = 50)
    private String gender;

    @Column(name = "AGE")
    @NotNull
    private int age;

    @Column(name = "EMAIL")
    @NotNull
    @Size(max = 50)
    private String email;

    @Column(name = "PHONE")
    @NotNull
    @Size(max = 50)
    private String phone;

    @Column(name = "PROFILE_URL", length = 255)
    private String profileUrl;

    @Column(name = "PROFILE_NAME", length = 255)
    private String profileName;

    @Column(name = "USER_SIZE", length = 50)
    private String userSize;

    @Column(name = "USER_BIO", length = 500)
    private String userBio;

    @Column(name = "RECEIVE_EMAIL", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String receiveEmail;

    @Column(name = "RECEIVE_MESSAGE", length = 1)
    @NotNull
    @Size(min = 1, max = 1)
    private String receiveMessage;

    @Column(name = "BLOCKED_PROFILES", columnDefinition = "CLOB")
    private String blockedProfiles;

    @Column(name = "FAVORITE_PRODUCTS", columnDefinition = "CLOB")
    private String favoriteProducts;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_AUTHORITY", joinColumns = @JoinColumn(name = "UA_ID"))
    @Column(name = "AUTHORITY_NAME")
    private List<String> roles = new ArrayList<>();
    
    
  

    public UserListDTO(int ulid, @NotNull @Size(max = 50) String userId, @NotNull @Size(max = 100) String userPw,
                       @NotNull @Size(max = 100) String userName, LocalDate joinDate, LocalDateTime lastLoginTime,
                       @NotNull @Size(max = 50) String gender, @NotNull int age,
                       @NotNull @Size(max = 50) String email, @NotNull @Size(max = 50) String phone,
                       String profileUrl, String profileName, String userSize, String userBio,
                       String receiveEmail, String receiveMessage, String blockedProfiles, String favoriteProducts,
                       List<String> roles) {
        this.ulid = ulid;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.joinDate = joinDate;
        this.lastLoginTime = lastLoginTime;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.profileUrl = profileUrl;
        this.profileName = profileName;
        this.userSize = userSize;
        this.userBio = userBio;
        this.receiveEmail = receiveEmail;
        this.receiveMessage = receiveMessage;
        this.blockedProfiles = blockedProfiles;
        this.favoriteProducts = favoriteProducts;
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    }

    // UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {   //UserDetails interface in Spring Security
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    //map is a stream operation that applies the given function to each element of the stream.
    //SimpleGrantedAuthority::new is a method reference that creates a new SimpleGrantedAuthority object for each role in the stream. The SimpleGrantedAuthority class is a concrete implementation of the GrantedAuthority interface, which represents a single authority granted to the user.
    //Each role (a String) is converted into a SimpleGrantedAuthority object.
    
    //When a user attempts to authenticate or perform an action, Spring Security will use the authorities returned by getAuthorities() to determine if the user has the necessary permissions. 
      //For example, if a certain action requires the "ROLE_ADMIN" authority, Spring Security will check if this authority is present in the collection returned by getAuthorities().
    
    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // UserDetails  ends
    public static class Builder {
        private int ulid;
        private String userId;
        private String userPw;
        private String userName;
        private LocalDate joinDate;
        private LocalDateTime lastLoginTime;
        private String gender;
        private int age;
        private String email;
        private String phone;
        private String profileUrl;
        private String profileName;
        private String userSize;
        private String userBio;
        private String receiveEmail;
        private String receiveMessage;
        private String blockedProfiles;
        private String favoriteProducts;
        private List<String> roles;

        public Builder ulid(int ulid) {
            this.ulid = ulid;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder userPw(String userPw) {
            this.userPw = userPw;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder joinDate(LocalDate joinDate) {
            this.joinDate = joinDate;
            return this;
        }

        public Builder lastLoginTime(LocalDateTime lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder profileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
            return this;
        }

        public Builder profileName(String profileName) {
            this.profileName = profileName;
            return this;
        }

        public Builder userSize(String userSize) {
            this.userSize = userSize;
            return this;
        }

        public Builder userBio(String userBio) {
            this.userBio = userBio;
            return this;
        }

        public Builder receiveEmail(String receiveEmail) {
            this.receiveEmail = receiveEmail;
            return this;
        }

        public Builder receiveMessage(String receiveMessage) {
            this.receiveMessage = receiveMessage;
            return this;
        }

        public Builder blockedProfiles(String blockedProfiles) {
            this.blockedProfiles = blockedProfiles;
            return this;
        }

        public Builder favoriteProducts(String favoriteProducts) {
            this.favoriteProducts = favoriteProducts;
            return this;
        }

        public Builder roles(List<String> roles) {
            this.roles = roles; // We assume a defensive copy of roles is handled outside the builder
            return this;
        }

        public UserListDTO build() {
            UserListDTO userListDTO = new UserListDTO();
            userListDTO.ulid = this.ulid;
            userListDTO.userId = this.userId;
            userListDTO.userPw = this.userPw;
            userListDTO.userName = this.userName;
            userListDTO.joinDate = this.joinDate;
            userListDTO.lastLoginTime = this.lastLoginTime;
            userListDTO.gender = this.gender;
            userListDTO.age = this.age;
            userListDTO.email = this.email;
            userListDTO.phone = this.phone;
            userListDTO.profileUrl = this.profileUrl;
            userListDTO.profileName = this.profileName;
            userListDTO.userSize = this.userSize;
            userListDTO.userBio = this.userBio;
            userListDTO.receiveEmail = this.receiveEmail;
            userListDTO.receiveMessage = this.receiveMessage;
            userListDTO.blockedProfiles = this.blockedProfiles;
            userListDTO.favoriteProducts = this.favoriteProducts;
            userListDTO.roles = this.roles != null ? new ArrayList<>(this.roles) : new ArrayList<>();
            return userListDTO;
        }
    }
    
}
