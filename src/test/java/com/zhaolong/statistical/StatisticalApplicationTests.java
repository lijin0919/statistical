package com.zhaolong.statistical;

import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.KeyWordsRecordRepository;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import com.zhaolong.statistical.service.DateListService;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	@Autowired
	private DateListService dateListService;

	@Test
	public void keyTest() throws ParseException {


		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = simpleDateFormat.parse("2018-02-09 00:00:00");
		Date endDate = simpleDateFormat.parse("2018-02-15 00:00:00");
		Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
		Pageable pageable = new PageRequest(1, 10, sort);
		List<KeywordsRecord> list = dateListService.getKeyWords(startDate,endDate,pageable);
		System.out.println(list);
	}

}
