package com.duia.english.model;

import com.duia.english.common.core.CoreEntity;

import javax.persistence.Table;

/**
 * Created by liuhao on 2018/4/2.
 */
@Table(name = "test")
public class DuiaTest extends CoreEntity {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DuiaTest{" +
                "title='" + title + '\'' +
                "} " + super.toString();
    }
}
