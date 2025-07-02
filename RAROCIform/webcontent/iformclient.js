//m26
var facility_type = "";//global var for popup on Collateral_Facility_Mapping Grid
var facility_name = "";
var facility="";
var  CFM_onRowClick_flag=false;//clicking on row of Collateral_Facility_Mapping Grid or not
var pathToCustomJS = '/RAROC/components/viewer/resources/scripts';
var scriptTag;
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/RAROCConstants.js?v1.6';
document.head.appendChild(scriptTag);
console.log("RAROCConstants Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/RAROC.js?v1.8';
document.head.appendChild(scriptTag);
console.log("RAROC Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/Initiator.js?v1.7';
document.head.appendChild(scriptTag);
console.log("Initiator Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/CommonPool.js?v1.2';
document.head.appendChild(scriptTag);
console.log("CommonPool Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/Exit.js?v1.1';
document.head.appendChild(scriptTag);
console.log("Exit Js included");
function customValidation(op){
     switch (op) {
        case 'S':
            
            break;
        case 'I':
            
            break;
        case 'D':
           
            break;
        default:
            break;
    }
    
    return true;
}

function formLoad(){
onFormLoad();
}

function onRowClick(tableId,rowIndex){
	if(tableId == 'LV_FacilityDetails' || tableId == 'LV_LoanDetailsAPI' || tableId == 'LV_REPAYMENT_DETAILS'){
		return false;
	}
	if(tableId == 'COLLATERAL_FACILITY_MAPPING_GRID'){//m26
		console.log(" OnRowCLick");
		//CFM_add_flag=false;
		CFM_onRowClick_flag=true;
		var stageId = getValue('CURRENT_WORKSTEPNAME');
		/*if (stageId == 'Initiate_Proposal' || stageId == 'RM_Proposal_Review' || stageId == 'RO_Proposal_Modify' || stageId == 'UH_TL_Proposal_Review' || stageId == 'CBO_Proposal_Review' || stageId == 'RM_Query Resolutions'
		|| stageId == 'RM_Doc_Execution' || stageId == 'UH_TL_Def_Waiver' || stageId == 'Head_Corporate_Banking') {*/
			
     facility_type = getValueFromTableCell('COLLATERAL_FACILITY_MAPPING_GRID',rowIndex,0);
		 facility_name = getValueFromTableCell('COLLATERAL_FACILITY_MAPPING_GRID',rowIndex,1);
		 facility = getValueFromTableCell('COLLATERAL_FACILITY_MAPPING_GRID',rowIndex,2);
		console.log(" facility_type,facility_name,facility,CFM_add_flag "+facility_type+" "+facility_name+" "+facility+" "+CFM_add_flag);
		
		//return true;
		//}
		return true;
	}
	else{
		
		return true;
	}
}

function customListViewValidation(controlId,flag){	
	//customListViewValidationRaroc(controlId,flag);   
    return true;
}   

function listViewLoad(controlId,action){//m26
	console.log("Inside listViewLoad");
    OnListLoad(controlId,action);
    if(controlId == COMMENT_HISTORY){
		console.log("checking action="+action);
    var userName = getValue("USER_ID");
    setValue('UserName',userName);
    
    const now = new Date();
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0'); 
    const day = now.getDate().toString().padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;
    setValue('Dates', formattedDate);
}

//start edit by mohit 26-09-2024
 if(controlId == 'COLLATERAL_FACILITY_MAPPING_GRID'){
	 console.log("  COLLATERAL_FACILITY_MAPPING_GRID listViewLoad facility_type is "+facility_type);
	 customListViewValidationRaroc(controlId,action); 
	 Collateral_Faci_Map_Onload(facility_type,facility_name,facility);
	 console.log("  after  Collateral_Faci_Map_Onload back, CFM_add_flag=false;");
	
   
}
//end edit by mohit 26-09-2024
}

function clickLabelLink(labelId){
    if(labelId=="createnewapplication"){
        var ScreenHeight=screen.height;
        var ScreenWidth=screen.width;
        var windowH=600;
        var windowW=1300;
        var WindowHeight=windowH-100;
        var WindowWidth=windowW;
        var WindowLeft=parseInt(ScreenWidth/2)-parseInt(WindowWidth/2);
        var WindowTop=parseInt(ScreenHeight/2)-parseInt(WindowHeight/2)-50;
        var wiWindowRef = window.open("../viewer/portal/initializePortal.jsp?NewApplication=Y&pid="+encode_utf8(pid)+"&wid="+encode_utf8(wid)+"&tid="+encode_utf8(tid)+"&fid="+encode_utf8(fid), 'NewApplication', 'scrollbars=yes,left='+WindowLeft+',top='+WindowTop+',height='+windowH+',width='+windowW+',resizable=yes')
    }
}
function allowPrecisionInText(){
    return 0;
}

function maxCharacterLimitForRichText(id){
    
 
    return -1;
}
function showCustomErrorMessage(controlId,errorMsg){
    return errorMsg;
}

function resizeSubForm(buttonId){
    return {
        "Height":450,
        "Width":950
    };
}

function selectFeatureToBeIncludedInRichText(){
    return {
        'bold' :true,
        'italic':true,
        'underline':true,
        'strikeThrough':true,
        'subscript':true,
        'superscript':true,
        'fontFamily':true,
        'fontSize':true,
        'color':true,
        'inlineStyle':false,
        'inlineClass':false,
        'clearFormatting':true,
        'emoticons':false,
        'fontAwesome':false,
        'specialCharacters':false,
        'paragraphFormat':true,
        'lineHeight':true,
        'paragraphStyle':true,
        'align':true,
        'formatOL':false,
        'formatUL':false,
        'outdent':false,
        'indent':false,
        'quote':false,
        'insertLink':false,
        'insertImage':false,
        'insertVideo':false,
        'insertFile':false,
        'insertTable':true,
        'insertHR':true,
        'selectAll':true,
        'getPDF':false,
        'print':false,
        'help':false,
        'html':false,
        'fullscreen':false,
        'undo':true,
        'redo':true
        
    }
}

function allowDuplicateInDropDown(comboName){
    return false;
}

function postChangeEventHandler(controlId, responseData)
{
    
}
function onClickTab(tabId,sheetindex,eventCall){
	onTabClickRaroc(tabId,sheetindex,eventCall);
}

function test(){
    executeServerEvent('button1', 'click' , '' , true);
}

function test1(){
    executeServerEvent('TEMPLATE_TYPE', 'change' , '' , true);
}
function onTableCellChange(rowIndex,colIndex,ref,controlId){
	onCellChangeRaroc(rowIndex,colIndex,ref,controlId);
	}
function postServerEventHandler(controlName, eventType, responseData) {
	postServerEventHandlerRaroc(controlName, eventType, responseData);
}	