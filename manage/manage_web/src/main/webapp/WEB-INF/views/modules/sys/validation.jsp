<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <script type="text/javascript">
        //动态口令验证
        $(document).ready(function () {
            $(".checkPass").on("click", function () {
                var url = $(this).attr("value-url");
                var key = url.substring(0, url.lastIndexOf("/"));
                $.post("${ctx}/util/dynamic/validation", {"url": key},
                    function (data) {
                        if (data == "true") {
                            window.location.href = url;
                        } else {
                            parent.showDynamicPa();
                            parent.enterDynamicPa(url);
                        }
                    });
            })
        });
    </script>
</head>
</html>