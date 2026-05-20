package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Employee;
import com.flowers.usermanagementservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUserFactory extends UserFactory {

    @Override
    public String getType() {
        return "EMPLOYEE";
    }

    @Override
    protected User createConcreteUser(User source) {
        return new Employee(source.getUsername(), source.getPassword(), source.getFlowerShopId());
    }
}
