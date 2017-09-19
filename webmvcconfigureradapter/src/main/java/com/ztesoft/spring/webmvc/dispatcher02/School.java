package com.ztesoft.spring.webmvc.dispatcher02;

import java.io.Serializable;

/**
 * @author tian.lue
 */
public class School implements Serializable {

    private Teacher teacher;
    private Student student;

    public School() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public School(Teacher teacher, Student student) {
        this.teacher = teacher;
        this.student = student;
    }
}
