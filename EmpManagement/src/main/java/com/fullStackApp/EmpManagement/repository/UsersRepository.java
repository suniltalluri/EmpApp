package com.fullStackApp.EmpManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fullStackApp.EmpManagement.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

}
