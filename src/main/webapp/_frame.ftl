<html>
  <head>
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/bootstrap/css/icon.css">
    [#--<link rel="stylesheet" type="text/css" href="${_r.contextPath}/bootstrap/css/bootstrap-responsive.css">--]
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/ciscobootstrap/cisco-bootstrap.css">
    <title>CTG Software Release Tool</title>
    [@webBundle path="${_r.contextPath}/css/" type="css" /]
    
    
    [@webBundle path="${_r.contextPath}/js/" type="js" /]
    <script type="text/javascript">
    var user = null;
      [#if _r.user??]
      user = {
        id: ${_r.user.id},
        username: "${_r.user.username}",
        admin: ${_r.user.admin?string("true","false")}
      }
      [/#if]
    </script>
  </head>
  
  <body class="">
    [@includeFrameContent /]
  </body>
</html>