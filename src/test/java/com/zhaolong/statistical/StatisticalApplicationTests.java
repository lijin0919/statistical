package com.zhaolong.statistical;

import com.zhaolong.statistical.entity.ExportInfo;
import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.entity.UserInfo;
import com.zhaolong.statistical.repository.*;
import com.zhaolong.statistical.service.DateListService;
import com.zhaolong.statistical.service.ExportService;
import com.zhaolong.statistical.service.KeyWordsCodeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  StatisticalApplicationTests {

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

	@Autowired
	private ExportRepository exportRepository;

	@Autowired
	private DataListRepository dataListRepository;

	@Test
	public void keyTest() throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = simpleDateFormat.parse("2018-02-09");
		Date endDate = simpleDateFormat.parse("2018-02-15");
		Sort sort = new Sort(Sort.Direction.DESC, "recordDate");
		Pageable pageable = new PageRequest(0, 1, sort);
		List<ExportInfo> list = exportRepository.getAll(startDate,endDate);
		System.out.println(list);
	}

	@Autowired
	private ExportService exportService;
	@Test
	public void export() throws IOException {
		exportService.exportExcl(new Date(),new Date());
	}

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Test
	public void userInfo(){
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("admin");
		userInfo.setPassword("admin");
//		userInfoRepository.save(userInfo);
	}
}
