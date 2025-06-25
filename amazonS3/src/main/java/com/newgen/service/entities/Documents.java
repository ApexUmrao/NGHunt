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
public class Documents {
	
	private String dataClassName;
	DocValues[] docValues;
	private String docRefNo;
	private String docType;
	private String filePath;
	private String folderStructure;
		
}
