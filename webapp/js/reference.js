/**
 * Create / Update a Reference - Parent Item Management :
 *
 * Loads the ReferenceItem combo box when the selected reference change  
 */
document.getElementById("idReference").onchange = loadByAjax( "rest/referenceitems/list/reference/", displaySelect );

var itemFormGroup = document.getElementById("form-group-parent-item");

var parentItemSelect = document.getElementById('idParentItem');

var visible = itemFormGroup.style.display;

function loadByAjax(url, callback) {
	
	return function(ev)
	{
		idReference = ev.target.value
		
		if (idReference == "")
		{
			itemFormGroup.style.display = "none";
			clearSelect();
		}
		else 	
		{
			xmlhttp = new XMLHttpRequest();
		
			xmlhttp.onreadystatechange = function() {
			    if (this.readyState == 4 && this.status == 200) {
			        response = JSON.parse(this.responseText);
			        callback(response.result);
			    }
			};
		
			xmlhttp.open("GET", url + idReference, true);
			xmlhttp.send();
		}
	}
}

// modify the items select 
function displaySelect(items) {
	
	clearSelect();

	parentItemSelect.options[0] = new Option ( "", "" );

    for(i = 1; i < items.length; i++) {
    	parentItemSelect.options[i] = new Option ( items[i].name, items[i].id );
    }
    
    itemFormGroup.style.display = visible;
}


function clearSelect() {
	parentItemSelect.options.length = 0;
}

