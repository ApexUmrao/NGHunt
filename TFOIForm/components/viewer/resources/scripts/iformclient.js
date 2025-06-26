/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 13/12/2016       Mohit Sharma            Bug 66247 - Validation on submit button in ibps mobile form required
 * 07/05/2018       Gaurav Sharma           Bug 77543 - Events and webservice functionality not working in iform not getting output.
 * 06/11/2018       Gaurav                  Bug 81232 - Digit Grouping not working in setValues() API
 * 12/12/2018       Aman Khan               Bug 81913 - In style3 TextBox,Label name not aligned properly.
 * 12/02/2019       Aman Khan               Bug 83038 - We need to open the URL in the application on click of labels.
 * 21/02/2019       Gaurav                  Bug 83221 - Not able to set value in editable combo using setValues
 * 26/02/2019       Abhishek                Bug 83310 - Removal of RTE Features
 * 26/04/2019       Aman Khan               Bug 84373 - unable to set date in ipad
 * 30/04/2019       Aman Khan               Bug 84407 - If the type of field in a comboBox is a DropDown then it is not getting populated through code whereas a DropDownList is workking fine. Kindly check this.
 * 28/05/2019       Gaurav                  Bug 84949 - mandatory validation is not removed if data is set using API.
 * 28/05/2019       Abhishek                Bug 84964 - Custom alert on cross icon of grid 
 * 12/07/2019       Abhishek                Bug 85595 - Suppress format tools in Richtext editor Initially 
 */

var pathToCustomJS = '/TFO/components/viewer/resources/scripts';
var scriptTag;
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/TFOConstants.js?v1.9';
document.head.appendChild(scriptTag);
console.log("TFOConstants Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/TFO.js?v2.0';
document.head.appendChild(scriptTag);
console.log("TFO Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/PPM.js?v2.1';
document.head.appendChild(scriptTag);
console.log("PPM Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/RM.js?v1.2';
document.head.appendChild(scriptTag);
console.log("RM Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/DELIVERY.js?v1.1';
document.head.appendChild(scriptTag);
console.log("DELIVERY Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/PMPCProcessingSystem.js?v2.0';
document.head.appendChild(scriptTag);
console.log("PMPCProcessingSystem Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/PM.js?v3.0';
document.head.appendChild(scriptTag);
console.log("PM Js included");
scriptTag = document.createElement('script');
scriptTag.src = pathToCustomJS + '/PC.js?v1.8';
document.head.appendChild(scriptTag);
console.log("PC Js included");

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
    return true;
}

function customListViewValidation(controlId,flag){
    return true;
}   

function listViewLoad(controlId,action){
    listViewLoadPPM(controlId,action);
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
	
	function addRowPostHook(tableId){
		
		if(tableId=='SIGN_REFERRAL_ID'||tableId=='LIMIT_REFERRAL_ID'||tableId=='Doc_Review_RefID'){
			sufficentBal(tableId);
		}
	}
	
	function modifyRowPostHook(tableId){
		
		if(tableId=='SIGN_REFERRAL_ID'||tableId=='LIMIT_REFERRAL_ID'||tableId=='Doc_Review_RefID'){
			sufficentBal(tableId);
		}
	}
}