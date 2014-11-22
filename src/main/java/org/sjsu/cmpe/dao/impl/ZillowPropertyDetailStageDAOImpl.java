package org.sjsu.cmpe.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.sjsu.cmpe.beans.ZillowPropertyDetailStage;
import org.sjsu.cmpe.dao.impl.mapper.ZillowPropertyDetailStageMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ZillowPropertyDetailStageDAOImpl {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<ZillowPropertyDetailStage> getAllData() {
        String SQL = "SELECT * FROM zillowdb.zillowproperty_detail_stage";

        List<ZillowPropertyDetailStage> result = jdbcTemplate.query(SQL, new ZillowPropertyDetailStageMapper());
        return result;
    }

    public int getAllDataSize() {
        String SQL = "SELECT * FROM zillowdb.zillowproperty_detail_stage";

        List<ZillowPropertyDetailStage> result = jdbcTemplate.query(SQL, new ZillowPropertyDetailStageMapper());
        return result.size();
    }

    public int getDescriptionRowSizeByKeyWord(String key) {
        String SQL = "SELECT * FROM zillowdb.zillowproperty_detail_stage WHERE home_description LIKE '%" + key + "%';";

        List<ZillowPropertyDetailStage> result = jdbcTemplate.query(SQL, new ZillowPropertyDetailStageMapper());
        return result.size();
    }

}
