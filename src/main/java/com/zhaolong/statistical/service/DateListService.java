package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DateListService {

    @Autowired
    private DataListRepository dataListRepository;

    public List<KeywordsRecord> getKeyWords(Date start, Date end, Pageable pageable){

        List<KeywordsRecord> list = new ArrayList<>();
        Page<KeywordsRecord> recordPage = dataListRepository.findByRecordDateBetween(start,end,pageable);

        for (KeywordsRecord k:recordPage) {
            list.add(k);
        }

        return list;
    }

}
