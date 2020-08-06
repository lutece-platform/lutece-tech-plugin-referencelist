
document.getElementById("idReference").onchange = loadByAjax( "rest/example/api/items", displaySelect );

var itemFormGroup = document.getElementById("form-group-parent-item");

var parentItemSelect = document.getElementById('idParentItem');

var visible = itemFormGroup.style.display;

function loadByAjax(url, callback) {
	
	return function(ev)
	{
		if (ev.target.value == "")
		{
			itemFormGroup.style.display = "none";
			clearSelect();
			return;
		}	
		
		var xmlhttp = new XMLHttpRequest();
		
		xmlhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		        response = JSON.parse(this.responseText);
		        callback(response.result);
		    }
		};
		
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
}

// modify the items select 
function displaySelect(items) {
	
	clearSelect();
	
    for(i = 0; i < items.length; i++) {
    	parentItemSelect.options[i] = new Option ( items[i].name, items[i].id );
    }
    
    itemFormGroup.style.display = visible;
}


function clearSelect() {
	parentItemSelect.options.length = 0;
}

