package com.example.demo.model;

import java.util.Date;

// 這是一個 POJO（Plain Old Java Object），也稱為 Java Bean 或資料實體類別（Entity）
// 用來表示一個「學生」的資料結構，通常會對應到資料庫中的一張 student 資料表
public class Student {

    // 主鍵（Primary Key），用來唯一識別每一筆學生資料
    private Integer id;

    // 學生的姓名
    private String name;

    // 學生的成績（可能是小數點，所以用 Double）
    private Double score;

    // 是否已經畢業，布林值 true/false，對應資料庫中的 BOOLEAN 或 TINYINT(1)
    private boolean graduate;

    // 建立資料的時間戳記，代表這筆資料是什麼時候建立的
    private Date createDate;

    // 以下是 getter 與 setter 方法，是 JavaBean 規範的一部分
    // 用來讀取與設定上面這些欄位的值
    // Spring、JPA、JSON 工具都會用這些方法來操作資料欄位


    // 回傳 id 的值（取得主鍵）
    public Integer getId() {
        return id;
    }

    // 設定 id 的值（通常由資料庫自動產生）
    public void setId(Integer id) {
        this.id = id;
    }

    // 回傳學生姓名
    public String getName() {
        return name;
    }

    // 設定學生姓名
    public void setName(String name) {
        this.name = name;
    }

    // 回傳學生的成績
    public Double getScore() {
        return score;
    }

    // 設定學生的成績
    public void setScore(Double score) {
        this.score = score;
    }

    // 回傳是否畢業（布林值）
    // 命名為 isGraduate 是 Java 對 boolean 的 getter 命名慣例
    public boolean isGraduate() {
        return graduate;
    }

    // 設定是否畢業
    public void setGraduate(boolean graduate) {
        this.graduate = graduate;
    }

    // 回傳建立時間
    public Date getCreateDate() {
        return createDate;
    }

    // 設定建立時間
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
