package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App,Integer> {
}
