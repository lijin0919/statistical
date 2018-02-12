package com.zhaolong.statistical;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.KeyWordsRecordRepository;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticalApplicationTests {

	@Autowired
	private KeyWordsCodeService keyWordsCodeService;

	@Autowired
	private KeyWordsRepository keyWordsRepository;

	@Autowired
	private KeyWordsRecordRepository keyWordsRecordRepository;
	@Test
	public void contextLoads() {
//		keyWordsCodeService.saveKeyWords("网络培训","wangluopeixun","百度PC");
	}

	@Test
	public void getKeyWordsCodeByKeyWords(){
//		List<KeywordsCode> list = keyWordsRepository.findByKeyWords("网络培训");
//		System.out.println(list.size());
	}

	@Test
	public void findKeyFromRecord(){
//		List<KeywordsCode> list = keyWordsRepository.findByKeyWords("网络培训");
//		List<KeywordsRecord> keywordsRecords =
//				keyWordsRecordRepository.findByKeywordsCode(list.get(0));
//			System.out.println(keywordsRecords.size());
	}

	@Test
	public void stringTest(){
		String a = "http://m.zhaolongedu.com/zt/java/?baidu-C-javapx&-java";
		int n = a.indexOf("?");
		String b = a.substring(n+1,a.length());
		System.out.println(b);
	}

}
