function changeEventCPDMakerThreeStep(event){
    else if(controlName == 'EMP_ADD_CITY') {	  
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'EMP_ADD_EMIRATES') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'EMP_ADD_STATE') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'SALARY_TRANSFER') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CRS_TAX_COUNTRY) {
		executeServerEvent(controlName, event.type, '', false);
	}
}

function clickEventCPDMakerThreeStep(event){
    else if (controlName == 'PRODUCT_QUEUE.PROD_CODE' || controlName == 'PRODUCT_QUEUE.CURRENCY'){
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == 'PRODUCT_QUEUE.TRNSFR_FROM_ACC_NO') {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == CT_BTN_RESETALL) {
		executeServerEvent(controlName, event.type, '', false);
	} else if(controlName == BTN_NEXT_CUST) {
	    executeServerEvent(controlName, event.type, '', false);
	}
}