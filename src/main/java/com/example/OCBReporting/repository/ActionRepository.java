package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.Action;
import com.example.OCBReporting.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action,Integer> {
}
