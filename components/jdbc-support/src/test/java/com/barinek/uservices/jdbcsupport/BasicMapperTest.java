package com.barinek.uservices.jdbcsupport;

import com.barinek.uservices.schema.TestDataSource;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

public class BasicMapperTest {
    @Test
    public void testFind() throws Exception {
        BasicMapper template = new BasicMapper(TestDataSource.getDataSource());

        int id = 42;
        String sql = "select id, name from (select 42 as id, 'apples' as name) as dates where id = ?";

        List<String> names = template.find(sql, rs -> {
            return rs.getString(2);
        }, id);
        TestCase.assertEquals("apples", names.get(0));
    }
}