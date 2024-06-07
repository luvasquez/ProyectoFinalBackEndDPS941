package sv.com.consultorio.apiconsultorio.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SelectOptionResponse {

	
	private Long value;
	
	private String label;
	
	@Builder.Default
	private String color = "#0095FF";
	
}