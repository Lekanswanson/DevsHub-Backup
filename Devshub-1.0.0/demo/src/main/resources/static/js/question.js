var sticky;
var navbar;
var defaultOffset;

window.onscroll = function()
{
	myFunction();
}
function myFunction()
{
    //let viewportHeight = window.innerWidth;
    //alert(viewportHeight);

  	if (window.pageYOffset >= sticky - 215)
  	{
        document.getElementById("leftInfo").classList.add("stickyLeft");
        document.getElementById("rightInfo").classList.add("stickyRight");
        document.getElementById("chooseContent").classList.add("stickyCenter");
//        document.getElementById("navbar").classList.add("resetNav");
//        document.getElementsByClassName("flexlist")[0].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[1].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[2].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[3].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[4].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[5].classList.add("resetList");
//        document.getElementsByClassName("flexlist")[6].classList.add("resetList");
//        document.getElementById("stop").classList.add("resetDiv");
//        document.getElementById("stop").classList.remove("stop");
        document.getElementById("articles").classList.add("flow");

  	}
  	else
  	{
        document.getElementById("leftInfo").classList.remove("stickyLeft");
        document.getElementById("rightInfo").classList.remove("stickyRight");
        document.getElementById("chooseContent").classList.remove("stickyCenter");
        document.getElementById("articles").classList.remove("flow")
  	}
}

window.addEventListener("load", getOffset)
window.addEventListener("fullscreenchange", getOffset)

function getOffset()
{
	var nav = document.getElementById("backLayout");
  	var stick = nav.offsetTop;

	if(window.pageYOffset == 0)
	{
		defaultOffset = stick
	}

	if(stick == 0)
	{
		sticky = defaultOffset;
	}
	else
	{
		sticky = stick;
	}
	navbar = nav
}