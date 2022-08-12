var currentUserView="";


function showMessageMobile()
{
    var user = document.getElementById("namesearch").value;
    document.getElementById("rcvname").value=user;

    var x = window.matchMedia("(max-width: 700px)");
    if(x.matches)
    {
        document.getElementById("formmobile").classList.remove("hide");

        document.getElementById("myView").classList.add("messageDivShow");
        document.getElementById("myView").classList.remove("memberMessageDiv");

        document.getElementById("memberNameView").classList.add("memberNameHide");
        document.getElementById("memberNameView").classList.remove("memberNameDiv");
    }
}
function hideMessageMobile()
{
    document.getElementById("myView").classList.remove("messageDivShow");
    document.getElementById("myView").classList.add("memberMessageDiv");

    document.getElementById("memberNameView").classList.add("memberNameDiv");
    document.getElementById("memberNameView").classList.remove("memberNameHide");
    document.getElementById("memberNameView").classList.remove("hide");

    document.getElementById("formmobile").classList.add("hide");
}


$(function(){
    $('#namesearch').on('input', function() {
        const nameDiv = document.getElementById("showMems").replaceChildren();
        $.ajax({
            url: "https://"+window.location.host+"/list/members",
            type: "GET",
            dataType: "text",
            data: {
                "email": document.getElementById("namesearch").value
            },
            success: function(data) {
                if(data==="")
                {
                    const nameDiv = document.getElementById("showMems");
                    nameDiv.classList.remove("display");
                    nameDiv.classList.add("hide");
                }
                else
                {
                    const arr = data.split("_");
                    for(var i=0; i<arr.length; i++)
                    {
                        if(arr[i].length !== 0)
                        {
                            createNameDiv(arr[i].trim(), "showMems","namesearch");
                        }
                    }
                }
            }
        });
    });
});

$(function(){
    document.getElementById("formmobile").classList.add("hide");
});

$(function(){
    var textarea = document.getElementById("hmsg");
    var limit = 64;

    textarea.oninput = function() {
      textarea.style.height = "";
      textarea.style.height = Math.min(textarea.scrollHeight, limit) + "px";
    };
});
$(function(){
    var textarea = document.getElementById("dmsg");
    var limit = 64;

    textarea.oninput = function() {
      textarea.style.height = "";
      textarea.style.height = Math.min(textarea.scrollHeight, limit) + "px";
    };
});


$(function(){
    $('#rcvname').on('input', function() {
        const nameDiv = document.getElementById("showNames").replaceChildren();
        $.ajax({
            url: "https://"+window.location.host+"/list/members",
            type: "GET",
            dataType: "text",
            data: {
                "email": document.getElementById("rcvname").value
            },
            success: function(data) {
                if(data==="")
                {
                    const nameDiv = document.getElementById("showNames");
                    nameDiv.classList.remove("display");
                    nameDiv.classList.add("hide");
                }
                else
                {
                    const arr = data.split("_");
                    for(var i=0; i<arr.length; i++)
                    {
                        if(arr[i].length !== 0)
                        {
                            createNameDiv(arr[i].trim(), "showNames", "rcvname");
                        }
                    }
                }
            }
        });
    });
});

function createNameDiv(name, id, tt)
{
    const nameDiv = document.getElementById(id);

    nameDiv.classList.remove("hide");
    nameDiv.classList.add("display");

    var newDiv = document.createElement("DIV");
    var userName = document.createElement("A");
    var text = document.createTextNode(name);

    userName.onclick = function(){
        document.getElementById(tt).value=name;
        nameDiv.classList.remove("display");
        nameDiv.classList.add("hide");
    }

    userName.appendChild(text);
    newDiv.appendChild(userName);
    nameDiv.appendChild(newDiv);
}


$(function(){
    getPerson();
});


