package com.trucks.truckcontent.service;

import com.trucks.truckcontent.dao.trucks.RoleRepository;
import com.trucks.truckcontent.dao.trucks.TruckDriverRepository;
import com.trucks.truckcontent.model.trucks.Role;
import com.trucks.truckcontent.model.trucks.TruckDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class TruckDriverService {

    private TruckDriverRepository truckDriverRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public TruckDriverService(TruckDriverRepository truckDriverRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.truckDriverRepository = truckDriverRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public TruckDriver findTruckDriverByEmail(String email) {
        return truckDriverRepository.findByEmail(email);
    }

    public TruckDriver saveUser(TruckDriver truckDriver) {
        truckDriver.setPassword(bCryptPasswordEncoder.encode(truckDriver.getPassword()));
        truckDriver.setActive(true);
        Role userRole = roleRepository.findByRole("DRIVER");
        truckDriver.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return truckDriverRepository.save(truckDriver);
    }

}
