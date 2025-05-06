package com.ozeken.expensecalendar.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

	
    private final AppUser appUser;

    public LoginUser(AppUser appUser) {
        super(
            Objects.requireNonNull(appUser, "AppUser must not be null").getUsername(),
            appUser.getPassword(),
            getAuthorities(appUser)
        );
        this.appUser = appUser;
    }
    
 // ★Springの内部処理用（@AuthenticationPrincipalなどで使われる）
    public LoginUser() {
        super("anonymous", "", List.of());
        this.appUser = null;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(AppUser user) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }
}
