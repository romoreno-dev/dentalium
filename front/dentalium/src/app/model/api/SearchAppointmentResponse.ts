export class SearchAppointmentResponse {
  appointmentList: Appointment[];

  constructor() {
    this.appointmentList = [];
  }
}

export class Appointment {
  id: number;
  initDate: Date;
  endDate: Date;
  observations: string;
  status: Status;
  doctor: Person;
  patient: Person;

  constructor(
    id: number,
    initDate: Date,
    endDate: Date,
    observations: string,
    status: Status,
    doctor: Person,
    patient: Person
  ) {
    this.id = id;
    this.initDate = initDate;
    this.endDate = endDate;
    this.observations = observations;
    this.status = status;
    this.doctor = doctor;
    this.patient = patient;
  }
}

export class Person {
  id: string;
  name: string;

  constructor(id: string, name: string) {
    this.id = id;
    this.name = name;
  }
}

export class Status {
  id: string;
  description: string;

  constructor(id: string, description: string) {
    this.id = id;
    this.description = description;
  }
}
