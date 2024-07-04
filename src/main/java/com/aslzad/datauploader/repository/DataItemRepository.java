package com.aslzad.datauploader.repository;

import com.aslzad.datauploader.model.DataItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataItemRepository extends JpaRepository<DataItem, UUID> {
    Optional<DataItem> findByCode(String code);
}
