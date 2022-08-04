function myFunction() {
	var navDiv = document.getElementById("myList");
	if (navDiv.className === "list") {
		navDiv.className += " responsive";
	}
	else {
		navDiv.className = "list";
	}
}