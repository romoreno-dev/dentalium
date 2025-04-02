export class BudgetResponse {
  patientDni: string;
  budgets: Budget[];

  constructor() {
    this.patientDni = "";
    this.budgets = [];
  }
}

export class Budget {
  id: number;
  date: Date;
  status: Status;

  constructor(id: number, date: Date, status: Status) {
    this.id = id;
    this.date = date;
    this.status = status;
  }
}

export class Status {
  id: number;
  description: string;

  constructor(id: number, description: string) {
    this.id = id;
    this.description = description;
  }
}
