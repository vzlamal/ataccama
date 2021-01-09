package com.example.ataccama.rest;

import com.example.ataccama.domain.Entry;
import com.example.ataccama.repository.EntryRepository;
import com.example.ataccama.service.DatabaseConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Set;

@RestController
@RequestMapping("api")
public class apiControler {
    private final DatabaseConnectorService databaseConnectorService;

    public apiControler(DatabaseConnectorService databaseConnectorService) {
        this.databaseConnectorService = databaseConnectorService;
    }

    @Autowired
    EntryRepository entryRepository;

    @RequestMapping(value = "/entry/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<String >> getTables(@PathVariable("id") long id){
        Entry entry = entryRepository.getOne(id);

        try {
            return new ResponseEntity<>( databaseConnectorService.getTables(entry), HttpStatus.OK);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