function myFunction(x) {
  if (x.matches) { // If media query matches
    document.getElementById("memberNameView").classList.add("hide");
    document.getElementById("formmobile").classList.remove("hide");
  } else {
    document.getElementById("memberNameView").classList.remove("hide");
    document.getElementById("formmobile").classList.add("hide");
  }
}
function showMessageDiv(user){
    const currentDiv = document.getElementsByClassName("buttclass");

    for(var i=0; i<currentDiv.length; i++)
    {
        var name = currentDiv[i].textContent;
        if(name.trim() === user)
        {
            try
            {
                document.getElementById("rcvname").value = user;
                document.getElementById(user).classList.remove("hide");
                document.getElementById(user).classList.add("show");

                document.getElementById("myView").classList.add("messageDivShow");

                var x = window.matchMedia("(max-width: 700px)");
                myFunction(x);
            }
            catch(error)
            {
                console.log(error);
            }
        }
        else
        {
            if(name.trim() !== "")
            {
                try
                {
                    document.getElementById(name.trim()).classList.remove("show");
                    document.getElementById(name.trim()).classList.add("hide");
                }
                catch(error)
                {
                    console.log(error);
                }
            }
        }
    }
}

function getPerson()
{
    //alert(window.location.host);
    $.ajax({
        url: "https://"+window.location.host+"/user/email",
        type: "GET",
        dataType: "text",
        success: function(data) {
            document.getElementById("sndname").value=data;
        }
    });
}

function getTextAreaName(x)
{
  if (x.matches) { // If media query matches
    return "hmsg";
  } else {
    return "dmsg";
  }
}
function postform()
{
    var x = window.matchMedia("(max-width: 700px)");

    var sender = document.getElementById("sndname").value;
    var receiver = document.getElementById("rcvname").value;
    var message = document.getElementById(getTextAreaName(x)).value;

    $.ajax({
        url: "https://"+window.location.host+"/user/createMessage",
        type: "GET",
        dataType: "text",
        data: {
            "sender": sender,
            "receiver": receiver,
            "message": message
        },
        success: function(data)
        {
            document.getElementById(getTextAreaName(x)).style.height = "";
            document.getElementById(getTextAreaName(x)).value = "";

            if(data)
            {
                var exists = 'false';
                const currentDiv = document.getElementsByClassName("buttclass");

                if(currentDiv.length < 1)
                {
                    createUserDiv(receiver);
                    newMessage(data.split(",")[2].split(":")[1].trim(), data, "flow", "imgflow");
                }
                else
                {
                    for(var i=0; i<currentDiv.length; i++)
                    {
                        var name = currentDiv[i].textContent;

                        if(name.trim() === data.split(",")[2].split(":")[1].trim())
                        {
                            exists='true';
                            addMessage(data.split(",")[2].split(":")[1].trim(), data, "flow", "imgflow");
                            document.getElementById(name.trim()).classList.add("show");
                            document.getElementById(name.trim()).classList.remove("hide");
                        }
                        else
                        {
                            document.getElementById(name.trim()).classList.remove("show");
                            document.getElementById(name.trim()).classList.add("hide");
                        }
                    }
                
                    if(exists=='false')
                    {
                        const parent = document.getElementById("currView");
                        try
                        {
                            document.getElementById(parent.lastChild.id).classList.remove("show");
                            document.getElementById(parent.lastChild.id).classList.add("hide");
                        }
                        catch(error)
                        {
                            console.log(error);
                        }
                        currentUserView=data.split(",")[2].split(":")[1].trim();
                        createUserDiv(currentUserView);
                        newMessage(data.split(",")[2].split(":")[1].trim(), data, "flow", "imgflow");
                    }
                }
            }
        }
    });
}

