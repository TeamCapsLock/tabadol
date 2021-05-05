package com.example.tabadol.repository;

import com.example.tabadol.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {
//    List<Offer> findAllBySource_id(long source_id);
//    List<Offer> findAllByDestination_id(long destination_id);
}
