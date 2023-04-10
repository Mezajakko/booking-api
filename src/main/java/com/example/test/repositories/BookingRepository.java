package com.example.test.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.test.models.BookingModel;

@Repository
public interface BookingRepository extends CrudRepository<BookingModel, Long> {

    @Query(value = "SELECT * FROM booking WHERE status = 'Active' AND start_date >= :today", nativeQuery = true)
    List<BookingModel> findActiveBookingsFromTodayOnwards(@Param("today") LocalDateTime today);

    @Query(value = "SELECT * FROM booking WHERE start_date >= :today", nativeQuery = true)
    List<BookingModel> findBookingsFromTodayOnwards(@Param("today") LocalDateTime today);

    @Query(value = "SELECT * FROM booking WHERE id = :id and status = 'Active'", nativeQuery = true)
    Optional<BookingModel> findByIdAndActive(@Param("id") Long id);

}
