package com.netpoint.clinicapp.DTO;

import com.netpoint.clinicapp.Enum.AppointmentStatus;
import com.netpoint.clinicapp.model.Appointment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentStatusUpdateDTO {
    private AppointmentStatus status;
}
