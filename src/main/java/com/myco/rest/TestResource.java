package com.myco.rest;

/**
 * Sample test resource
 */

import lombok.extern.slf4j.Slf4j;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestResource {

    @Autowired
    DBI dbi;

    @RequestMapping(method = RequestMethod.POST)
    public String post() {
        return "Successful POST";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        String name;
        Handle handle = dbi.open();
        try {
            name = handle.createQuery("select description from my_test")
                    .map(StringMapper.FIRST)
                    .first();
        } finally {
            handle.close();
        }
        return name;
    }
}
