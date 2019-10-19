<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Get Employees</title>
</head>
<body>
    <h3 style="color: red;">Third Party Client</h3>

    <div id="getEmployees">
        <form:form action="http://localhost:8080/oauth/authorize?response_type=code&client_id=thirdPartyClient"
            method="post" modelAttribute="emp">
            <p>
                <!-- <label>Enter Employee Id</label>
                 <input type="text" name="response_type" value="code" readonly="readonly"/> 
                 <input type="text" name="client_id" value="thirdPartyClient" readonly="readonly" />
                 <input type="text" name="redirect_uri" value="http://localhost:8090/showEmployees" />
                 <input type="text" name="scope" value="read" />   -->
                 <input type="SUBMIT" value="Get Employee info" />
        </form:form>
    </div>
</body>
</html>
