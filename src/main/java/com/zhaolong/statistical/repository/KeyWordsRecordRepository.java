package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KeyWordsRecordRepository extends JpaRepository<KeywordsRecord,Integer>{

    List<KeywordsRecord> findByKeywordsCodeAndSearchEngine(KeywordsCode keywordsCode, String search);
}
