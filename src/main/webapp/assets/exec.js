function includeFile(path, type) {
  if (type == "js") { //if filename is a external JavaScript file
    var element = document.createElement('script');
    element.setAttribute("type", "text/javascript");
    element.setAttribute("src", path);
  } else if (type == "css") { //if filename is an external CSS file
    var element = document.createElement("link");
    element.setAttribute("rel", "stylesheet");
    element.setAttribute("type", "text/css");
    element.setAttribute("href", path);
  }

  if (typeof element != "undefined") {
    document.head.appendChild(element);
  }
}

function includeTargetElement() {
  var element = document.createElement("div");
  element.setAttribute("id", "panel");
  document.getElementById("wrap").appendChild(element);
}

includeFile("https://code.jquery.com/jquery-3.3.1.min.js", "js");
includeFile("https://use.fontawesome.com/releases/v5.1.0/css/all.css", "css");
includeFile("/asset/exec_template/panel.js", "js");
includeFile("/asset/exec_template/panel.css", "css");
