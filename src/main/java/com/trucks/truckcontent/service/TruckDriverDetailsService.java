package com.trucks.truckcontent.service;

import com.trucks.truckcontent.model.trucks.Role;
import com.trucks.truckcontent.model.trucks.TruckDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TruckDriverDetailsService implements UserDetailsService {

    @Autowired
    private TruckDriverService truckDriverService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TruckDriver truckDriver = truckDriverService.findTruckDriverByEmail(email);
        List<GrantedAuthority> authorities = getUserAuthority(truckDriver.getRoles());
        return buildUserForAuthentication(truckDriver, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(TruckDriver truckDriver, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(truckDriver.getEmail(), truckDriver.getPassword(),
                truckDriver.getActive(), true, true, true, authorities);
    }
}
