package com.example.myrestfulservice.controller;

import com.example.myrestfulservice.bean.AdminUser;
import com.example.myrestfulservice.bean.AdminUserV2;
import com.example.myrestfulservice.bean.User;
import com.example.myrestfulservice.dao.UserDaoService;
import com.example.myrestfulservice.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    // "/admin/v1/users/{id}"
//    @GetMapping("/v1/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=1")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUser4Admin(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();
        // 조회내용이 없을 경우, exception 처리
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // 프로퍼티 복사 user -> adminUser (같은 필터 끼리)
            BeanUtils.copyProperties(user, adminUser);
        }

        // 사용 제한 JSON 객체에서 필요한 것만 출력 필터 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // UserInfo에 대한 필터 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // return Object 생성 그리고 filter된 내용 input
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    // "/admin/v2/users/{id}"
//    @GetMapping("/v2/users/{id}")
//    @GetMapping(value = "/users/{id}", params = "version=2")
//    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id) {
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();
        // 조회내용이 없을 경우, exception 처리
        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            // 프로퍼티 복사 user -> adminUser (같은 필터 끼리)
            BeanUtils.copyProperties(user, adminUser);
            adminUser.setGrade("VIP"); // grade 추가
        }

        // 사용 제한 JSON 객체에서 필요한 것만 출력 필터 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        // UserInfo에 대한 필터 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // return Object 생성 그리고 filter된 내용 input
        MappingJacksonValue mapping = new MappingJacksonValue(adminUser);
        mapping.setFilters(filters);

        return mapping;
    }

    // "/admin/users"
    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUser4Admin() {
        List<User> users = service.findAll();

        List<AdminUser> adminUsers = new ArrayList<AdminUser>();
        AdminUser adminUser = null;

        for (User user : users) {
            adminUser = new AdminUser();
            BeanUtils.copyProperties(user, adminUser);

            adminUsers.add(adminUser);
        }

        // 사용 제한 JSON 객체에서 필요한 것만 출력 필터 생성
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // UserInfo에 대한 필터 적용
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // return Object 생성 그리고 filter된 내용 input(List)
        MappingJacksonValue mapping = new MappingJacksonValue(adminUsers);
        mapping.setFilters(filters);

        return mapping;
    }
}
