/**
 * Copyright (c) 2012-2016, www.tinygroup.org (luo_guo@icloud.com).
 * <p>
 * Licensed under the GPL, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/gpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package xm.ruleengine.selfengine;

import java.io.Serializable;
import java.util.Map;

/**
 * 普通的环境，不支持多层嵌套
 *
 * @author luoguo
 */
public interface BaseContext extends Serializable {
    /**
     * 添加参数信息到上下文中
     *
     * @param name   参数名称
     * @param object 参数值
     */
    <T> T put(String name, T object);

    /**
     * 把环境中某键值的名字换成另外的名字
     *
     * @param key    旧的参数名称
     * @param newKey 新的参数名称
     * @return
     */
    boolean renameKey(String key, String newKey);

    /**
     * 移除参数名称对应的参数值
     *
     * @param name 参数名称
     * @return 返回参数名称对应的参数值
     */
    <T> T remove(String name);

    /**
     * 获取对象，如果当前环境中没有，则到子环境当中寻找
     *
     * @param name 参数名称
     * @return 获取参数名称对应的参数值
     */
    <T> T get(String name);

    /**
     * 存储参数值
     *
     * @param map 多个参数组成的map数据结构
     */
    void putAll(Map<String, Object> map);

    /**
     * 获取参数名称对应的参数值，如果参数值不存在，那么返回默认值
     *
     * @param name         参数名称
     * @param defaultValue 默认值
     * @return 返回参数值
     */
    <T> T get(String name, T defaultValue);

    /**
     * 返回上下文中的参数数量
     *
     * @return
     */
    int size();

    /**
     * 检测参数是否存在
     *
     * @param name 参数名称
     * @return 返回是否存在
     */
    boolean exist(String name);

    /**
     * 如果上下文存在参数名称对应的参数信息，那么返回当前实例,不存在则返回null
     *
     * @param name 参数名称
     * @return 存在返回this, 不存在返回null
     */
    BaseContext contain(String name);

    /**
     * 删除上下文中的所有参数信息
     */
    void clear();

    /**
     * 返回上下文中所有参数
     *
     * @return
     */
    Map<String, Object> getItemMap();

}