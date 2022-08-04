window.addEventListener("load", switchSize)
function switchSize()
{
   var image = document.getElementById('userim')
   if(image.src!=="https://"+window.location.host+"/images/person.png")
   {
        image.classList.remove("default");
        image.classList.add("replace");
   }
}
function profImage() {
    var form = document.getElementById('vim');
    form.submit();
}

function showcolors(id, ident){
    var colorpanel = document.getElementById(id);
    var emptypanel = document.getElementById(ident);

    if(!emptypanel.classList.contains('hide'))
    {
        emptypanel.classList.remove("show");
        emptypanel.classList.add("hide");

        colorpanel.classList.remove('hide')
        colorpanel.classList.add("show");
    }
    else
    {
        emptypanel.classList.remove("hide");
        emptypanel.classList.add("show");

        colorpanel.classList.remove('show')
        colorpanel.classList.add("hide");
    }
}
function setColor(color)
{
    document.getElementById('setcp').value=color;
    document.getElementById('colorpanelprof').submit();
}


function changeBackGroundColour(color)
{
    document.getElementById('profcolor').style.backgroundColor=color;
}


function showCommentsDiv(id)
{
    if(document.getElementById("usercomment_"+id).classList.contains("hide"))
    {
        document.getElementById("usercomment_"+id).classList.add("comments");
        document.getElementById("usercomment_"+id).classList.remove("hide");
    }
    else
    {
        document.getElementById("usercomment_"+id).classList.add("hide");
        document.getElementById("usercomment_"+id).classList.remove("comments");
    }
}
