package com.newgen.service.entities;

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
public class AddDocumentResponse {
	private String description;
	private String status;
	private String message;
	private String docIndex;
			
}
