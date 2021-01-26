package com.example.ataccama.rest;

import com.example.ataccama.repository.EntryRepository;
import com.example.ataccama.domain.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EntryController {
    private static final Map<String, String> OK_RESULT = Collections.singletonMap("status", "OK");

    @Autowired
    EntryRepository entryRepository;

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getStatus() {
        return OK_RESULT;
    }

    @GetMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Entry>> getAllEntries() {
        try {

            List<Entry> entries = new ArrayList<>(entryRepository.findAll());

            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entry> createEntry(@RequestBody Entry entry) {
        try {
            return new ResponseEntity<>(
                    entryRepository.save(new Entry(entry.getName(), entry.getPass(), entry.getDatabase(), entry.getUrl(), entry.getPort())),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/entry/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Entry> updateEntry(@PathVariable("id") long id, @RequestBody Entry entry) {
        Optional<Entry> optionalEntry = entryRepository.findById(id);

        if (optionalEntry.isPresent()) {
            Entry updateEntryToUpdate = optionalEntry.get();
            updateEntryToUpdate.setName(entry.getName());
            updateEntryToUpdate.setPass(entry.getPass());
            updateEntryToUpdate.setDatabase(entry.getDatabase());
            updateEntryToUpdate.setUrl(entry.getUrl());
            updateEntryToUpdate.setPort(entry.getPort());
            return new ResponseEntity<>(entryRepository.save(updateEntryToUpdate), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/entry/{id}")
    public ResponseEntity<HttpStatus> deleteEntry(@PathVariable("id") long id) {
        try {
            entryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
