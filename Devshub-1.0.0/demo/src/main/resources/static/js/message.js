var currentUserView="";

$(function(){
    getPerson();
});

function showMessageDiv(user){

    const currentDiv = document.getElementById("memName");
    const children = currentDiv.childNodes;

    for(var i=0; i<children.length; i++)
    {
        var name = children[i].textContent;

        if(name.trim() === user)
        {
            //alert(name.trim() + "<----->" + user)
            try
            {
                document.getElementById("rcvname").value = user;
                document.getElementById(user).classList.remove("hide");
                document.getElementById(user).classList.add("show");
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
        url: "http://"+window.location.host+"/user/email",
        type: "GET",
        dataType: "text",
        success: function(data) {
            document.getElementById("sndname").value=data;
        }
    });
}
function postform()
{
    var sender = document.getElementById("sndname").value;
    var receiver = document.getElementById("rcvname").value;
    var message = document.getElementById("dmsg").value;

    $.ajax({
        url: "http://"+window.location.host+"/user/createMessage",
        type: "GET",
        dataType: "text",
        data: {
            "sender": sender,
            "receiver": receiver,
            "message": message
        },
        success: function(data)
        {
            document.getElementById("dmsg").value = "";
            if(data)
            {
                var exists = 'false';
                const currentDiv = document.getElementById("memName");
                const children = currentDiv.childNodes;
                
                if(children.length <= 1)
                {
                    createUserDiv(receiver);
                    newMessage(data.split(",")[2].split(":")[1].trim(), data);
                }
                else
                {
                    for(var i=0; i<children.length; i++)
                    {
                        var name = children[i].textContent;

                        if(name.trim() === data.split(",")[2].split(":")[1].trim())
                        {
                            exists='true';
                            addMessage(data.split(",")[2].split(":")[1].trim(), data);
                            document.getElementById(name.trim()).classList.add("show");
                            document.getElementById(name.trim()).classList.remove("hide");
                        }
                        else
                        {
                            if(name.trim() !== "")
                            {
                                //alert(name);
                                document.getElementById(name.trim()).classList.remove("show");
                                document.getElementById(name.trim()).classList.add("hide");
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
                        currentUserView=data.split(",")[2].split(":")[1];
                        createUserDiv(data.split(",")[2].split(":")[1]);
                        newMessage(data.split(",")[2].split(":")[1].trim(), data);
                    }
                }
            }
        }
    });
}

(function pollReceiver() {
    setTimeout(function() {
        $.ajax({
            url: "http://"+window.location.host+"/receiver/inbox",
            type: "GET",
            success: function(data)
            {
                if(data && document.getElementById("sndname").value.trim() !== data.split(",")[1].split(":")[1].trim())
                {
                    document.getElementById("notifications").classList.add("unread");

                    var exists = 'false';
                    const currentDiv = document.getElementById("memName");
                    const children = currentDiv.childNodes;

                    if(children.length <= 1)
                    {
                        createUserDiv(data.split(",")[1].split(":")[1]);
                        newMessage(data.split(",")[1].split(":")[1].trim(), data);
                    }
                    else
                    {
                        for(var i=0; i<children.length; i++)
                        {
                            var name = children[i].textContent;

                            if(name.trim() === data.split(",")[1].split(":")[1].trim())
                            {
                                exists='true';
                                addMessage(data.split(",")[1].split(":")[1].trim(), data);
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

                            currentUserView=data.split(",")[1].split(":")[1];
                            createUserDiv(data.split(",")[1].split(":")[1]);
                            newMessage(data.split(",")[1].split(":")[1].trim(), data);
                        }
                    }
                }
            },
            dataType: "text",
            complete: pollReceiver,
            timeout: 2000
        })
    }, 5000);
})();


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

    uname.onclick = function(){
        showMessageDiv(name.trim());
    }

    newDiv.appendChild(uname);
    currentDiv.appendChild(newDiv);
}


function newMessage(id, data)
{
    const currentDiv = document.getElementById("currView");

    var newDiv = document.createElement("DIV");
    newDiv.classList.add("show");
    newDiv.id = id;

    currentDiv.appendChild(newDiv);
    addMessage(id, data);
}

function addMessage(id, data)
{
    var newDiv = document.createElement("DIV");
    newDiv.classList.add("flow");

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
    currentDiv.appendChild(newDiv);

    const currView = document.getElementById("currView");
    currView.scrollTop = currView.scrollHeight;
}

function getUser(receiver) {
    document.getElementById(receiver).value = receiver;
    document.getElementById(receiver+'_').submit();
};