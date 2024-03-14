package com.example.demo.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;

@Component
public class BootStrapData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (customerRepository.count() < 2) {

            Division AZ = divisionRepository.findById(2L).orElse(null);
            Customer firstCustomer = new Customer("Fred", "Schwartz", "4014 Mom st", "91382", "(555)999-1213", AZ);
            customerRepository.save(firstCustomer);

            Division CO = divisionRepository.findById(5L).orElse(null);
            Customer secondCustomer = new Customer("Frank", "Shwartz", "5015 Dad st", "91385", "(555)999-1214", CO);
            customerRepository.save(secondCustomer);

            Division NH = divisionRepository.findById(28L).orElse(null);
            Customer thirdCustomer = new Customer("Fabio", "Swartz", "6016 Son st", "91383", "(555)999-1215", NH);
            customerRepository.save(thirdCustomer);

            Division PA = divisionRepository.findById(37L).orElse(null);
            Customer fourthCustomer = new Customer("Fiona", "Sharts", "7017 Bun st", "91387", "(555)999-1216", PA);
            customerRepository.save(fourthCustomer);

            Division UT = divisionRepository.findById(43L).orElse(null);
            Customer fifthCustomer = new Customer("Faith", "wartz", "9018 Cam st", "91388", "(555)999-1217", UT);
            customerRepository.save(fifthCustomer);

        }

    }

}
