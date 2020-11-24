package com.taxi.taxicontent.dao.trucks;

import com.taxi.taxicontent.model.trucks.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
