package com.example.demo.service;

import com.example.demo.dao.StudentDao;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// 告訴 Spring 這是一個元件（類別），會自動被掃描並註冊為 Bean
@Component
public class StudentServiceImpl implements StudentService {

    // 自動注入 StudentDao，讓我們可以使用它來存取資料庫
    @Autowired
    private StudentDao studentDao;

    // 實作介面中的 insert 方法，將學生資料新增到資料庫中
    @Override
    /*
        student 是這個方法的「參數」，它的型別是 Student，定義在方法括號內：
     */
    public Integer insert(Student student) {
        return studentDao.insert(student);
    }

    @Override
    public void update(Student student) {
        studentDao.update(student);
    }

    @Override
    public void deleteById(Integer id) {
        studentDao.deleteById(id);
    }

    @Override
    public Student getById(Integer id) {
        return studentDao.getById(id);
    }
}
