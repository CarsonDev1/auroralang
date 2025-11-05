package vn.edu.fpt.AuroraLang.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponse {
	private Integer sessionId;
	private Integer sessionNumber;
	private String sessionName;
	private LocalDate sessionDate;
	private LocalTime startTime;
	private LocalTime endTime;
}


