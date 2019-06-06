# thymeleaf常用语法
### 变量
#### 显示变量

> 使用${...}来表示一个变量
~~~html
<p th:text="${value1}">value1 </p>
~~~
#### 设置变量

### 表示字符串

> 使用 + 或者 || 
~~~html
<p th:text="'hello! '+${value1}+'!'">hello! vvv1</p>
<p style='color:red' th:text="|hello! ${value1}|">hello! vvv1</p>
~~~
### 获取对象属性的两种方式

~~~
<p th:text="${currentProduct.name}" ></p>
<p th:text="${currentProduct.getName()}" ></p>

//使用 *{} 方式显示当前对象的属性
<p th:text="*{name}" ></p>
~~~
### 算数运算

~~~
<p th:text="${currentProduct.price+999}" ></p>
~~~

### include

创建函数 (fragment)
~~~html
<html xmlns:th="http://www.thymeleaf.org">
<footer th:fragment="footer1">
    <p >All Rights Reserved</p>
</footer>
<footer th:fragment="footer2(start,now)">
    <p th:text="|${start} - ${now} All Rights Reserved|"></p>
</footer>
</html>
~~~
调用 th:replace="文件名::functionName" (replace and insert，二者区别是replace不保留自己的主标签)
~~~html
<div class="showing">
    <div th:replace="include::footer1" >replace</div>
    <div th:replace="include::footer2(2015,2018)" >replace</div>
    <div th:insert="include::footer1" >insert</div>
    <div th:insert="include::footer2(2015,2018)" >insert</div>
</div>
~~~
### 条件判断

th:if th:unless以及三相表达式 
~~~html
<p th:if="${not flag}">if the flag is not  true,print this message.</p>
<p th:unless="${flag}">if the flag is false,print this message</p>
<p th:text="${flag}?'the flag is true' : 'the flag is false '" ></p>
~~~

### 遍历

使用th:each遍历
~~~html
<table>
<thread>
    <tr>
        <th>id</th>
        <th>产品名称</th>
        <th>价格</th>
    </tr>
</thread>
<tbody>
    <tr th:each="p: ${color}">
        <td th:text="${p}"></td>
    </tr>
</tbody>
</table>
~~~
带状态的遍历
~~~html
<div class="showing">
    <h2>带状态遍历</h2>
        <table  th:each="p,status: ${colors}">
            <tr  th:text="|index:${status.index} count:${status.count} size:${status.size} value:${p} first:${status.first}|"></tr>
        </table>
</div>
~~~
select
~~~html
<div class="showing">
    <h2>遍历 select </h2>
 
    <select size="3">
        <option th:each="p:${colors}" th:value="${p}"  th:text="${p}" ></option>
    </select>
 
</div>
~~~
结合单选框
~~~html
<div class="showing">
    <h2>遍历 radio </h2>
    <input name="product" type="radio" th:each="p:${colors}" th:text="${p}"  />
</div>
~~~
# Ioc
# AoP
面向切面
