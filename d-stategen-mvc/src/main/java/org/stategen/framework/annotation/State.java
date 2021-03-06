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
package org.stategen.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.stategen.framework.enums.DataOpt;

/**
 * The Interface State.
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@GenForm()
@GenEffect()
@GenReducer()
@GenRefresh()
@Inherited
public @interface State {

    Class<?> area() default Object.class;

    DataOpt dataOpt() default DataOpt.APPEND_OR_UPDATE;

    boolean init() default false;

    /*** 检查路径和是否打开过 */
    boolean initCheck() default true;
    
    @AliasFor(annotation = GenReducer.class,attribute="value")
    boolean genReducer() default true;
    
    @AliasFor(annotation = GenEffect.class,attribute="value")
    boolean genEffect() default true;
    
    @AliasFor(annotation = GenForm.class,attribute="value")
    boolean genForm() default false;
    
    @AliasFor(annotation = GenRefresh.class,attribute="value")
    boolean genRefresh() default false;
    
    

}
