package com.cognizant.admin.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ValidatingDTO {
	
		@JsonProperty
	    private boolean validStatus;

}

