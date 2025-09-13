package com.tadobasolutions.repository;

import com.tadobasolutions.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByResetToken(String resetToken);

    List<User> findByManagerId(Long managerId);

    @Query("SELECT u FROM User u WHERE u.department.id = :departmentId AND u.manager.id = :managerId")
    List<User> findByDepartmentIdAndManagerId(@Param("departmentId") Long departmentId,
                                              @Param("managerId") Long managerId);

    @Query("SELECT u FROM User u WHERE u.manager.id = :managerId")
    List<User> findEmployeesByManagerId(@Param("managerId") Long managerId);
}
