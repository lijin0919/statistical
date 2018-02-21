package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeyWordsRepository extends JpaRepository<KeywordsCode,Integer> {

    List<KeywordsCode> findByKeyWordsAndSearchEngineAndState(String keywords,String search,Integer state);
    List<KeywordsCode> findByCodeAndSearchEngineAndState(String keywords,String search,Integer state);
    Page<KeywordsCode> findAll(Pageable pageable);
    Page<KeywordsCode> findByState(Integer state,Pageable pageable);
    List<KeywordsCode> findByKeyWordsAndSearchEngine(String keywords,String search);
}
