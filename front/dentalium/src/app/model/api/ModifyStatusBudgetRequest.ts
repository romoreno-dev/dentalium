export class ModifyStatusBudgetRequest {
  id?: number;
  statusCode?: number;

  constructor(id: number, statusCode: number) {
    this.id = id;
    this.statusCode = statusCode;
  }
}
