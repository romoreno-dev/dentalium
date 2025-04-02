export class BudgetRequestRestDTO {
  id?: number;
  idPatient: string;
  treatments?: BudgetLineRestDTO[];

  constructor(
    idPatient: string = '',
    treatments: BudgetLineRestDTO[] = []
  ) {
    this.idPatient = idPatient;
    this.treatments = treatments;
  }
}


export class BudgetLineRestDTO {
  treatmentId: number;
  quantity: number;
  teeth: string;
  discount: number;

  constructor(
    treatmentId: number = 0,
    quantity: number = 0,
    teeth: string = '',
    discount: number = 0
  ) {
    this.treatmentId = treatmentId;
    this.quantity = quantity;
    this.teeth = teeth;
    this.discount = discount;
  }
}
