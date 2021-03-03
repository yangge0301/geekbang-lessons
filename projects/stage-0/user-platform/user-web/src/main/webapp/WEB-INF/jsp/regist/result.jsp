<head>
    <jsp:directive.include
            file="/WEB-INF/jsp/prelude/include-head-meta.jspf" />
    <title>My Home Page</title>
    <style>
        html,body{
            margin:0;padding: 0;
            width:100%;height:100%;

        }
        .container-lg{
            width:100%;height:100%;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size:30px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container-lg">
    <!-- Content here -->
    <p><%=request.getAttribute("msg") %></p>
</div>
</body>
