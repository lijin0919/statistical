package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.KeywordsRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface DataListRepository extends JpaRepository<KeywordsRecord,Integer>{

    Page<KeywordsRecord> findByRecordDateBetween(Date start, Date end, Pageable pageable);
}