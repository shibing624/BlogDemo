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

import java.util.Map;

/**
 * 多层环境
 *
 * @author luoguo
 */
public interface Context extends BaseContext {
    /**
     * 创建子上下文
     *
     * @param contextName 上下文名称
     * @return 创建上下文名称对应的子上下文
     */
    Context createSubContext(String contextName);


    /**
     * 从子上下文中删除name的参数信息
     *
     * @param contextName 子环境名称
     * @param name        上下文变量名称
     * @return 子上下文中参数名称为name的参数信息
     */
    <T> T remove(String contextName, String name);

    /**
     * 从指定子环境获取变量
     *
     * @param contextName 子环境
     * @param name        上下文变量名称
     * @return 子上下文中参数名称为name的参数信息
     */
    <T> T getInSubContext(String contextName, String name);

    /**
     * 添加到子环境
     *
     * @param contextName 子环境
     * @param name        上下文变量名称
     * @param object      要放入的数据
     * @return
     */
    <T> T put(String contextName, String name, T object);

    /**
     * 返回父上下文对象
     *
     * @return
     */
    Context getParent();

    /**
     * 设置父上下文对象
     *
     * @param parent 父上下文对象
     */
    void setParent(Context parent);

    /**
     * 添加子环境
     *
     * @param contextName 子环境
     * @param context
     * @return
     */
    Context putSubContext(String contextName, Context context);

    /**
     * 删除子上下文
     *
     * @param contextName 子环境
     */
    Context removeSubContext(String contextName);

    /**
     * 返回子环境
     *
     * @param contextName 子环境
     * @return 子环境
     */
    Context getSubContext(String contextName);

    /**
     * 删除所有子上下文
     */
    void clearSubContext();

    /**
     * 返回所有子上下文组装成的map
     *
     * @return 子环境MAP
     */
    Map<String, Context> getSubContextMap();

    /**
     * 返回当前上下文(不包含子上下文)中所有内容
     *
     * @return 返回当前上下文(不包含子上下文)中所有内容
     */
    Map<String, Object> getItemMap();

    /**
     * 返回上下文(包含子上下文)中所有内容
     *
     * @return 返回上下文(包含子上下文)中所有内容
     */
    Map<String, Object> getTotalItemMap();

}