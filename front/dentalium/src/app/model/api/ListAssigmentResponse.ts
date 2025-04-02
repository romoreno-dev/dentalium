import {Status} from './BudgetResponse';

export class ListAssigmentResponse {
  assigmentList: Person[] = [];

  constructor() {
    this.assigmentList = [];
  }
}

export class Person {

  id: number;
  name: string;
  initDate: Date;

  constructor(id: number, name: string, initDate: Date) {
    this.id = id;
    this.name = name;
    this.initDate = initDate;
  }
}
