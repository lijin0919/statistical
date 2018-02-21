package com.zhaolong.statistical.repository;

import com.zhaolong.statistical.entity.ExportInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class ExportRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ExportInfo> getAll(Date start,Date end){
        return jdbcTemplate.query("SELECT\n" +
                "  keywords_code.key_words AS keyword,\n" +
                "  sum(click_count) AS click,\n" +
                "  sum(dialogue_count) AS dialog,\n" +
                "  sum(phone_count) AS phone,\n" +
                "  sum(registered_count) AS register,\n" +
                "  sum(show_times) AS showtimes,\n" +
                "  sum(spend_money) AS money,\n" +
                "  sum(visit_count) AS visit\n" +
                "FROM keywords_record,keywords_code\n" +
                "WHERE record_date BETWEEN\n" +
                "  ? AND ?\n" +
                "      AND keywords_code.code_id = keywords_code_code_id\n" +
                "GROUP BY keywords_code_code_id;",
                new Object[]{start,end},new ExportRowMapper());
    }

    class ExportRowMapper implements RowMapper<ExportInfo>{

        @Override
        public ExportInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            ExportInfo exportInfo = new ExportInfo();
            exportInfo.setKey(resultSet.getString("keyword"));
            exportInfo.setVisit(resultSet.getInt("visit"));
            exportInfo.setTotalMoney(resultSet.getDouble("money"));
            exportInfo.setRegister(resultSet.getInt("register"));
            exportInfo.setPhone(resultSet.getInt("phone"));
            exportInfo.setDialog(resultSet.getInt("dialog"));
            exportInfo.setClick(resultSet.getInt("click"));
            exportInfo.setShow(resultSet.getInt("showtimes"));
            return exportInfo;
        }
    }

}
