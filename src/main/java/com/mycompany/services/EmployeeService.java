package com.mycompany.services;

import com.mycompany.model.Employee;
import com.mycompany.dao.EmployeeDAO;

import java.io.IOException;

public class EmployeeService implements AbstractEmployeeService{
    private final EmployeeDAO employeeDAO;
    public EmployeeService() throws IOException {
        employeeDAO = new EmployeeDAO("db-connection.properties");
    }

    public boolean createEmployee(Employee employee){
        return employeeDAO.createEmployee(employee);
    }

    public Employee getEmployee(int empId){
        return employeeDAO.getEmployee(empId);
    }

    public boolean updateEmployee(Employee employee){
        return employeeDAO.updateEmployee(employee);
    }

    public boolean deleteEmployee(int empId){
        return employeeDAO.deleteEmployee(empId);
    }

}
