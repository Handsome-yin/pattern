package com.pattern.factory.factorymethod;


import com.pattern.factory.ICourse;
import com.pattern.factory.JavaCourse;

/**
 *
 */
public class JavaCourseFactory implements ICourseFactory {
    public ICourse create() {
        return new JavaCourse();
    }
}
