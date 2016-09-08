function addPhone() {

    var newDiv = document.createElement('div');
    newDiv.className = "input-group";
    newDiv.innerHTML = '<input id="phone2" data-inputmask=\"\'mask\': \'(999)-999-999\'\" type="text" class="form-control" name="phones[]"><span class="input-group-btn"><button class="btn btn-default" type="button" onclick="removePhone(this)"><span class="glyphicon glyphicon-minus"></span></button></span>';

    document.getElementById('addPhone').appendChild(newDiv);
}


function removePhone(div) {
    document.getElementById('addPhone').removeChild(div.parentNode.parentNode);
}
