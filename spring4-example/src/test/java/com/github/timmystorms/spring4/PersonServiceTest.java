package com.github.timmystorms.spring4;

import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfiguration.class})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PersonServiceTest {

    @Autowired
    private PersonService service;

    @Autowired
    PersonRepository repo;

    @Before
    public void init() {
        repo.deleteAll();
        for (int i = 0; i < 10000; i++) {
            service.save(new Person("John Doe" + i));
        }
    }

    @Test
    public void findPersonByName() throws Exception {
        for (int i = 0; i < 5000; i++) {
            Set<Person> byName = service.findByName("John Doe1");
            Assert.assertNotNull(byName);
        }
    }

    @Test
    public void findPersonByNameNoCache() throws Exception {
        for (int i = 0; i < 5000; i++) {
            Set<Person> byName = service.findByNameNoCache("John Doe1");
            Assert.assertNotNull(byName);
        }
    }

//    @Test
//    public void findPersonById() throws Exception {
//        for (int i = 0; i < 5000; i++) {
//            Person byId = service.findOneNoCache(1L);
//            Assert.assertNotNull(byId);
//        }
//    }
//
//    @Test
//    public void findPersonByIdNoCache() throws Exception {
//        for (int i = 0; i < 5000; i++) {
//            Set<Person> byName = service.searchByName("John Doe");
//            Assert.assertNotNull(byName);
//        }
//    }

    @Test
    public void searchPerson() throws Exception {
        for (int i = 0; i < 5000; i++) {
            Set<Person> byName = service.searchByName("John Doe");
            Assert.assertNotNull(byName);
        }
    }

    @Test
    public void searchPersonNoCache() throws Exception {
        for (int i = 0; i < 5000; i++) {
            Set<Person> byName = service.searchNoCache("John Doe");
            Assert.assertNotNull(byName);
        }
    }
}
