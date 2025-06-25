package com.newgen.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDocumentRequest {
	private String applicationName;
	private String applicationUserName;
	private String docAttachmentFlag;	
	Documents documents;
	
	private String folderPathCriteria;
	private String funcName;
	private String password;
	private String reqMode;
	private String scheduledDateTime;
	private String seqRefNo;
	private String userName;
	private String docExtension;
	
}
