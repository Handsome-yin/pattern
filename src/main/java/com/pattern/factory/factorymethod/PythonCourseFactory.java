package com.pattern.factory.factorymethod;


import com.pattern.factory.ICourse;
import com.pattern.factory.PythonCourse;

/**
 *
 */
public class PythonCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new PythonCourse();
    }
}
