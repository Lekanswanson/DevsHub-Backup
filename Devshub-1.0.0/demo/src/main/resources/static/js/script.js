var sticky;
var navbar;
var defaultOffset;

window.addEventListener("load", getOffset)
window.addEventListener("fullscreenchange", getOffset)

window.onscroll = function()
{
	myFunction()
}

function getOffset()
{
	var nav = document.getElementById("navbar")
  	var stick = nav.offsetTop

	if(window.pageYOffset == 0)
	{
		defaultOffset = stick
	}

	if(stick == 0)
	{
		sticky = defaultOffset
	}
	else
	{
		sticky = stick
	}
	navbar = nav
}

function myFunction()
{
  	if (window.pageYOffset >= sticky)
  	{
  		navbar.classList.add("sticky")
  	}
  	else
  	{
    	navbar.classList.remove("sticky")
  	}
}


window.addEventListener("pageshow", () => {
  var form = document.getElementById("details")
  if(form !== null)
    form.reset()
});

function openForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("myText");
  element.classList.add("editbox");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}

function closeForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("myText");
  element.classList.remove("editbox");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}

function openEducationForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("eduform");
  element.classList.add("educationbox");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeEducationForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("eduform");
  element.classList.remove("educationbox");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}

function GetElementInsideContainer(containerID, childID) {
  var elm = document.getElementById(childID);
  var parent = elm ? elm.parentNode : {};
  return (parent.id && parent.id === containerID) ? elm : {};
}
function editEducationForm(education) {
    const myArray = education.split(",");
    alert(myArray[0])

    document.getElementById(myArray[0]).value = myArray[0]
    document.getElementById(myArray[1]+'-'+myArray[0]).value = myArray[1]
    document.getElementById(myArray[2]+'-'+myArray[0]).value = myArray[2]
    document.getElementById(myArray[3]+'-'+myArray[0]).value = myArray[3]
    document.getElementById(myArray[4]+'-'+myArray[0]).value = myArray[4]
    document.getElementById(myArray[5]+'-'+myArray[0]).value = myArray[5]
    document.getElementById(myArray[6]+'-'+myArray[0]).value = myArray[6]

    alert(education);
    var element = document.getElementById(education);
    element.classList.add("educationbox");
}

function closeEditForm(id) {
    var element = document.getElementById(id);
    element.classList.remove("educationbox");
}

function setActive()
{
    alert("activated");
    document.getElementById("cv").classList.remove("active");
    document.getElementById("prof").classList.add("active");
    window.location.replace = "http://localhost:8080/cv";
    return false;
}
