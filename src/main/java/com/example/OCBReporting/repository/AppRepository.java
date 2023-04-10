package com.example.OCBReporting.repository;

import com.example.OCBReporting.model.App;
import com.example.OCBReporting.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppRepository extends JpaRepository<App,Integer> {
    List<App> findByNameIn(List<String> appsNames);
}
