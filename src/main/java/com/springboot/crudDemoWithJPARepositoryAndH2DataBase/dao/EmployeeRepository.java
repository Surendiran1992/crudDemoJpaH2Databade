package com.springboot.crudDemoWithJPARepositoryAndH2DataBase.dao;

import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    static List<Employee> getEmployeeData() {
        return Stream.of(new Employee("Parthiv", "Patel", "patel123@gmail.com"),
                new Employee( "Vicky","Koshal", "vicky123@gmail.com"),
                new Employee("Mandu", "Rakesh", "rakesh@gmail.com"),
                new Employee("Kishan", "Ishaan", "ishaan@gmail.com"),
                new Employee("kumar", "Ajay", "ajay@gmail.com"),
                new Employee("Das", "Harold", "harold@gmail.com"),
                new Employee("Diran", "Suren", "suren@gmail.com"),
                new Employee("Wayne", "Suren", "suren1@gmail.com")
        ).collect(Collectors.toList());
    }

    default void createDataForH2(){
        saveAll(EmployeeRepository.getEmployeeData());
    }
    List<Employee> findByFirstName(String firstName);
}
