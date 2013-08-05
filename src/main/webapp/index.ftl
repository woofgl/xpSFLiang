<script type="text/javascript">
$(function(){
    var url;
    $.get("authorize", function(result){
        console.log(url);
        url = url;
        return;
    })

    $.ajax({
        type: "GET",
        url: "authorize",
        dataType: "html",
        success: function(result){
            console.log(result);
            window.location.href = result;
        }
    });
});  
</script>
