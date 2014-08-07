/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 the original author or authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.jdev.geb.cucumber.core.util

import cucumber.runtime.io.ClasspathResourceLoader
import cucumber.runtime.io.Resource

class ClassUtil {

    public static <T> Map<String, List<Class<? extends T>>> scanForClassesOfType(Class<T> targetClass, List<String> packageNames) {
        return scanForClassesOfType(targetClass, packageNames, ClassUtil.classLoader)
    }

    public static <T> Map<String, List<Class<? extends T>>> scanForClassesOfType(Class<T> targetClass, List<String> packageNames, ClassLoader classLoader) {
        Map<String, List<Class<? extends T>>> classNameToClassMap = [:]
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(classLoader)
        for(String packageName : packageNames) {
            String prefix = packageName.replace('.' as char, '/' as char)
            for(Resource resource in resourceLoader.resources(prefix, '.class')) {
                String className = resource.getClassName('.class')
                Class<?> cls = classLoader.loadClass(className)
                if(!targetClass.isAssignableFrom(cls)) {
                    // not a geb page, whatever the class name is hinting
                    continue
                }
                String simpleClassName = cls.simpleName
                List<Class<? extends T>> classesWithSimpleName = classNameToClassMap[simpleClassName]
                if(!classesWithSimpleName) {
                    classNameToClassMap[simpleClassName] = [cls]
                } else {
                    classesWithSimpleName.add(cls as Class<? extends T>)
                }
            }
        }
        classNameToClassMap
    }
}
