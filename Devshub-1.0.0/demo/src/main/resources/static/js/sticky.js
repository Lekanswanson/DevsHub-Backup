var sticky;
var navbar;
var defaultOffset;

window.onscroll = function()
{
	myFunction()
}

window.addEventListener("load", getOffset)
window.addEventListener("fullscreenchange", getOffset)

function getOffset()
{
	var nav = document.getElementById("navbar");
  	var stick = nav.offsetTop;

	if(window.pageYOffset == 0)
	{
		defaultOffset = stick;
	}
	  
	if(stick == 0)
	{
		sticky = defaultOffset;
	}
	else
	{
		sticky = stick;
	}
}

function myFunction() 
{

  	if (window.pageYOffset >= sticky) 
  	{
  		nav.classList.add("sticky")
  	} 
  	else
  	{
    	nav.classList.remove("sticky")
  	}
}