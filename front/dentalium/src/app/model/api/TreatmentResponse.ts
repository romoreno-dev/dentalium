import {TreatmentRestDTO} from './TreatmentRestDto';

export class TreatmentResponse {

  treatments: TreatmentRestDTO[]


  constructor(treatments: TreatmentRestDTO[]) {
    this.treatments = treatments;
  }
}
