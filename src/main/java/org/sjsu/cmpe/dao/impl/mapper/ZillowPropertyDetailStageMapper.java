package org.sjsu.cmpe.dao.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import org.sjsu.cmpe.beans.ZillowPropertyDetailStage;

public class ZillowPropertyDetailStageMapper implements RowMapper<ZillowPropertyDetailStage> {

    public ZillowPropertyDetailStage mapRow(ResultSet rs, int rowNum) throws SQLException {
        ZillowPropertyDetailStage zillowPropertyDetailStage = new ZillowPropertyDetailStage();
        zillowPropertyDetailStage.setHomeDescription(rs.getString("home_description"));
        zillowPropertyDetailStage.setImageCount(rs.getInt("image_count"));
        zillowPropertyDetailStage.setPageViewCount(rs.getLong("page_view_count"));
        zillowPropertyDetailStage.setZpid(rs.getLong("zpid"));
        return zillowPropertyDetailStage;
    }
}
