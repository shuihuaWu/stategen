/*
 * Copyright (C) 2018  niaoge<78493244@qq.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.stategen.framework.progen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Class GenContext.
 */
public class GenContext {

    public static String tempRootPath;
    public static String projectRootPath;

    private static Set<Class<?>> ignoreParamClasses = new HashSet<Class<?>>();
    private static Set<Class<? extends Annotation>> ignoreParamAnnoClasses = new HashSet<Class<? extends Annotation>>();

    public static Map<PathType, String> pathMap = new LinkedHashMap<PathType, String>();

    public static WrapContainer wrapContainer = new WrapContainer();

    public static Set<Class<?>> staticUtils = new HashSet<Class<?>>();
    public static String tempDir;
    public static String outDir;
    public static String outConfigDir;

    private GenContext() {
        super();
    }

    public static void registIgnoreParamClz(Class<?> clz) {
        ignoreParamClasses.add(clz);
    }

    public static void addStaticUtil(Class<?> clazz) {
        if (clazz != null) {
            staticUtils.add(clazz);
        }
    }

    public static void registIgnoreParamAnnotationClz(Class<? extends Annotation> clz) {
        ignoreParamAnnoClasses.add(clz);
    }

    public static WrapContainer registSimpleClz(Class<?> dest, String convertTo) {
        wrapContainer.registSimpleClz(dest, convertTo, null);
        return wrapContainer;
    }

    public static WrapContainer registSimpleClz(Class<?> dest, String convertTo, String WholeImportPath) {
        wrapContainer.registSimpleClz(dest, convertTo, WholeImportPath);
        return wrapContainer;
    }

    public static boolean checkIgnore(Parameter parameter) {
        Class<?> paramRawClz = parameter.getType();
        for (Class<?> clz : ignoreParamClasses) {
            if (clz.isAssignableFrom(paramRawClz)) {
                return true;
            }
        }

        for (Class<? extends Annotation> annoClz : ignoreParamAnnoClasses) {
            if (parameter.isAnnotationPresent(annoClz)) {
                return true;
            }
        }

        return false;
    }

    public static Set<Class<?>> getIgnoreParamClasses() {
        return ignoreParamClasses;
    }

    public static void addIgnoreParamClass(Class<?> clz) {
        if (clz != null) {
            ignoreParamClasses.add(clz);
        }
    }

    public static void addIgnoreParamAnnoClasses(Class<? extends Annotation> clz) {
        if (clz != null) {
            ignoreParamAnnoClasses.add(clz);
        }
    }

    public static Set<Class<? extends Annotation>> getIgnoreParamAnnoClasses() {
        return ignoreParamAnnoClasses;
    }

    public static void put(PathType pathType, String importPath) {
        pathMap.put(pathType, importPath);
    }



}
