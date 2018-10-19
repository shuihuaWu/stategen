/*
 * copy from rapid-framework<code.google.com/p/rapid-framework> and modify by niaoge
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
package cn.org.rapid_framework.generator.ext.tableconfig.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.stategen.framework.util.StringUtil;

import cn.org.rapid_framework.generator.provider.db.table.TableFactory;
import cn.org.rapid_framework.generator.provider.db.table.TableFactoryListener;
import cn.org.rapid_framework.generator.provider.db.table.model.Table;
import cn.org.rapid_framework.generator.util.GLogger;

public class TableConfigSet implements Iterable<TableConfig>,TableFactoryListener{
    private Set<TableConfig> tableConfigs = new LinkedHashSet<TableConfig>();
    
    private String _package;
    
    public TableConfigSet() {
        
        //增加监听器,用于table的自定义className
        TableFactory tf = TableFactory.getInstance();
        tf.addTableFactoryListener(this);
    }
    
    public String getPackage() {
        return _package;
    }

    public void setPackage(String _package) {
        this._package = _package;
        setPackageForTableConfigs();
    }
    
    public Set<String> getSequences() {
        Set<String> result = new LinkedHashSet<String>();
        for(TableConfig c : tableConfigs) {
            if(StringUtil.isNotBlank(c.getSequence())) {
                result.add(c.getSequence());
            }
        }
        return result;
    }
    
    public void addTableConfig(TableConfig t) {
        setPackageIfBlank(t);
        tableConfigs.add(t);
    }
    
    public Set<TableConfig> getTableConfigs() {
        return Collections.unmodifiableSet(tableConfigs);
    }

    public void setTableConfigs(Set<TableConfig> tableConfigs) {
        this.tableConfigs = tableConfigs;
        setPackageForTableConfigs();
    }

    private void setPackageForTableConfigs() {
        for(TableConfig t : this.tableConfigs) {
            setPackageIfBlank(t); //FIXME 需要检查,如果 tableConfig.package为空,才可以设置,不然会覆盖 tableConfig已有的值
        }
    }

    private void setPackageIfBlank(TableConfig t) {
        if(StringUtil.isBlank(t.getPackage())) {
            t.setPackage(getPackage());
        }
    }

    public TableConfig getRequiredBySqlName(String sqlName) {
        TableConfig tc = getBySqlName(sqlName);
        if(tc == null) {
            throw new IllegalArgumentException("not found TableConfig on TableConfigSet by sqlName:"+sqlName);
        }
        return tc;
    }
    
    public TableConfig getBySqlName(String sqlName) {
        for(TableConfig c : tableConfigs) {
            if(sqlName.equalsIgnoreCase(c.getSqlName())) {
                return c;
            }
        }
        return null;
    }
    
    public TableConfig getByClassName(String name) {
        for(TableConfig c : tableConfigs) {
            if(name.equals(c.getClassName())) {
                return c;
            }
        }
        return null;
    }

    public Iterator<TableConfig> iterator() {
        return tableConfigs.iterator();
    }

    public void onTableCreated(Table table) {
        TableConfig tc = getBySqlName(table.getSqlName());
        if(tc == null) return;
        tc.customTable(table);
//        table.setClassName(tc.getClassName());
//        if(StringUtil.isNotBlank(table.getRemarks())) {
//            table.setTableAlias(table.getRemarks());
//        }
        
        //FIXME 还没有考虑TableConfigSet 的listener清除问题,
        //TODO 增加单元测试
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        GLogger.warn("没有手工清除TableFactoryListener for TableConfigSet");
        TableFactory.getInstance().removeTableFactoryListener(this);
    }
}
