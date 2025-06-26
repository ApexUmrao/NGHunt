
				//dde work
				if(MANUAL_PREFIX == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if (COMBODOC == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(CP_CITY == (event.target.id) || PA_CITY == (event.target.id) 
						|| RA_CITY == (event.target.id)) { //load also 
					executeserverevent(event.target.id, event.type, '', false);
				} else if(PD_CUSTSEGMENT == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(MANUAL_FIRSTNAME == (event.target.id)){
					executeserverevent(event.target.id, event.type, '', false);
				} else if(MANUAL_LASTNAME == (event.target.id)){
					executeserverevent(event.target.id, event.type, '', false);
				} else if(MANUAL_NAME == (event.target.id)){
					executeserverevent(event.target.id, event.type, '', false);
				} else if(MANUAL_VISASTATUS == (event.target.id)) { 
					executeserverevent(event.target.id, event.type, '', false);
				} else if('COMBO34' == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(EMP_STATUS == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(FCR_EMPLYR_NAME == (event.target.id) 
						|| EIDA_EMPLYR_NAME == (event.target.id) 
						|| MANUAL_EMPLYR_NAME == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(FCR_RESIDENT == (event.target.id) || EIDA_RESIDENT == (event.target.id) 
						|| MANUAL_RESIDENT == (event.target.id) 
						|| FCR_NATIONALITY == (event.target.id) 
						|| EIDA_NATIONALITY == (event.target.id) 
						|| MANUAL_NATIONALITY == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(MANUAL_CNTRY == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(RES_CNTRY == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(CORR_CNTRY == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(PERM_CNTRY == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(WORKSTEPS.DDE_CUST_INFO == (workstepname) 
						&& (event.target.id == (CHECKBOX_NATIONALITY_FCR) 
						|| event.target.id == (FCR_NATIONALITY) 
						|| event.target.id == (CHECKBOX_NATIONALITY_EIDA)
						|| event.target.id == (CHECKBOX_NATIONALITY_MANUAL) 
						|| event.target.id == (EIDA_NATIONALITY)
						|| event.target.id == (MANUAL_NATIONALITY) 
						|| event.target.id == (CHECKBOX_CNTRY_OF_CORR_FCR) || event.target.id == (FCR_CNTRY) 
						|| event.target.id == (CHECKBOX_CNTRY_OF_CORR_EIDA) || event.target.id == (CHECKBOX_CNTRY_OF_CORR_MANUAL) 
						|| event.target.id == (EIDA_CNTRY) || event.target.id == (MANUAL_CNTRY)
						|| event.target.id == (CHECKBOX_TELE_RES_FCR) || event.target.id == (FCR_PH) 
						|| event.target.id == (CHECKBOX_TELE_RES_EIDA) || event.target.id == (CHECKBOX_TELE_RES_MANUAL) 
						|| event.target.id == (EIDA_PH) || event.target.id == (MANUAL_PH) 
						|| event.target.id == (CHECKBOX_TELE_MOB_FCR) || event.target.id == (FCR_MOBILE) 
						|| event.target.id == (CHECKBOX_TELE_MOB_EIDA) || event.target.id == (CHECKBOX_TELE_MOB_MANUAL) 
						|| event.target.id == (EIDA_MOBILE) || event.target.id == (MANUAL_MOBILE)
						|| event.target.id == (CHECKFCR) || event.target.id == (FCR_RESIDENT) 
						|| event.target.id == (CHECKEIDA) || event.target.id == (CHECKMANUAL) 
						|| event.target.id == (EIDA_RESIDENT) 
						|| event.target.id == (MANUAL_RESIDENT) || event.target.id == (FAT_US_PERSON)
						|| event.target.id == (FAT_LIABLE_TO_PAY_TAX) || event.target.id == (CNTRY_OF_BIRTH) 
						|| event.target.id == (POACOMBO) || event.target.id == (INDICIACOMBO)
						|| event.target.id == (FAT_SSN) || event.target.id == (FAT_CUST_CLASSIFICATION)
						|| event.target.id == (DATEPICKERCUST) 
						|| event.target.id == (DATEPICKERW8) || event.target.id == (COMBODOC)
						|| event.target.id == (CHECKBOX_COB_FCR) || event.target.id == (CHECKBOX_COB_EIDA) 
						|| event.target.id == (CHECKBOX_COB_MANUAL) || event.target.id == (FCR_COUNTRYBIRTH)
						|| event.target.id == (EIDA_COUNTRYBIRTH) 
						|| event.target.id == (MANUAL_COUNTRYBIRTH))) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(WORKSTEPS.DDE_CUST_INFO == (workstepname) 
						&& event.target.id == (FAT_CUST_CLASSIFICATION)) {
					executeserverevent(event.target.id, event.type, '', false);
				} //text79//text80-->Kritika (ACTIVITY_DDE_CUST_INFO)
				else if(WORKSTEPS.DDE_ACCOUNT_INFO == (workstepname) 
						&& event.target.id == (NEW_DEL_MODE)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (NEW_RM_CODE_CAT_CHANGE)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (PD_ANY_CHNG_CUST_INFO)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (PA_SAMEAS)) {
					executeserverevent(event.target.id, event.type, '', false);
				} //3555-3602 -->Kritika (DDE_ACCOUNT_INFO)
				else if(ACTIVITY_DDE_ACCOUNT_INFO == (workstepname) 
						&& event.target.id == (DFC_STATIONERY_AVAIL)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(PD_CUSTSEGMENT == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(ACC_INFO_PG == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(GROUP_TYPE == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(NEW_LINK == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(EMPNAME == (event.target.id)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (SI_DEB_ACC_NO)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (NEW_RM_CODE_CAT_CHANGE)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (NEW_RM_NAME_CAT_CHANGE)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (COMBODOC)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (FAT_US_PERSON)) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if((event.target.id == (FCR_COUNTRYBIRTH)) 
						|| (event.target.id == (EIDA_COUNTRYBIRTH))
						|| (event.target.id == (MANUAL_COUNTRYBIRTH))) {
					executeserverevent(event.target.id, event.type, '', false);
				} else if(event.target.id == (NEW_CUST_SEGMENT)) {
					executeserverevent(event.target.id, event.type, '', false);
				}
			