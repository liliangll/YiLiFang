<html>
	<head>
	<title>student</title>
	</head>
	<body>
	学生信息:<br>
	学号:${student.id}&nbsp;&nbsp;&nbsp;
	姓名:${student.name}&nbsp;&nbsp;&nbsp;
	年龄:${student.age}&nbsp;&nbsp;&nbsp;
	地址:${student.address}&nbsp;&nbsp;&nbsp;
	学生列表:
	<table border="1">
		<tr>
			<td>序号</td>
			<td>学号</td>
			<td>姓名</td>
			<td>年龄</td>
			<td>家庭住址</td>
		</tr>
		<#list stuList as stu>
		<#if stu_index%2==0>	
		<tr bgcolor="red">
			<#else>
		<tr bgcolor="green">
		</#if>
			<td>${stu_index}</td>
			<td>${stu.id}</td>
			<td>${stu.name}</td>
			<td>${stu.age}</td>
			<td>${stu.address}</td>
		</tr>
		</#list>
	</table>
	<!--可以使用？date,?time,?datetime,?string(patten)-->
	当前日期:${date?string("yyyy/MM/dd HH:mm:ss")}<br>
	null值得处理:${val!"val的值为null"}<br>
	判断val的值是否为null:<br>
	<#if val??>
	val中有内容
	<#else>
	val为null
	</#if>
	</body>
</html>