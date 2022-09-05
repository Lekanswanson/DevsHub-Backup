

function expandTextArea(id)
{
        var textarea = document.getElementById(id);
        var limit = 2000;
        var savecount=0;

        this.oninput = function()
        {
            var message="";
            var count=textarea.value.length;

            count = count - savecount;
            if(count == 26)
            {
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+30, limit) + "px";
                console.log("dang")
            }
            else if(count==51)
            {
                message += textarea.value;

                enteredText = textarea.value;
                if(enteredText.match(/\n/g)||[])
                {
                    //alert("Break");
                }
                savecount = savecount + count;
            }

            if(textarea.value.length < savecount)
            {
                savecount=savecount-51;
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+30, limit) + "px";
            }
            if(textarea.value.length==0)
            {
                textarea.style.height = "";
                textarea.style.height = Math.min(textarea.scrollHeight+2, limit) + "px";
            }
        };
}

