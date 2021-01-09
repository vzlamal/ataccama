package com.example.ataccama.repository;

import com.example.ataccama.domain.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry,Long> {

}
