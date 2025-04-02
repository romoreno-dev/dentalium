export class BudgetWithBudgetLinesResponse {

  id?: number;
  patientName?: string;
  date?: Date;
  status?: Status;
  totalPrice?: String;
  budgetLine: BudgetLine[];

  constructor() {
    this.budgetLine = [];
  }
}

export class BudgetLine {
  treatmentId: number;
  treatmentName: string;
  quantity: number;
  teeth: string;
  discount: number;
  unitPrice: number;
  treatmentPrice: string;
  totalPrice: string;

  constructor(treatmentId: number, treatmentName: string, quantity: number, teeth: string, discount: number, unitPrice: number, treatmentPrice: string, totalPrice: string) {
    this.treatmentId = treatmentId;
    this.treatmentName = treatmentName;
    this.quantity = quantity;
    this.teeth = teeth;
    this.discount = discount;
    this.unitPrice = unitPrice;
    this.treatmentPrice = treatmentPrice;
    this.totalPrice = totalPrice;
  }
}

class Status {
  id: number;
  description: string;

  constructor(id: number, description: string) {
    this.id = id;
    this.description = description;
  }
}
