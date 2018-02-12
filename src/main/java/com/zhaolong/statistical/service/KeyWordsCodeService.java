package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<KeywordsCode> getKeywordsCodeList(){
        List<KeywordsCode> list = keyWordsRepository.findAll();
        return list;
    }
}
