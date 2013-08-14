package com.auditbucket.spring.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:ab-spring.xml"
})
public class SimpleAuditedServiceTest {
    @Autowired
    private SimpleAuditedService simpleAuditedService;

    @Test
    public void testSimpleCallSimpleAuditedService() {
        SimpleAuditedService.Customer customer = new SimpleAuditedService.Customer();
        customer.setId(1L);
        customer.setName("name");
        customer.setEmail("email@email.com");
        simpleAuditedService.save(customer);
    }
}
