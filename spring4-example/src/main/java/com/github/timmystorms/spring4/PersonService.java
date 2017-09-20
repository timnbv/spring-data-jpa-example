package com.github.timmystorms.spring4;

import com.google.common.cache.CacheLoader;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tim_ryzhov
 */
@Service
public class PersonService {

    private PersonRepository repository;

    private CacheContainer cacheContainer;

    @Autowired
    public PersonService(PersonRepository repository, CacheContainer cacheContainer) {
        this.repository = repository;
        this.cacheContainer = cacheContainer;
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Set<Person> findByName(String name) throws ExecutionException {
        return cacheContainer.getFromCache("personByName", name, new CacheLoader<String, Set<Person>>() {
            @Override
            public Set<Person> load(String key) {
                return findByNameNoCache(key);
            }
        });
    }

    public Set<Person> findByNameNoCache(String key) {
        return repository.findByName(key);
    }

    public Set<Person> searchByName(String name) throws ExecutionException {
        return cacheContainer.getFromCache("personSearchByName", name, new CacheLoader<String, Set<Person>>() {
            @Override
            public Set<Person> load(String key) {
                return searchNoCache(key);
            }
        });
    }

    public Set<Person> searchNoCache(String key) {
        return repository.search(key);
    }

    public Person findById(Long id) throws ExecutionException {
        return cacheContainer.getFromCache("personById", id, new CacheLoader<Long, Person>() {
            @Override
            public Person load(Long key) {
                return findOneNoCache(key);
            }
        });
    }

    public Person findOneNoCache(Long key) {
        return repository.findOne(key);
    }

}
