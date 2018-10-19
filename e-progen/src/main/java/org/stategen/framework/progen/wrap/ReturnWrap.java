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
package org.stategen.framework.progen.wrap;

import org.stategen.framework.lite.SimpleResponse;

/**
 * The Class ReturnWrap.
 */
public class ReturnWrap extends MemberWrap{
    
    
    public Boolean getIsSimpleResponse(){
        return SimpleResponse.class.isAssignableFrom(getClazz());
    }

    public Boolean getIsVoid(){
        return getClazz().equals(Void.TYPE);
    }
    
}
