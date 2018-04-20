package com.myprojects.tinyurl.repositiory;

import com.myprojects.tinyurl.domain.LongURL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LongURLRepository extends JpaRepository<LongURL, Long> {
    LongURL findByLongURL(String longURL);
}
