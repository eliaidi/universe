/*
 * Copyright 2016 dactiv
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dactiv.universe.entity.validation.test;

import com.github.dactiv.universe.entity.validation.annotation.*;

import java.util.Date;

/**
 * @author maurice
 */
@Valid
public class User {

    @Required
    @Equal("username")
    @NotEqual("password")
    @Length(min = 6, max = 16)
    @RegularExpression("^[a-zA-z][a-zA-Z0-9_]{2,9}$")
    private String username;

    @Required
    @Length(min = 6, max = 16)
    private String password;

    @Required
    @Length(max = 16)
    private String nickname;

    @Required
    @Max(2)
    @Min(0)
    private Integer state;

    @Email
    @Length(max = 64)
    private String email;

    @Required
    @Between(min = "2016-02-05", max = "2017-02-06")
    private Date brithday;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }
}
