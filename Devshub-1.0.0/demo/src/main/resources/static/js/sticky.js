


$(function(){
    function expandArea(id)
    {
        var textarea = document.getElementById("dcomments_")+id;
        var limit = 2000;
        var savecount=0;

        textarea.oninput = function()
        {
            var message="";
            var count=textarea.value.length;

            count = count - savecount;
            if(count === 26)
            {
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+30, limit) + "px";
                console.log("dang")
            }
            else if(count===51)
            {
                message += textarea.value;
                savecount = savecount + count;
            }

            if(textarea.value.length < savecount)
            {
                savecount=savecount-51;
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+30, limit) + "px";
            }
            if(textarea.value.length===0)
            {
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+2, limit) + "px";
            }
        };
    }
});
