window.addEventListener("load", switchSize)
function switchSize()
{
   var image = document.getElementById('userim')
   if(image.src!=="http://"+window.location.host+"/images/person.png")
   {
        image.classList.remove("default");
        image.classList.add("replace");
   }
}
function profImage() {
    var form = document.getElementById('vim');
    form.submit();
}

function showcolorsProfile(){
    var colorpanel = document.getElementById('colorpan');
    var emptypanel = document.getElementById('emptypan');

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
function changeBackGroundColour(color)
{
    document.getElementById('profcolor').style.backgroundColor=color;
}