package com.springboot.crudDemoWithJPARepositoryAndH2DataBase;

import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.controller.EmployeeSpringRestController;
import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.dao.EmployeeRepository;
import com.springboot.crudDemoWithJPARepositoryAndH2DataBase.entity.Employee;
import jakarta.servlet.ServletContext;
import jdk.jfr.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@WebMvcTest(value = EmployeeSpringRestController.class,excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class EmployeeSpringRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(get("/ping")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasToString("hello world")));
    }

    @Test
    public void validatedFindAllEmployee() throws Exception {
        when(employeeRepository.findAll()).thenReturn(EmployeeRepository.getEmployeeData());
        mockMvc.perform(get("/employees")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(8)));
    }

    @Test
    public void validateAddingNewEmployee() throws Exception{
        Employee employee = new Employee("suren","diran","suren@gmail.com");
        String employeeBody = "{" + "\"firstName\": \"suren\"," + "\"lastName\": \"diran\"," + "\"email\": \"suren@gmail.com\"}";
        when(employeeRepository.save(employee)).thenReturn(employee);
        mockMvc.perform(post("/employees").contentType("application/json").content(employeeBody)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.hasToString("suren")));
    }
}
