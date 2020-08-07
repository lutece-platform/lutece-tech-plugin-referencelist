/**
 * Create / Update a Reference - Parent Item Display by Ajax Call :
 *
 * Loads the ReferenceItem combo box when the selected reference change or at starting
 * 
 * uses "idParentItem" variable from create/modify template 
 */

var itemFormGroup = document.getElementById("form-group-parent-item");

var parentItemSelect = document.getElementById('idParentItem');

var masterRefSelect = document.getElementById('idMasterReference');

var visible = itemFormGroup.style.display;

var load = loadByAjax( "rest/referenceitems/list/reference/", displayItemSelect );

masterRefSelect.onchange = load;

// don't display the reference items select if not necessary'
if (idParentItem)
{
	load();
}
else 
	itemFormGroup.style.display = "none";

function loadByAjax(url, callback) {
	
	return function(ev)
	{
		// on change
		if (ev) 
			idReference = ev.target.value
		// first load 
		else
			idReference = getSelectedReferenceId();
			
		if (idReference == "")
		{
			itemFormGroup.style.display = "none";
			clearItemSelect();
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
function displayItemSelect(items) {
	
	clearItemSelect();

	parentItemSelect.options[0] = new Option ( "", "" );

    for(i = 0; i < items.length; i++) {

    	parentItemSelect.options[i + 1] = new Option ( items[i].code + " -- " + items[i].name, items[i].id );

		if (idParentItem && idParentItem == items[i].id)
			parentItemSelect.options[i + 1].selected = true;		
    }
    
    itemFormGroup.style.display = visible;
}


function clearItemSelect() 
{
	parentItemSelect.options.length = 0;
}

function getSelectedReferenceId() 
{
	idReference = "";
	
	selectLength = masterRefSelect.options ? masterRefSelect.options.length : 0;
	
	for(i = 0 ; i < selectLength; i++) 
	{	
		if (masterRefSelect.options[i].selected)
		{
			idReference = masterRefSelect.options[i].value;
			break;
		}
	}	
	
	return idReference;
}

