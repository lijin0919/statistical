package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyWordsCodeService {

    @Autowired
    private KeyWordsRepository keyWordsRepository;

    /**
     * 保存关键词
     * @param keywords
     * @param keycode
     * @param keychannel
     */
    public void saveKeyWords(String keywords,String keycode,String keychannel){
        KeywordsCode keywordsCode = new KeywordsCode();
        keywordsCode.setCode(keycode);
        keywordsCode.setKeyWords(keywords);
        keywordsCode.setSearchEngine(keychannel);

        keyWordsRepository.save(keywordsCode);
    }

    public List<KeywordsCode> getKeywordsCodeList(Pageable pageable){
//        Page<KeywordsCode> list = keyWordsRepository.findAll(pageable);
        Page<KeywordsCode> list = keyWordsRepository.findByState(1,pageable);
        List<KeywordsCode> keywordsCodes = new ArrayList<>();
        int i = 1;
        for (KeywordsCode key:list) {
            key.setNo(i);
            keywordsCodes.add(key);
            i++;
        }


        return keywordsCodes;
    }

    public void deleteKey(Integer id){
        KeywordsCode keywordsCode = keyWordsRepository.findOne(id);
        keywordsCode.setState(0);
        keyWordsRepository.save(keywordsCode);
    }
}
