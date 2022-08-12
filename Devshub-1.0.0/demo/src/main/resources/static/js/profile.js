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

function openEditInfoForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("editinfo");
  element.classList.add("displayForms");
  element.classList.remove("hide");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeEditInfoForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("editinfo");
  element.classList.remove("displayForms");
  element.classList.add("hide");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
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


function addLike(id)
{
    $.ajax({
        url: "https://"+window.location.host+"/article/addlike",
        type: "GET",
        dataType: "text",
        data: {
            "id": id
        },
        success: function(data) {
            if(data)
            {
                if(data==1)
                {
                    document.getElementById("liked_"+id).innerHTML=data + " Like";

                    document.getElementById("liked_"+id).classList.add("showshow");
                    document.getElementById("liked_"+id).classList.remove("hide");

                }
                else if(data > 1)
                {
                    document.getElementById("liked_"+id).innerHTML=data + " Likes";
                }
                else
                {
                    document.getElementById("liked_"+id).text=data;;

                    document.getElementById("liked_"+id).classList.remove("showshow");
                    document.getElementById("liked_"+id).classList.add("hide");
                }
            }
        }
    });
}


function postComment(articleId)
{
    $.ajax({
        url: "https://"+window.location.host+"/post/comment",
        type: "GET",
        dataType: "text",
        data: {
            "comment": document.getElementById("dcomments_"+articleId).value,
            "articleId": articleId
        },
        success: function(data) {
            if(data)
            {
                createComment(data, articleId);
            }
        }
    });
}

function createComment(data, articleId)
{
    var id = data.split(",")[0];
    var date = data.split(",")[1];
    var name = data.split(",")[2];
    var comment = data.split(",")[3];
    var image = data.split(",")[4];

    var commdiv = document.createElement("DIV");
    commdiv.classList.add("commdiv");

    var img = document.createElement("IMG");
    img.classList.add("commImg");
    img.src = image;

    var dateHeader = document.createElement("H6");
    var dateText = document.createTextNode(date);
    dateHeader.appendChild(dateText);

    var userName = document.createElement("H4");
    var nameText = document.createTextNode(name);
    userName.appendChild(nameText);

    var commentLabel = document.createElement("LABEL");
    var commentText = document.createTextNode(comment);
    commentLabel.appendChild(commentText);

    commdiv.appendChild(dateHeader);
    commdiv.appendChild(userName);
    commdiv.appendChild(commentLabel);


    var userComments = document.createElement("DIV");
    userComments.classList.add("commentsDiv");


    userComments.appendChild(img);
    userComments.appendChild(commdiv);

    document.getElementById("userComments_"+articleId).appendChild(userComments);
}