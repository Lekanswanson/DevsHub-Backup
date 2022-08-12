function myFunction() {
	var navDiv = document.getElementById("myList");
	if (navDiv.className === "list") {
		navDiv.className += " responsive";
	}
	else {
		navDiv.className = "list";
	}
}

$(function(){

    $("input").focus(function() {
        document.getElementById(this.id).placeholder="";
        var id="";
        if(this.id==="userName")
        {
            id="lbltxt";
        }
        else
        {
            id="passtxt";
        }

        document.getElementById(id).classList.remove("hide");
        document.getElementById(id).classList.add("inputLabelStyle")
        document.getElementById(this.id).classList.add("notEmptyInput");
    });

    $("input").focusout(function() {
        var val = document.getElementById(this.id).value;
        var id="";
        var msg="";

        if(val.trim() === "")
        {
             if(this.id === "userName")
             {
                 id="lbltxt";
                 msg="Enter username or email"
             }
             else
             {
                 id="passtxt";
                 msg="Enter Password";
             }

            document.getElementById(id).classList.add("hide");
            document.getElementById(id).classList.remove("inputLabelStyle");

            document.getElementById(this.id).placeholder=msg;
            document.getElementById(this.id).classList.remove("notEmptyInput");
        }
        else{
            document.getElementById(this.id).classList.add("notEmptyInput");
        }
    });
});

