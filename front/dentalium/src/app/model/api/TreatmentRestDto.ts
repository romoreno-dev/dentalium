export class TreatmentRestDTO {
  id?: string;
  name?: string;
  unitPrice?: number;
  active?: boolean;

  constructor(id?: string, name?: string, unitPrice?: number, active?: boolean) {
    this.id = id;
    this.name = name;
    this.unitPrice = unitPrice;
    this.active = active;
  }
}
