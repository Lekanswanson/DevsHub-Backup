window.addEventListener("load", switchSize)
function switchSize()
{
   var image = document.getElementById('userimg')
   if(image.src!=="http://localhost:8080/images/person.png")
   {
        image.classList.remove("default");
        image.classList.add("replace");
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
  element.classList.add("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}

function closeForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("myText");
  element.classList.remove("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}

function openEducationForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("eduform");
  element.classList.add("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeEducationForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("eduform");
  element.classList.remove("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}


function openExperienceForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("expform");
  element.classList.add("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeExperienceForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("expform");
  element.classList.remove("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}



function openSkillsForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("skillform");
  element.classList.add("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeSkillsForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("skillform");
  element.classList.remove("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}


function openLanguageForm() {
  document.getElementById("backlayout").classList.add("blur")
  var element = document.getElementById("langForm");
  element.classList.add("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = true;
  }
}
function closeLanguageForm() {
  document.getElementById("backlayout").classList.remove("blur")
  var element = document.getElementById("langForm");
  element.classList.remove("displayForms");

  var elems = document.getElementsByClassName("btn");
  for(var i = 0; i < elems.length; i++) {
    elems[i].disabled = false;
  }
}


function editEducationForm(education) {
    const myArray = education.split(",");

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

function editExperienceForm(experience) {
    const myArray = experience.split(",");

    document.getElementById(myArray[0]).value = myArray[0]
    document.getElementById(myArray[1]+'-'+myArray[0]).value = myArray[1]
    document.getElementById(myArray[2]+'-'+myArray[0]).value = myArray[2]
    document.getElementById(myArray[3]+'-'+myArray[0]).value = myArray[3]
    document.getElementById(myArray[4]+'-'+myArray[0]).value = myArray[4]
    document.getElementById(myArray[5]+'-'+myArray[0]).value = myArray[5]

    var element = document.getElementById(experience);
    element.classList.add("educationbox");
}

function closeEditForm(id) {
    var element = document.getElementById(id);
    element.classList.remove("educationbox");
}

function userAction() {
    var form = document.getElementById('svimg');
    form.submit();
}


function showcolors(){
    var colorpanel = document.getElementById('colorpanel');
    var emptypanel = document.getElementById('emptypanel');

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


function changeBackGroundColor(color)
{
    document.getElementById('testcolor').style.backgroundColor=color;
}
//function saveVideo() {
//    var form = document.getElementById('addvid');
//    form.submit();
//}



