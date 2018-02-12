package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyWordsRepository extends JpaRepository<KeywordsCode,Integer> {

    List<KeywordsCode> findByKeyWordsAndSearchEngine(String keywords,String search);
}
