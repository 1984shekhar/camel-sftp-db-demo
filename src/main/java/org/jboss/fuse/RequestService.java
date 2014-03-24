package org.jboss.fuse;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class RequestService {

    private String response = "";

    private DataSource dataSource;

    public void create(@Body String message, @Header("code") String code, @Header("id") Integer id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO REQUEST (id, code, message, response) VALUES(?,?,?,?)",
                new Object[] { id, code, message, response });
    }

    public void update(@Body String response, @Header("id") Integer id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE REQUEST set response = ? where id = ?",
                new Object[] { response, id });
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