$(function pollReceiver() {
    setTimeout(function() {
        $.ajax({
            url: "https://"+window.location.host+"/receiver/inbox",
            type: "GET",
            success: function(data)
            {
                if(data && document.getElementById("sndname").value.trim() !== data.split(",")[1].split(":")[1].trim())
                {
                    document.getElementById("notifications").classList.add("unread");

                    var exists = 'false';
                    const currentDiv = document.getElementsByClassName("buttclass");

                    if(currentDiv.length < 1)
                    {
                        createUserDiv(data.split(",")[1].split(":")[1]);
                        newMessage(data.split(",")[1].split(":")[1].trim(), data, "flowright","imgright");
                    }
                    else
                    {
                        for(var i=0; i<currentDiv.length; i++)
                        {
                            var name = currentDiv[i].textContent;

                            if(name.trim() === data.split(",")[1].split(":")[1].trim())
                            {
                                exists='true';
                                addMessage(data.split(",")[1].split(":")[1].trim(), data, "flowright","imgright");
                                document.getElementById(name.trim()).classList.add("show");
                                document.getElementById(name.trim()).classList.remove("hide");
                            }
                            else
                            {
                                if(name.trim() !== "")
                                {
                                    try
                                    {
                                        document.getElementById(name.trim()).classList.remove("show");
                                        document.getElementById(name.trim()).classList.add("hide");
                                    }
                                    catch(error)
                                    {
                                        console.log(error);
                                    }
                                }
                            }
                        }

                        if(exists=='false')
                        {
                            const parent = document.getElementById("currView");
                            try
                            {
                                document.getElementById(parent.lastChild.id).classList.remove("show");
                                document.getElementById(parent.lastChild.id).classList.add("hide");
                            }
                            catch(error)
                            {
                                console.log(error);
                            }

                            currentUserView=data.split(",")[1].split(":")[1].trim();
                            createUserDiv(currentUserView);
                            newMessage(data.split(",")[1].split(":")[1].trim(), data, "flowright", "imgright");
                        }
                    }
                }
            },
            dataType: "text",
            complete: pollReceiver,
            timeout: 2000
        })
    }, 5000);
});


function createUserDiv(name)
{
    const currentDiv = document.getElementById("memName");

    var newDiv = document.createElement("DIV");
    newDiv.classList.add("buttdiv")

    var uname = document.createElement("BUTTON");
    var buttonText = document.createTextNode(name);
    uname.appendChild(buttonText);
    uname.classList.add("buttclass");
    uname.type = "button";

    var deleteMessage = document.createElement("BUTTON");
    var deleteIcon = document.createTextNode("X");
    deleteMessage.appendChild(deleteIcon);
    deleteMessage.classList.add("messgeDelete");
    deleteMessage.type = "button";

    uname.onclick = function(){
        showMessageDiv(name.trim());
    }

    newDiv.appendChild(uname);
    newDiv.appendChild(deleteMessage);
    currentDiv.appendChild(newDiv);
}

function newMessage(id, data, val,imgval)
{
    const currentDiv = document.getElementById("currView");

    var newDiv = document.createElement("DIV");
    newDiv.classList.add("show");
    newDiv.id = id;

    currentDiv.appendChild(newDiv);
    addMessage(id, data, val, imgval);
}

function addMessage(id, data, val, imgval)
{
     var msgdiv =  document.createElement("DIV");
     msgdiv.classList.add("msgflow");


    var imageDiv = document.createElement("DIV");
    imageDiv.classList.add("imgflow")
    imageDiv.classList.add(imgval);

    var image = document.createElement("IMG");
    image.src="../images/person.png";
    image.classList.add("imgstyle");

    imageDiv.appendChild(image);

    var newDiv = document.createElement("DIV");
    newDiv.classList.add("flow")
    newDiv.classList.add(val);

    var date = document.createElement("LABEL");
    var from = document.createElement("LABEL");
    var to = document.createElement("LABEL");
    var message = document.createElement("LABEL");

    var dateText = data.split(',')[0];
    var received = data.split(',')[1];
    var sent = data.split(',')[2];
    var msg = data.split(',')[3];

    var text1 = document.createTextNode(dateText);
    var header1 = document.createElement("H3");
    header1.appendChild(text1);
    date.appendChild(header1);

    var text2 = document.createTextNode(received);
    var header2 = document.createElement("H3");
    header2.appendChild(text2);
    from.appendChild(header2);

    var text3 = document.createTextNode(sent);
    var header3 = document.createElement("H3");
    header3.appendChild(text3);
    to.appendChild(header3)

    var text4 = document.createTextNode(msg);
    var header4 = document.createElement("H3");
    header4.appendChild(text4);
    message.appendChild(header4)


    newDiv.appendChild(date);
    newDiv.appendChild(from);
    newDiv.appendChild(to);
    newDiv.appendChild(message);

    const currentDiv = document.getElementById(id);

    msgdiv.appendChild(imageDiv);
    msgdiv.appendChild(newDiv);

    currentDiv.appendChild(msgdiv);

    const currView = document.getElementById("currView");
    currView.scrollTop = currView.scrollHeight;
}

function getUser(receiver) {
    document.getElementById(receiver).value = receiver;
    document.getElementById(receiver+'_').submit();
};