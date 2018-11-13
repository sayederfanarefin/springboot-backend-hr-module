package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findById (long id);

    Image findByUrl(String url);

    Image getFirstById(long id);
}