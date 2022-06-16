window.addEventListener("load", switchSize)
function switchSize()
{
   var image = document.getElementById('userim')
   if(image.src!=="http://localhost:8080/images/person.png")
   {
        image.classList.remove("default");
        image.classList.add("replace");
   }
}
function profImage() {
    var form = document.getElementById('vim');
    form.submit();
}